package ru.korshun.solbeg.csv;

import org.apache.commons.csv.CSVPrinter;

public interface CsvCreator {
  void writeToFile(CSVPrinter csvPrinter);
}
