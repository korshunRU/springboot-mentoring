package ru.korshun.solbeg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "privileges")
public class PrivilegeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @ManyToMany(
          mappedBy = "privileges",
          fetch = FetchType.EAGER,
          cascade = CascadeType.ALL
  )
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Set<RoleEntity> roles;
}
