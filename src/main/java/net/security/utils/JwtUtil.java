package net.security.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import net.security.model.Token;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtil {

  private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
  private static final String SECRET_KEY =
      "0uZ6R9JYcD7E3vLq4xA9kN2pT1mG8wHsB5yQ0jVfLrCeUdWiOaMhXzPnTsRgKvY="; // 32-bit key

  private Key getKey() {
    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
  }

  public Token generateToken(String username) {
    return generateToken(Map.of(), username);
  }

  public Token generateToken(Map<String, Object> claims, String subject) {
    Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

    String tokenS =
        Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .signWith(getKey())
            .compact();
    return new Token(tokenS, expiration.getTime());
  }

  // Extract username (subject) from token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extract expiration date
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Generic claim extractor
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Validate token for username and expiration
  public boolean isTokenValid(String token, String username) {
    String tokenUsername = extractUsername(token);
    return tokenUsername.equals(username) && !isTokenExpired(token);
  }

  // Validate only signature + expiration (no username check)
  public boolean isTokenValid(String token) {
    try {
      extractAllClaims(token); // throws if invalid
      return !isTokenExpired(token);
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
  }
}
