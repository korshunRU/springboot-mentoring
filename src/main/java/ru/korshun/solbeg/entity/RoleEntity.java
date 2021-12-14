package ru.korshun.solbeg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(
          fetch = FetchType.EAGER,
          mappedBy = "roles",
          cascade = CascadeType.ALL
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<UserEntity> users;

  @ManyToMany(
          fetch = FetchType.EAGER,
          cascade = CascadeType.ALL
  )
  @JoinTable(
          name = "roles_authority",
          joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") },
          inverseJoinColumns = { @JoinColumn(name = "autority_id", referencedColumnName = "id") }
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<PrivilegeEntity> privileges;
}
