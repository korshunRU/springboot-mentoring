package ru.korshun.solbeg.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.utils.AppFakerImpl;

import java.io.IOException;

@Slf4j
@Component
public class AuthorCsvCreatorImpl extends AbstractCsvCreator {

  public AuthorCsvCreatorImpl(
          @Value("${custom.csv.authors.create}") boolean isCreate,
          @Value("${custom.csv.authors.file-name}") String fileName,
          @Value("${custom.csv.authors.count}") int count,
          AppFakerImpl appFaker
  )
          throws IOException {
    super(isCreate, fileName, count, appFaker, "id", "firstName", "lastName");
  }

  @Override
  public void writeToFile(CSVPrinter csvPrinter) {
    getAppFaker().createAuthors(getCount()).forEach(item -> {
      try {
        csvPrinter.printRecord(
                item.getId(),
                item.getFirstName(),
                item.getLastName()
        );
      } catch (IOException e) {
        e.printStackTrace();
        log.error(String.format("Write to file error: %s", getFileName()));
      }
    });
    log.info(String.format("Write to file successfully: %s", getFileName()));
  }

}
