package org.example.utils;

import com.opencsv.*;
import com.opencsv.bean.*;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    //read csv file
    public static <T> List<T> parseCsvToBean(Class<T> clazz, String fileName, String params, char csvSeparator, int skipLineNum) {
        String[] columnMapping = params.split("\\|", -1);
        ColumnPositionMappingStrategy<T> mapper = new ColumnPositionMappingStrategy<>();
        mapper.setColumnMapping(columnMapping);
        mapper.setType(clazz);
        List<T> parse = new ArrayList<>();
        try {
            CsvToBean csvToBean = new CsvToBeanBuilder<>(new FileReader(fileName))
                    .withMappingStrategy(mapper)
                    .withSeparator(csvSeparator)
                    .withSkipLines(skipLineNum)
                    .build();
            parse = csvToBean.parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return parse;
    }

    //write csv file
    public static <T> boolean writeCsvFile(List<T> list, String fileName, String params, char csvSeparator, boolean append) {
        try {
            String[] columnMapping = params.split("\\|", -1);
            ColumnPositionMappingStrategy<T> mapper = new ColumnPositionMappingStrategy<>();
            mapper.setColumnMapping(columnMapping);
            mapper.setType((Class<? extends T>) list.get(0).getClass());
            Writer writer = new FileWriter(fileName, append);
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer)
                    .withMappingStrategy(mapper)
                    .withSeparator(csvSeparator)
                    .build();
            beanToCsv.write(list);
            writer.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
