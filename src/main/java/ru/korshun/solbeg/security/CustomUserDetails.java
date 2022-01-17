package ru.korshun.solbeg.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.korshun.solbeg.entity.PrivilegeEntity;
import ru.korshun.solbeg.entity.RoleEntity;
import ru.korshun.solbeg.entity.UserEntity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails extends UserEntity implements UserDetails {

  private final boolean isEnabled;

  public static final String ADMIN = "admin";
  public static final String USER = "user";

  public static final String PRIVILEGE_WRITE = "write";
  public static final String PRIVILEGE_READ = "read";
  public static final String PRIVILEGE_EDIT = "edit";
  public static final String PRIVILEGE_DELETE = "delete";

  public CustomUserDetails(
            Long id,
            String firstName,
            String lastName,
            String email,
            String address,
            String password,
            Long createdAt,
            Set<RoleEntity> role,
            boolean enabled
    ) {
    super(id, firstName, lastName, email, address, password, createdAt, null, role);
    this.isEnabled = enabled;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<RoleEntity> roles = super.getRoles();

    if (roles == null)
      return new HashSet<>();

    Set<GrantedAuthority> authorities = new HashSet<>();
    Set<PrivilegeEntity> permissions = new HashSet<>();

    roles.forEach((role) -> {
      permissions.addAll(role.getPrivileges());
      permissions.forEach((authority) -> authorities.add(new SimpleGrantedAuthority(authority.getName())));
    });

    return authorities;
  }

  public Long getId() {
    return super.getId();
  }

  @Override
  public String getUsername() {
    return super.getEmail();
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
  public String getPassword() {
    return super.getPassword();
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
