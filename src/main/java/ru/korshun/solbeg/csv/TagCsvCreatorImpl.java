package ru.korshun.solbeg.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.korshun.solbeg.utils.AppFakerImpl;

import java.io.IOException;

@Slf4j
@Component
public class TagCsvCreatorImpl extends AbstractCsvCreator {

  public TagCsvCreatorImpl(
          @Value("${custom.csv.tags.create}") boolean isCreate,
          @Value("${custom.csv.tags.file-name}") String fileName,
          @Value("${custom.csv.tags.count}") int count,
          AppFakerImpl appFaker
  )
          throws IOException {
    super(isCreate, fileName, count, appFaker, "id", "name");
  }

  @Override
  public void writeToFile(CSVPrinter csvPrinter) {
    getAppFaker().createTags(getCount()).forEach(item -> {
      try {
        csvPrinter.printRecord(
                item.getId(),
                item.getName()
        );
      } catch (IOException e) {
        e.printStackTrace();
        log.error(String.format("Write to file error: %s", getFileName()));
      }
    });
    log.info(String.format("Write to file successfully: %s", getFileName()));
  }

}
