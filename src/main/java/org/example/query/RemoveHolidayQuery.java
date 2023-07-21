package org.example.query;

import lombok.Data;

@Data
public class RemoveHolidayQuery {
    private String countryCode;
    private String holidayDate;
}
