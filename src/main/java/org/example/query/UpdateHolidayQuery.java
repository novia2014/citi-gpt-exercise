package org.example.query;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class UpdateHolidayQuery {
    @CsvBindByPosition(position = 0)
    private String countryCode;
    @CsvBindByPosition(position = 1)
    private String countryDesc;
    @CsvBindByPosition(position = 2)
    private String holidayDate;
    @CsvBindByPosition(position = 3)
    private String holidayName;
}
