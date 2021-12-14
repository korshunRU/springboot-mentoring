package ru.korshun.solbeg.csv;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.io.FileSystemResource;
import ru.korshun.solbeg.utils.AppFakerImpl;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Getter
public abstract class AbstractCsvCreator implements CsvCreator {

  private final String fileName;
  private FileWriter fileWriter;
  private CSVPrinter csvPrinter;
  private final AppFakerImpl appFaker;
  private final String[] headers;
  private final int count;

  public AbstractCsvCreator(
          boolean isCreate,
          String fileName,
          int count,
          AppFakerImpl appFaker,
          final String... headers
  ) throws IOException {
    this.count = count;
    this.fileName = fileName;
    this.appFaker = appFaker;
    this.headers = headers;

    if (!isCreate)
      return;

    fileWriter = createWriter(fileName).orElseThrow(() ->
            new FileNotFoundException(String.format("Error creating file %s", fileName)));
    writeToFile(createPrinter());
    clearResources();
  }

  private Optional<FileWriter> createWriter(String fileName) {
    FileWriter writer = null;
    try {
      writer = new FileWriter(new FileSystemResource(fileName).getFile());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Optional.ofNullable(writer);
  }

  private CSVPrinter createPrinter() throws IOException {
    return csvPrinter =
            new CSVPrinter(
                    fileWriter,
                    CSVFormat.Builder.create().setHeader(headers).build()
            );
  }

  private void clearResources() {
    try {
      csvPrinter.close();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
