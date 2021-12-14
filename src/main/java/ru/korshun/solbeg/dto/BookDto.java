package ru.korshun.solbeg.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
  private Long id;
  private String title;
  private double price;
  private String imageUrl;
  private String createdAt;
  private String updatedAt;
  private TagDto tag;
  private AuthorDto author;
}
