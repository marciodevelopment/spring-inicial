package br.org.curitiba.ici.saude.common.entity.type;

import br.org.curitiba.ici.saude.common.EnumUtil;
import lombok.Getter;


@Getter
public enum SituacaoType {
  ATIVO(1, "Nacional"), INATIVO(2, "Importado");

  private final int id;
  private final String description;

  private SituacaoType(int id, String description) {
    this.id = id;
    this.description = description;
  }

  public static SituacaoType toType(Integer id) {
    return EnumUtil.toType(id, SituacaoType.class);
  }
}
