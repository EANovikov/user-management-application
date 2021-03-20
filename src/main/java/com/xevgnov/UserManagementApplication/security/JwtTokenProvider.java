package com.xevgnov.UserManagementApplication.security;

import com.xevgnov.UserManagementApplication.exception.JwtAuthenticationException;
import com.xevgnov.UserManagementApplication.model.Role;
import com.xevgnov.UserManagementApplication.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

  @Value("${jwt.token.secret}")
  private String tokenSecret;

  @Value("${jwt.token.expired}")
  private Long tokenValidityMilliseconds;

  private UserDetailsService userDetailsService;

  @Autowired
  public JwtTokenProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @PostConstruct
  protected void init() {
    tokenSecret = Base64.getEncoder().encodeToString(tokenSecret.getBytes());
  }

  public String createToken(User user) {

    Claims claims = Jwts.claims().setSubject(user.getUsername());
    claims.put("roles", getRoleNames(Set.of(user.getRole())));

    Date currentDate = new Date();
    Date validityDate = new Date(currentDate.getTime() + tokenValidityMilliseconds);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(currentDate)
        .setExpiration(validityDate)
        .signWith(SignatureAlgorithm.HS256, tokenSecret)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest request) {
    String token = request.getHeader("Authorization");
    if (token != null && token.startsWith("Bearer")) {
      return token.substring(7, token.length());
    } else {
      return null;
    }
  }

  public boolean validateToken(String token) {
    try {
      Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
      return claimsJws.getBody().getExpiration().after(new Date());

    } catch (ExpiredJwtException | JwtAuthenticationException | IllegalArgumentException e) {
      throw new JwtAuthenticationException("Invalid or expired JWS token", e);
    }
  }

  private Set<String> getRoleNames(Set<Role> userRoles) {
    return userRoles.stream().map(Role::getName).collect(Collectors.toSet());
  }
}
