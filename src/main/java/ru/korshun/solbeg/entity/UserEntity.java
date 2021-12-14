package ru.korshun.solbeg.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  @Email
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private Long createdAt;

  private Long updatedAt;

  @ManyToMany(
          fetch = FetchType.EAGER,
          cascade = CascadeType.ALL
  )
  @JoinTable(
          name = "user_roles",
          joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
          inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<RoleEntity> roles;

}
