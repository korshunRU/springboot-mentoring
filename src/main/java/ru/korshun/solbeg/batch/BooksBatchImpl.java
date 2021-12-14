package ru.korshun.solbeg.batch;

import lombok.extern.slf4j.Slf4j;
import ru.korshun.solbeg.csv.model.Book;

import javax.sql.DataSource;

@Slf4j
public class BooksBatchImpl extends AbstractBatchImpl<Book> {

  public BooksBatchImpl(
          DataSource dataSource,
          String fileName
  ) {
    super(dataSource, fileName, Book.class);
    setHeaders(new String[]{"id", "title", "price", "imageUrl", "createAt"});
    setQuery(
            "SET IDENTITY_INSERT books ON; " +
            "INSERT INTO books (id, title, price, image_url, created_at, tag_id, author_id) " +
                    "VALUES (:id, :title, :price, :imageUrl, :createAt, " +
            "(SELECT TOP 1 ID FROM tags ORDER BY NEWID()), " +
            "(SELECT TOP 1 ID FROM authors ORDER BY NEWID()));" +
            "SET IDENTITY_INSERT books OFF;"
    );
  }

}
