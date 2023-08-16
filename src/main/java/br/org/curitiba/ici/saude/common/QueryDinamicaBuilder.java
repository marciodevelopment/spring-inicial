package br.org.curitiba.ici.saude.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import br.org.curitiba.ici.saude.common.query.CondicaoSelect;
import br.org.curitiba.ici.saude.common.query.CondicaoSelectHql;
import br.org.curitiba.ici.saude.common.query.CondicaoType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class QueryDinamicaBuilder<T> {
  private String selectColunas = "select new %s (%s)\n";
  private String selectCount = "select count(*) \n";
  private Class<T> classeResultado;
  private String colunas;
  private Map<String, Object> parametros = new HashMap<>();
  private List<CondicaoSelect> condicoes = new ArrayList<>();
  private EntityManager entityManager;
  private List<String> tabelasFrom = new ArrayList<>();


  public QueryDinamicaBuilder(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public QueryDinamicaBuilder<T> addFrom(String from) {
    tabelasFrom.add(from);
    return this;
  }

  public QueryDinamicaBuilder<T> classeResultado(Class<T> classeResultado) {
    this.classeResultado = classeResultado;
    return this;
  }

  public QueryDinamicaBuilder<T> colunas(String colunas) {
    this.colunas = colunas;
    return this;
  }

  public QueryDinamicaBuilder<T> query(String query) {
    String fromQuery = query.substring(0, query.toUpperCase().indexOf("WHERE"));
    query = query.replace(fromQuery, "");
    this.addFrom(fromQuery);
    List<CondicaoSelect> condicoesMap = Stream.of(query.split("(?i)and")).map(condicaoQuery -> {
      if (!condicaoQuery.toUpperCase().contains("WHERE"))
        condicaoQuery = "and " + condicaoQuery;
      if (condicaoQuery.toUpperCase().contains("LIKE")) {
        return new CondicaoSelect(condicaoQuery, CondicaoType.LIKE_INIT);
      }
      return new CondicaoSelect(condicaoQuery);
    }).toList();

    this.condicoes.addAll(condicoesMap);
    return this;
  }

  public QueryDinamicaBuilder<T> addCondicao(String condicao) {
    condicoes.add(new CondicaoSelect(condicao));
    return this;
  }

  public QueryDinamicaBuilder<T> addCondicaoLikeInit(String condicao) {
    condicoes.add(new CondicaoSelect(condicao, CondicaoType.LIKE_INIT));
    return this;
  }

  public QueryDinamicaBuilder<T> addParametersValue(Map<String, Object> parameters) {
    this.parametros = parameters.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return this;
  }

  private String getHql(Map<String, Object> parametros) {
    return selectColunas.formatted(this.classeResultado.getName(), this.colunas)
        + tabelasFrom.stream().reduce("", (itemFrom, fromFinal) -> fromFinal + " \n" + itemFrom)
        + CondicaoSelectHql.de(parametros, condicoes);
  }

  private String getHqlCount(Map<String, Object> parametros) {
    return selectCount
        + tabelasFrom.stream().reduce("", (itemFrom, fromFinal) -> fromFinal + itemFrom)
        + CondicaoSelectHql.de(parametros, condicoes);
  }

  @Override
  public String toString() {
    return this.getHql(parametros);
  }

  private Long criarQueryCount() {
    String hqlCount = this.getHqlCount(parametros);
    Query query = entityManager.createQuery(hqlCount);
    this.parametros.forEach(query::setParameter);
    return (Long) query.getSingleResult();
  }


  @SuppressWarnings("unchecked")
  public List<T> list() {
    adequarValoresParametros();
    String hql = this.getHql(parametros);
    Query query = entityManager.createQuery(hql);
    this.parametros.forEach(query::setParameter);
    return query.getResultList();
  }

  @SuppressWarnings("unchecked")
  public Page<T> getPage(Pageable pageable) {
    int pageNumber = pageable.getPageNumber();
    int pageSize = pageable.getPageSize();

    if (pageNumber == 0)
      pageNumber = 1;

    String sort = criarSort(pageable);
    adequarValoresParametros();
    String hql = this.getHql(parametros) + sort;

    Query query = entityManager.createQuery(hql);
    query.setFirstResult((pageNumber - 1) * pageSize);
    query.setMaxResults(pageSize);
    this.parametros.forEach(query::setParameter);
    List<T> resultadoBanco = query.getResultList();
    Long count = this.criarQueryCount();
    return new PageImpl<>(resultadoBanco, pageable, count);
  }

  private String criarSort(Pageable pageable) {
    Sort sort = pageable.getSort();
    if (sort.isUnsorted())
      return "";

    Order order = sort.iterator().next();

    String colunaOrdenacao = getColunaQuery(order.getProperty());
    if (colunaOrdenacao.isBlank())
      return "";

    String direction = "desc";
    if (order.isAscending()) {
      direction = "asc";
    }

    return "\n order by " + colunaOrdenacao + " " + direction;
  }


  private String getColunaQuery(String nome) {
    return Stream.of(this.colunas.split(","))
        .filter(coluna -> coluna.toUpperCase().contains(nome.toUpperCase())).findFirst().orElse("");
  }

  private void adequarValoresParametros() {
    for (CondicaoSelect condicao : this.condicoes) {
      parametros.computeIfPresent(condicao.getNomeParametro(), (key, value) -> {
        if (condicao.isLikeInit()) {
          return value.toString() + "%";
        }
        return value;
      });
    }
  }
}
