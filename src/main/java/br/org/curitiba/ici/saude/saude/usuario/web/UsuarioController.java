package br.org.curitiba.ici.saude.saude.usuario.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/usuarios")
@RestController
public class UsuarioController {

  private static List<UsuarioDto> usuarios = new ArrayList<>();

  @PostMapping
  public void addNovoUsuario(@RequestBody UsuarioDto usuario) {
    UsuarioController.usuarios.add(usuario);
  }

  @GetMapping
  public List<UsuarioDto> getUsuarios() {
    return UsuarioController.usuarios;
  }
}
