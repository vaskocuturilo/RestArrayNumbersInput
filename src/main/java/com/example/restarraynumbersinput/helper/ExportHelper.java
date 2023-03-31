package com.example.restarraynumbersinput.helper;

import com.example.restarraynumbersinput.entity.NumberEntity;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ExportHelper {
    public static ByteArrayInputStream exportDataFromDatabaseToCSV(List<NumberEntity> numberEntities) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            for (NumberEntity numberEntity : numberEntities) {
                List<? extends Number> data = Arrays.asList(
                        numberEntity.getId(),
                        numberEntity.getNumber());
                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException exception) {
            throw new RuntimeException("Fail to import data from database to CSV file: " + exception.getMessage());
        }
    }
}
