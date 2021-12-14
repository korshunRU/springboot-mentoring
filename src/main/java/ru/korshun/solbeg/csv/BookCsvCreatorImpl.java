package ru.korshun.solbeg.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.utils.AppFakerImpl;

import java.io.IOException;

@Slf4j
@Component
public class BookCsvCreatorImpl extends AbstractCsvCreator {

  public BookCsvCreatorImpl(
          @Value("${custom.csv.books.create}") boolean isCreate,
          @Value("${custom.csv.books.file-name}") String fileName,
          @Value("${custom.csv.books.count}") int count,
          AppFakerImpl appFaker
  )
          throws IOException {
    super(isCreate, fileName, count, appFaker, "id", "title", "price", "imageUrl", "createAt");
  }

  @Override
  public void writeToFile(CSVPrinter csvPrinter) {
    getAppFaker().createBooks(getCount()).forEach(item -> {
      try {
        csvPrinter.printRecord(
                item.getId(),
                item.getTitle(),
                item.getPrice(),
                item.getImageUrl(),
                item.getCreateAt()
        );
      } catch (IOException e) {
        e.printStackTrace();
        log.error(String.format("Write to file error: %s", getFileName()));
      }
    });
    log.info(String.format("Write to file successfully: %s", getFileName()));
  }

}
