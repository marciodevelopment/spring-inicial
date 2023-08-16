package br.org.curitiba.ici.saude.saude.seguranca.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.curitiba.ici.saude.saude.seguranca.entity.UsuarioEntity;
import br.org.curitiba.ici.saude.saude.seguranca.repository.UsuarioRepository;
import br.org.curitiba.ici.saude.saude.seguranca.service.TokenService;
import br.org.curitiba.ici.saude.saude.seguranca.web.dto.LoginDto;
import br.org.curitiba.ici.saude.saude.seguranca.web.dto.RegisterDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final UsuarioRepository repository;
  private final TokenService tokenService;

  @PostMapping("/login")
  public String login(@RequestBody @Valid LoginDto login) {
    var usernamePassword =
        new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
    var auth = authManager.authenticate(usernamePassword);
    return tokenService.generateToken((UsuarioEntity) auth.getPrincipal());
  }

  @PostMapping("/register")
  public void register(@RequestBody @Valid RegisterDto register) {
    String passwordEncript = new BCryptPasswordEncoder().encode(register.getPassword());
    UsuarioEntity usuario =
        new UsuarioEntity(register.getUsername(), passwordEncript, register.getRole());
    this.repository.save(usuario);
  }

}
