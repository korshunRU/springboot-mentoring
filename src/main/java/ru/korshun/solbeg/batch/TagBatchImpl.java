package ru.korshun.solbeg.batch;

import lombok.extern.slf4j.Slf4j;
import ru.korshun.solbeg.csv.model.Tag;

import javax.sql.DataSource;

@Slf4j
public class TagBatchImpl extends AbstractBatchImpl<Tag> {

  public TagBatchImpl(
          DataSource dataSource,
          String fileName
  ) {
    super(dataSource, fileName, Tag.class);
    setHeaders(new String[]{"id", "name"});
    setQuery(
            "SET IDENTITY_INSERT tags ON; " +
            "INSERT INTO tags (id, name) VALUES (:id, :name);"+
            "SET IDENTITY_INSERT tags OFF;"
    );
  }

}
