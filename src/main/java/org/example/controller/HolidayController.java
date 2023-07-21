package org.example.controller;

import jakarta.annotation.Resource;
import org.example.dto.HolidayDto;
import org.example.dto.R;
import org.example.query.SearchHolidayQuery;
import org.example.query.UpdateHolidayQuery;
import org.example.service.IHolidayService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/holiday")
public class HolidayController {

    @Resource
    private IHolidayService iHolidayService;

    @ResponseBody
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> add(@RequestBody UpdateHolidayQuery updateHolidayQuery) {
        if (updateHolidayQuery == null) {
            return R.fail("query is null");
        }
        if (updateHolidayQuery.getCountryCode() == null) {
            return R.fail("countryCode is null");
        }
        if (updateHolidayQuery.getCountryDesc() == null) {
            return R.fail("countryDesc is null");
        }
        if (updateHolidayQuery.getHolidayDate() == null) {
            return R.fail("holidayDate is null");
        }
        if (updateHolidayQuery.getHolidayName() == null) {
            return R.fail("holidayName is null");
        }

        SearchHolidayQuery searchHolidayQuery = new SearchHolidayQuery(updateHolidayQuery.getCountryCode(), updateHolidayQuery.getHolidayDate());
        List<HolidayDto> holidayDtoList = iHolidayService.getHolidayList();
        if (iHolidayService.isHolidayExisted(searchHolidayQuery, holidayDtoList)) {
            return R.fail("duplicated country code and holiday date");
        }
        return R.ok(iHolidayService.saveOrUpdateHolidayList(holidayDtoList, List.of(updateHolidayQuery), true));
    }

    @ResponseBody
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> update(@RequestBody UpdateHolidayQuery updateHolidayQuery) {
        if (updateHolidayQuery == null) {
            return R.fail("query is null");
        }
        if (updateHolidayQuery.getCountryCode() == null) {
            return R.fail("countryCode is null");
        }
        if (updateHolidayQuery.getCountryDesc() == null) {
            return R.fail("countryDesc is null");
        }
        if (updateHolidayQuery.getHolidayDate() == null) {
            return R.fail("holidayDate is null");
        }
        if (updateHolidayQuery.getHolidayName() == null) {
            return R.fail("holidayName is null");
        }
        List<HolidayDto> holidayDtoList = iHolidayService.getHolidayList();
        SearchHolidayQuery searchHolidayQuery = new SearchHolidayQuery(updateHolidayQuery.getCountryCode(), updateHolidayQuery.getHolidayDate());
        if (!iHolidayService.isHolidayExisted(searchHolidayQuery, holidayDtoList)) {
            return R.fail("country code and holiday date not existed");
        }
        return R.ok(iHolidayService.saveOrUpdateHolidayList(holidayDtoList, List.of(updateHolidayQuery), false));
    }

    @ResponseBody
    @PostMapping(value = "/batchAdd", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> batchAdd(@RequestBody List<UpdateHolidayQuery> updateHolidayQueryList) {
        if (updateHolidayQueryList == null) {
            return R.fail("query is null");
        }
        for (UpdateHolidayQuery updateHolidayQuery : updateHolidayQueryList) {
            if (updateHolidayQuery.getCountryCode() == null) {
                return R.fail("countryCode is null");
            }
            if (updateHolidayQuery.getCountryDesc() == null) {
                return R.fail("countryDesc is null");
            }
            if (updateHolidayQuery.getHolidayDate() == null) {
                return R.fail("holidayDate is null");
            }
            if (updateHolidayQuery.getHolidayName() == null) {
                return R.fail("holidayName is null");
            }
        }
        List<HolidayDto> holidayDtoList = iHolidayService.getHolidayList();
        for (HolidayDto holidayDto : holidayDtoList) {
            for (UpdateHolidayQuery updateHolidayQuery : updateHolidayQueryList) {
                if (holidayDto.getCountryCode().equals(updateHolidayQuery.getCountryCode())
                        && holidayDto.getHolidayDate().equals(updateHolidayQuery.getHolidayDate())) {
                    return R.fail(String.format("duplicated counter code and holiday date: %s, %s", holidayDto.getCountryCode(), holidayDto.getHolidayDate()));
                }
            }
        }
        return R.ok(iHolidayService.saveOrUpdateHolidayList(holidayDtoList, updateHolidayQueryList, true));
    }

    @ResponseBody
    @PostMapping(value = "/batchUpdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> batchUpdate(@RequestBody List<UpdateHolidayQuery> updateHolidayQueryList) {
        if (updateHolidayQueryList == null) {
            return R.fail("query is null");
        }
        for (UpdateHolidayQuery updateHolidayQuery : updateHolidayQueryList) {
            if (updateHolidayQuery.getCountryCode() == null) {
                return R.fail("countryCode is null");
            }
            if (updateHolidayQuery.getCountryDesc() == null) {
                return R.fail("countryDesc is null");
            }
            if (updateHolidayQuery.getHolidayDate() == null) {
                return R.fail("holidayDate is null");
            }
            if (updateHolidayQuery.getHolidayName() == null) {
                return R.fail("holidayName is null");
            }
        }
        List<HolidayDto> holidayDtoList = iHolidayService.getHolidayList();
        for (UpdateHolidayQuery updateHolidayQuery : updateHolidayQueryList) {
            boolean isExisted = false;
            for (HolidayDto holidayDto : holidayDtoList) {
                if (holidayDto.getCountryCode().equals(updateHolidayQuery.getCountryCode())
                        && holidayDto.getHolidayDate().equals(updateHolidayQuery.getHolidayDate())) {
                    isExisted = true;
                    break;
                }
            }
            if (!isExisted) {
                return R.fail(String.format("country code and holiday date: %s, %s not exist",
                        updateHolidayQuery.getCountryCode(), updateHolidayQuery.getHolidayDate()));
            }
        }
        return R.ok(iHolidayService.saveOrUpdateHolidayList(holidayDtoList, updateHolidayQueryList, false));
    }

    @ResponseBody
    @PostMapping(value = "/getNextYear", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public R<HolidayDto> getNextYear(@RequestBody SearchHolidayQuery searchHolidayQuery) {
        List<HolidayDto> holidayDtoList = iHolidayService.getHolidayList();
        for (HolidayDto holidayDto : holidayDtoList) {
            if (holidayDto.getCountryCode().equals(searchHolidayQuery.getCountryCode())
                    && holidayDto.getHolidayDate().equals(searchHolidayQuery.getHolidayDate())) {
                //convert string to date
                String currentYear = holidayDto.getHolidayDate();
                //add one year



                return R.ok(holidayDto);
            }
        }
        return R.fail("country code and holiday date not exist");
    }
}


