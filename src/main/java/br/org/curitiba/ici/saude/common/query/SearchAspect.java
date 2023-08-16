package br.org.curitiba.ici.saude.common.query;

import java.lang.reflect.Method;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import br.org.curitiba.ici.saude.common.QueryDinamicaBuilder;
import jakarta.persistence.EntityManager;

@Component
@Aspect
public class SearchAspect {
  @Autowired
  private EntityManager entityManager;

  @Around("@annotation(br.org.curitiba.ici.saude.common.query.QuerySearch)")
  public Object queryDinamicaAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) {
    MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = signature.getMethod();

    QuerySearch queryDinamica = method.getAnnotation(QuerySearch.class);
    @SuppressWarnings("unchecked")
    Map<String, Object> parameters = (Map<String, Object>) proceedingJoinPoint.getArgs()[0];
    Pageable pageable = (Pageable) proceedingJoinPoint.getArgs()[1];
    return this.pesquisa(queryDinamica.viewResult(), parameters, pageable, queryDinamica.columns(),
        queryDinamica.query());
  }

  private <T> Page<T> pesquisa(Class<T> clazz, Map<String, Object> parameters, Pageable pageable,
      String colunas, String query) {
    QueryDinamicaBuilder<T> queryDinamica = new QueryDinamicaBuilder<>(entityManager);
    return queryDinamica.classeResultado(clazz).colunas(colunas).query(query)
        .addParametersValue(parameters).getPage(pageable);

  }
}
