package org.example.query;

import lombok.Data;

@Data
public class SearchHolidayQuery {
    private String countryCode;
    private String holidayDate;

    public SearchHolidayQuery(String countryCode, String holidayDate) {
        this.countryCode = countryCode;
        this.holidayDate = holidayDate;
    }
}
