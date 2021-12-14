package ru.korshun.solbeg.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class AuthorEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @OneToMany(
          mappedBy = "author",
          orphanRemoval = true,
          cascade = CascadeType.ALL)
  @OnDelete(action = OnDeleteAction.CASCADE)
  private List<BookEntity> authorsBooks;

}
