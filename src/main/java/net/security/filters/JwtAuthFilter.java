package net.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import net.security.services.UserServiceImpl;
import net.security.utils.Constants;
import net.security.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserServiceImpl userService;

  public JwtAuthFilter(JwtUtil jwtUtil, UserServiceImpl userService) {
    this.jwtUtil = jwtUtil;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader(Constants.AUTHORIZATION);
    String token = null, username = null;
    if (authHeader != null && authHeader.startsWith(Constants.BEARER)) {
      token = authHeader.substring(7);
      username = jwtUtil.extractUsername(token);
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userService.loadUserByUsername(username);

      if (jwtUtil.isTokenValid(token, username)) {
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(
            new WebAuthenticationDetailsSource()
                .buildDetails(
                    request)); // Not required, just in case if request details required later
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
