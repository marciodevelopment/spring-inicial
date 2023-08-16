package br.org.curitiba.ici.saude.common.web;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import br.org.curitiba.ici.saude.common.BaseEnum;

public class CustomEnumSerializer extends JsonSerializer<BaseEnum> {

  @Override
  public void serialize(BaseEnum value, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeString(value.getDescription());
  }



}
