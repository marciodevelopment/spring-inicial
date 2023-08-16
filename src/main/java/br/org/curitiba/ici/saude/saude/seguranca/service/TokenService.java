package br.org.curitiba.ici.saude.saude.seguranca.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import br.org.curitiba.ici.saude.saude.seguranca.entity.UsuarioEntity;

@Service
public class TokenService {
  @Value("{api.security.secret}")
  private String secret;

  public String generateToken(UsuarioEntity user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.create()

          .withIssuer("e-saude")

          .withSubject(user.getUsername())

          .withExpiresAt(this.generateExpirationDate())

          .sign(algorithm);
    } catch (JWTCreationException exception) {
      exception.printStackTrace();
      throw exception;
    }
  }

  public String validateToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secret);
    return JWT.require(algorithm).withIssuer("e-saude").build().verify(token).getSubject();
  }

  private Instant generateExpirationDate() {
    return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.ofHours(-3));
  }
}
