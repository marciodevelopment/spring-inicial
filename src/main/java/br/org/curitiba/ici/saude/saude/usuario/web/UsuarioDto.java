package br.org.curitiba.ici.saude.saude.usuario.web;

import java.util.UUID;
import lombok.Data;

@Data
public class UsuarioDto {
  private UUID id = UUID.randomUUID();
  private String nmUsuario;
  private String nmMae;
}
