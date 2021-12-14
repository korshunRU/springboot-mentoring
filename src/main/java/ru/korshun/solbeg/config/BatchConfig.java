package ru.korshun.solbeg.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.korshun.solbeg.batch.AuthorsBatchImpl;
import ru.korshun.solbeg.batch.BooksBatchImpl;
import ru.korshun.solbeg.batch.TagBatchImpl;
import ru.korshun.solbeg.csv.model.Author;
import ru.korshun.solbeg.csv.model.Book;
import ru.korshun.solbeg.csv.model.Tag;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

  @Value("${custom.csv.tags.file-name}")
  private String fileNameTags;

  @Value("${custom.csv.authors.file-name}")
  private String fileNameAuthors;

  @Value("${custom.csv.books.file-name}")
  private String fileNameBooks;

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final DataSource dataSource;

  @Bean
  public TagBatchImpl getTagData() {
    return new TagBatchImpl(
            dataSource,
            fileNameTags
    );
  }

  @Bean
  public AuthorsBatchImpl getAuthorData() {
    return new AuthorsBatchImpl(
            dataSource,
            fileNameAuthors
    );
  }

  @Bean
  public BooksBatchImpl getBookData() {
    return new BooksBatchImpl(
            dataSource,
            fileNameBooks
    );
  }

  @Bean
  public Step tagStep() {
    return stepBuilderFactory.get("tag-step")
            .<Tag, Tag>chunk(10)
            .reader(getTagData().getReader())
            .writer(getTagData().getWriter())
            .build();
  }

  @Bean
  public Step authorStep() {
    return stepBuilderFactory.get("author-step")
            .<Author, Author>chunk(10)
            .reader(getAuthorData().getReader())
            .writer(getAuthorData().getWriter())
            .build();
  }

  @Bean
  public Step bookStep() {
    return stepBuilderFactory.get("book-step")
            .<Book, Book>chunk(10)
            .reader(getBookData().getReader())
            .writer(getBookData().getWriter())
            .build();
  }

  @Bean
  public Job job() {
    return jobBuilderFactory.get("job")
            .start(tagStep())
            .next(authorStep())
            .next(bookStep())
            .build();
  }

}
