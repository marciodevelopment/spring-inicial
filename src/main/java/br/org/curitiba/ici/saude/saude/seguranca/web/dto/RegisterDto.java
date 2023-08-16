package br.org.curitiba.ici.saude.saude.seguranca.web.dto;

import br.org.curitiba.ici.saude.saude.seguranca.entity.UserRoleType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDto {
  @NotEmpty
  private String username;
  @NotEmpty
  private String password;
  @NotNull
  private UserRoleType role;


}
