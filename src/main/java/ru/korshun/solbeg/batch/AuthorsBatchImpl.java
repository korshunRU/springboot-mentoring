package ru.korshun.solbeg.batch;

import lombok.extern.slf4j.Slf4j;
import ru.korshun.solbeg.csv.model.Author;

import javax.sql.DataSource;

@Slf4j
public class AuthorsBatchImpl extends AbstractBatchImpl<Author> {

  public AuthorsBatchImpl(
          DataSource dataSource,
          String fileName
  ) {
    super(dataSource, fileName, Author.class);
    setHeaders(new String[]{"id", "firstName", "lastName"});
    setQuery(
            // "SET IDENTITY_INSERT authors ON; " +
            "INSERT INTO authors (id, first_name, last_name) VALUES (:id, :firstName, :lastName);"
            // "SET IDENTITY_INSERT authors OFF;"
    );
  }

}
