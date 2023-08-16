package br.org.curitiba.ici.saude.common;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParametrosPesquisaRequest {

  public Map<String, Object> toParametros() {
    return Stream.of(this.getClass().getDeclaredFields()).filter(field -> getValue(field) != null)
        .collect(Collectors.toMap(Field::getName, this::getValue));
  }


  public Object getValue(Field field) {
    try {
      field.setAccessible(true);
      return field.get(this);
    } catch (Exception e) {
      return null;
    }
  }
}
