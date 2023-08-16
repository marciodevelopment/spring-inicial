package br.org.curitiba.ici.saude.saude.seguranca.entity;

import lombok.Getter;

@Getter
public enum UserRoleType {
  ADMIN("ADMIN"), USER("USER");

  private String role;

  UserRoleType(String role) {
    this.role = role;
  }

  public String getAuthorityRole() {
    return "ROLE_" + this.role.toUpperCase();
  }
}
