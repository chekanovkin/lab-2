package com.example.lab1.service;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSVReadService {

    public static List<String[]> readCSV(String fileName) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(fileName))
            .withCSVParser(csvParser)
            .build()) {
            return reader.readAll();
            //r.forEach(x -> System.out.println(Arrays.toString(x)));
        } catch (CsvException | IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
