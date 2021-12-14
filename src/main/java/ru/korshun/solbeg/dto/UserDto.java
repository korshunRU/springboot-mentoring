package ru.korshun.solbeg.dto;

import lombok.*;
import ru.korshun.solbeg.entity.RoleEntity;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private Long createdAt;
  private Set<RoleEntity> roles;

}
