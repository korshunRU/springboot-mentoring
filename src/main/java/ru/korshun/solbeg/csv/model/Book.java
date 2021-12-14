package ru.korshun.solbeg.csv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
  private Long id;
  private String title;
  private double price;
  private String imageUrl;
  private Long createAt;
}
