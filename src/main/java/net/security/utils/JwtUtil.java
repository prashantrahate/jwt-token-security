package net.security.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.security.model.Token;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class JwtUtil {

  private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
  private static final String SECRET_KEY =
      "0uZ6R9JYcD7E3vLq4xA9kN2pT1mG8wHsB5yQ0jVfLrCeUdWiOaMhXzPnTsRgKvY="; // 32 bit key

  public Token generateToken(String username) {
    Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
    Token token = new Token();
    token.setExpiresIn(expiration.getTime());
    token.setType("jwt");
    token.setUsername(username);

    String tokenS =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expiration) //
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    token.setToken(tokenS);
    return token;
  }
}
