package ru.korshun.solbeg.utils;

import ru.korshun.solbeg.csv.model.Author;
import ru.korshun.solbeg.csv.model.Book;
import ru.korshun.solbeg.csv.model.Tag;

import java.util.List;

public interface AppFaker {

  List<Tag> createTags(int count);
  List<Author> createAuthors(int count);
  List<Book> createBooks(int count);

}
