package bop.PropertyImportService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class CsvFileReader {
    private CSVReader reader;

    @Autowired
    public CsvFileReader(String csvFile) {
//        try {
//            FileReader fileReader = new FileReader(csvFile);
//            reader = new CSVReader(fileReader, ',', '"', '\\', 1, false);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String[] attemptToReadNextValues() {
        try {
            return reader.readNext();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
