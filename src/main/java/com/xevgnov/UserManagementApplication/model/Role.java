package com.xevgnov.UserManagementApplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

  @Id
  @Positive
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(name = "name", nullable = false)
  private String name;

  @NotEmpty
  @ElementCollection(targetClass = Permission.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"))
  @Enumerated(EnumType.STRING)
  private Set<Permission> permissions;

  @Override
  @JsonIgnore
  public String getAuthority() {
    return getName();
  }

}
