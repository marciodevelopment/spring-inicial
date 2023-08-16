package br.org.curitiba.ici.saude.common.query;

import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtratorNomeParametros {
  public static List<String> de(String query) {
    StringBuilder parametro = new StringBuilder();
    boolean lerCaracter = false;
    for (char caracter : query.toCharArray()) {
      if (caracter == ':') {
        lerCaracter = true;
      }
      if (caracter == ' ' || caracter == '\n') {
        lerCaracter = false;
      }
      if (lerCaracter) {
        parametro.append(caracter);
      }
    }

    return Stream.of(parametro.toString().replaceFirst(":", "").split(":"))
        .filter(item -> item.trim().length() > 0).toList();
  }
}
