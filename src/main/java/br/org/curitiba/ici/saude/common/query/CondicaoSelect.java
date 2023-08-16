package br.org.curitiba.ici.saude.common.query;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CondicaoSelect {
  private static final CharSequence WHERE = "where 1 = 1 ";
  private String condicao;
  private CondicaoType condicaoType;

  public CondicaoSelect(String condicao) {
    this.condicao = formatarWhere(condicao);
    this.condicaoType = CondicaoType.IGUAL;
  }

  public CondicaoSelect(String condicao, CondicaoType tipoCondicao) {
    this.condicao = formatarWhere(condicao);
    this.condicaoType = tipoCondicao;
  }

  private String formatarWhere(String condicao) {
    if (condicao.toLowerCase().contains("where")) {
      return condicao.replace("where", WHERE + " \n and");
    }
    return condicao;
  }

  public boolean existeWhere() {
    return condicao.contains(WHERE);
  }

  public boolean isLikeInit() {
    return condicaoType.equals(CondicaoType.LIKE_INIT);
  }

  public String getNomeParametro() {
    List<String> nomeParametros = ExtratorNomeParametros.de(condicao);
    if (nomeParametros.isEmpty()) {
      return null;
    }
    return nomeParametros.get(0);
  }

}
