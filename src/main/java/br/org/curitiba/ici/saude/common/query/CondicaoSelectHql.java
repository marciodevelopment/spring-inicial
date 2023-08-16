package br.org.curitiba.ici.saude.common.query;

import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CondicaoSelectHql {

  public static String de(Map<String, Object> parametros, List<CondicaoSelect> condicoes) {
    StringBuilder hqlCondicao = new StringBuilder();
    for (CondicaoSelect condicao : condicoes) {
      List<String> nomeParametros = ExtratorNomeParametros.de(condicao.getCondicao());
      if (existeValoresParaOsParametros(nomeParametros, parametros)) {
        hqlCondicao.append(condicao.getCondicao()).append("\n");
      }
      if (condicao.existeWhere() && !existeValoresParaOsParametros(nomeParametros, parametros)) {
        hqlCondicao.append(" where 1 = 1\n");
      }
    }
    return hqlCondicao.toString();
  }

  private static boolean existeValoresParaOsParametros(List<String> nomeParametros,
      Map<String, Object> parametros) {
    if (nomeParametros.isEmpty())
      return true;
    return parametros.containsKey(nomeParametros.get(0));
  }

}
