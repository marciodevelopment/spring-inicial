package br.org.curitiba.ici.saude.saude.seguranca.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import br.org.curitiba.ici.saude.saude.seguranca.entity.UsuarioEntity;

@Service
public class UsuarioRepository {
  private static List<UsuarioEntity> usuarios = new ArrayList<>();

  public UsuarioEntity findByLogin(String login) {
    return usuarios.stream().filter(usr -> usr.getUsername().equals(login)).findFirst().get();
  }

  public void save(UsuarioEntity usuario) {
    UsuarioRepository.usuarios.add(usuario);
  }
}
