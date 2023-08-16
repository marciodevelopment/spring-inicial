package br.org.curitiba.ici.saude.saude.seguranca.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Data;

@Data
public class UsuarioEntity implements UserDetails {
  private static final long serialVersionUID = -2949698677551917290L;
  private UUID id = UUID.randomUUID();
  private String username;
  private String password;
  private UserRoleType role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.role == UserRoleType.ADMIN)
      return List.of(new SimpleGrantedAuthority(UserRoleType.ADMIN.getAuthorityRole()),
          new SimpleGrantedAuthority(UserRoleType.USER.getRole()));
    return List.of(new SimpleGrantedAuthority(UserRoleType.USER.getAuthorityRole()));
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public UsuarioEntity(String username, String password, UserRoleType role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }
}
