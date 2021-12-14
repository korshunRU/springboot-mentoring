package ru.korshun.solbeg.dto.request;

import lombok.Getter;

@Getter
public class EditBookRequestDto {
  private String title;
  private double price;
  private String imageUrl;
  private String tag;
  private String authorFirstName;
  private String authorLastName;
}
