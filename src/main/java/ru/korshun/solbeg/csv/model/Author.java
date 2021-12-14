package ru.korshun.solbeg.csv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  private Long id;
  private String firstName;
  private String lastName;
}
