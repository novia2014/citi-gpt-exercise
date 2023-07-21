package org.example.service.impl;

import org.example.dto.HolidayDto;
import org.example.query.SearchHolidayQuery;
import org.example.query.UpdateHolidayQuery;
import org.example.service.IHolidayService;
import org.example.utils.CsvUtil;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class HolidayServiceImpl implements IHolidayService {

    private final String holidayFile = "src\\main\\resources\\holiday.csv";
    private final String columnNames = "Country Code|Country Desc|Holiday Date|Holiday Name";

    @Override
    public List<HolidayDto> getHolidayList() {
        return CsvUtil.parseCsvToBean(HolidayDto.class, holidayFile, columnNames, ',', 1);
    }

    @Override
    public boolean isHolidayExisted(SearchHolidayQuery searchHolidayQuery, List<HolidayDto> holidayDtoList) {
        return holidayDtoList.stream().filter(holidayDto ->
                holidayDto.getCountryCode().equals(searchHolidayQuery.getCountryCode())
                        && holidayDto.getHolidayDate().equals(searchHolidayQuery.getHolidayDate())
        ).toList().size() != 0;
    }

    @Override
    public boolean saveOrUpdateHolidayList(List<HolidayDto> originalHolidayDtoList, List<UpdateHolidayQuery> updateHolidayQueryList, boolean isAdd) {
        if (isAdd) {
            return CsvUtil.writeCsvFile(updateHolidayQueryList, holidayFile, columnNames, ',', isAdd);
        } else {
            for (HolidayDto holidayDto : originalHolidayDtoList) {
                for (UpdateHolidayQuery updateHolidayQuery : updateHolidayQueryList) {
                    if (holidayDto.getCountryCode().equals(updateHolidayQuery.getCountryCode())
                            && holidayDto.getHolidayDate().equals(updateHolidayQuery.getHolidayDate())) {
                        holidayDto.setCountryDesc(updateHolidayQuery.getCountryDesc());
                        holidayDto.setHolidayName(updateHolidayQuery.getHolidayName());
                    }
                }
            }
            return CsvUtil.writeCsvFile(originalHolidayDtoList, holidayFile, columnNames, ',', isAdd);
        }


    }
}


