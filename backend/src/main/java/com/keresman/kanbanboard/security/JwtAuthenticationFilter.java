package com.keresman.kanbanboard.security;

import com.keresman.kanbanboard.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Authentication filter that validates JWT tokens in the Authorization header and sets the
 * authentication context if valid.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserRepository userRepository;

  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain chain)
      throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String token = null;

    if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      token = authHeader.substring(7);
    }

    if (token != null
        && jwtUtil.isTokenValid(token)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      String subject = jwtUtil.getSubject(token);

      var user =
          userRepository
              .findById(Long.valueOf(subject))
              .orElseThrow(
                  () ->
                      new UsernameNotFoundException(
                          "User not found for token subject: %s".formatted(subject)));

      var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(request, response);
  }
}
