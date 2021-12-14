package ru.korshun.solbeg.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "books")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private double price;

  @Column(nullable = false)
  private String imageUrl;

  @Column(nullable = false)
  private Long createdAt;

  private Long updatedAt;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "author_id", nullable = false)
  private AuthorEntity author;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "tag_id", nullable = false)
  private TagEntity tag;

}
