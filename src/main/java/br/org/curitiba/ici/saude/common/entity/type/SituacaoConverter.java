package br.org.curitiba.ici.saude.common.entity.type;


import jakarta.persistence.AttributeConverter;


public class SituacaoConverter implements AttributeConverter<SituacaoType, Integer> {

  @Override
  public Integer convertToDatabaseColumn(SituacaoType type) {
    if (type == null)
      return null;
    return type.getId();
  }

  @Override
  public SituacaoType convertToEntityAttribute(Integer id) {
    return SituacaoType.toType(id);
  }
}
