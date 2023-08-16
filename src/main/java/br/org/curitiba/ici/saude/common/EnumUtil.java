package br.org.curitiba.ici.saude.common;

import java.util.Optional;
import java.util.stream.Stream;

public class EnumUtil {

  @SuppressWarnings("unchecked")
  public static <T> T toType(Integer id, Class<?> clazz) {
    Optional<?> possivelBaseEnum = Stream.of(clazz.getEnumConstants())
        .filter(typeEnum -> ((BaseEnum) typeEnum).getId().equals(id)).findAny();
    if (possivelBaseEnum.isEmpty())
      return null;
    return (T) possivelBaseEnum.get();
  }

}
