package ru.korshun.solbeg.batch;

import lombok.Setter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

public class AbstractBatchImpl<T> {

  private final DataSource dataSource;
  private final String fileName;
  private final Class<T> type;

  @Setter
  private String query;
  @Setter
  private String[] headers;

  public AbstractBatchImpl(
          DataSource dataSource,
          String fileName,
          Class<T> type) {
    this.dataSource = dataSource;
    this.fileName = fileName;
    this.type = type;
  }

  public FlatFileItemReader<T> getReader() {
    FlatFileItemReader<T> reader = new FlatFileItemReader<>();
    reader.setLinesToSkip(1);
    reader.setResource(new FileSystemResource(fileName));

    DefaultLineMapper<T> customerLineMapper = new DefaultLineMapper<>();

    DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
    tokenizer.setNames(headers);

    customerLineMapper.setLineTokenizer(tokenizer);
    customerLineMapper.setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
      setTargetType(type);
    }});
    customerLineMapper.afterPropertiesSet();
    reader.setLineMapper(customerLineMapper);
    return reader;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public JdbcBatchItemWriter<T> getWriter() {
    JdbcBatchItemWriter<T> itemWriter = new JdbcBatchItemWriter<>();

    itemWriter.setDataSource(dataSource);
    itemWriter.setSql(query);
    itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
    itemWriter.afterPropertiesSet();

    return itemWriter;
  }
}
