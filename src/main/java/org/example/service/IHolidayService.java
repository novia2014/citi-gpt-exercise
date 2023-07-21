package org.example.service;

import org.example.dto.HolidayDto;
import org.example.query.SearchHolidayQuery;
import org.example.query.UpdateHolidayQuery;

import java.util.List;

public interface IHolidayService {

    List<HolidayDto> getHolidayList();

    boolean isHolidayExisted(SearchHolidayQuery searchHolidayQuery, List<HolidayDto> holidayDtoList);

    boolean saveOrUpdateHolidayList(List<HolidayDto> originalHolidayDtoList, List<UpdateHolidayQuery> updateHolidayQueryList, boolean isAdd);
}
