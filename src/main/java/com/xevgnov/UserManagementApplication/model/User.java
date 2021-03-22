package com.xevgnov.UserManagementApplication.model;

import com.xevgnov.UserManagementApplication.exception.IllegalRoleException;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@ToString(exclude = {"role"})
@Table(name = "users")
public class User implements UserDetails {

  public static final String USER_AUTHORITY_PREFIX = "ROLE_";

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id")
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    // recognize admin and non-admin users
    String userAuthority = getRole().getAuthority();
    if (!userAuthority.startsWith(USER_AUTHORITY_PREFIX)) {
      userAuthority = USER_AUTHORITY_PREFIX + userAuthority;
    }
    authorities.add(new SimpleGrantedAuthority(userAuthority));
    // convert permissions to authorities
    getRole()
        .getPermissions()
        .forEach(
            p -> authorities.add(new SimpleGrantedAuthority(USER_AUTHORITY_PREFIX + p.name())));
    return authorities;
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

  public void setRole(Role role) {
    if (!isAdminUser() && role.getName().equals("ROLE_ADMIN")) {
      throw new IllegalRoleException("The role [" + role.getName() + "] can be set by admin only");
    }
    this.role = role;
  }

  private boolean isAdminUser() {
    if (SecurityContextHolder.getContext().getAuthentication() != null) {
      return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
              .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
    return false;
  }
}
