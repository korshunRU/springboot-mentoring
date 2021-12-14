package ru.korshun.solbeg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tags")
public class TagEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(
          mappedBy = "tag",
          orphanRemoval = true,
          cascade = CascadeType.ALL)
  private List<BookEntity> tagBooks;
}
