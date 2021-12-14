package ru.korshun.solbeg.utils;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.csv.model.Author;
import ru.korshun.solbeg.csv.model.Book;
import ru.korshun.solbeg.csv.model.Tag;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class AppFakerImpl implements AppFaker {

  private final Faker faker = new Faker();

  @Override
  public List<Tag> createTags(int count) {
    AtomicLong counter = new AtomicLong(0);
    return Stream
            .generate(() -> faker.book().genre())
            .distinct()
            .limit(count)
            .collect(Collectors.toList())
            .stream()
            .map(item -> new Tag(
                    counter.incrementAndGet(),
                    item
            ))
            .collect(Collectors.toList());
  }

  @Override
  public List<Author> createAuthors(int count) {
    AtomicLong counter = new AtomicLong(0);
    return Stream
            .generate(() -> new Author(
                    counter.incrementAndGet(),
                    faker.name().firstName(),
                    faker.name().lastName())
            )
            .limit(count)
            .collect(Collectors.toList());
  }

  @Override
  public List<Book> createBooks(int count) {
    AtomicLong counter = new AtomicLong(0);
    return Stream
            .generate(() -> {
              String price = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.US))
                      .format(faker.number().randomDouble(2, 3, 15));
              return new Book(
                      counter.incrementAndGet(),
                      faker.book().title(),
                      Double.parseDouble(price),
                      "https://picsum.photos/200/300?random=1",
                      System.currentTimeMillis());
            })
            .limit(count)
            .collect(Collectors.toList());
  }

}
