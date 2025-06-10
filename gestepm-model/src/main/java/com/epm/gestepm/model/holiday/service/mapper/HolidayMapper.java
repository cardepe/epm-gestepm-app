package com.epm.gestepm.model.holiday.service.mapper;

import com.epm.gestepm.modelapi.deprecated.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.deprecated.country.service.CountryServiceOld;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayDTO;
import com.epm.gestepm.modelapi.holiday.dto.YearCalendarDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HolidayMapper {

	private static final Log log = LogFactory.getLog(HolidayMapper.class);
	
	private HolidayMapper() {
		
	}

	public static Holiday mapDTOToHoliday(HolidayDTO holidayDTO, CountryServiceOld countryServiceOld, ActivityCenterService activityCenterService) {
		
		final Holiday holiday = new Holiday();

		holiday.setName(holidayDTO.getName());
		holiday.setDay(holidayDTO.getDay());
		holiday.setMonth(holidayDTO.getMonth());
		holiday.setCountry(holidayDTO.getCountry() != null ? countryServiceOld.getById(holidayDTO.getCountry()) : null);
		holiday.setActivityCenter(holidayDTO.getActivityCenter() != null ? activityCenterService.getById(holidayDTO.getActivityCenter()) : null);
		
		return holiday;
	}

	public static List<YearCalendarDTO> mapUserHolidaysToYearCalendarDTOs(List<UserHoliday> userHolidays) {

		final List<YearCalendarDTO> yearCalendarDTOs = new ArrayList<>();

		for (UserHoliday userHoliday : userHolidays) {

			final YearCalendarDTO yearCalendarDto = mapUserHolidayToYearCalendarDTO(userHoliday);

			yearCalendarDTOs.add(yearCalendarDto);
		}

		return yearCalendarDTOs;
	}

	public static YearCalendarDTO mapUserHolidayToYearCalendarDTO(UserHoliday userHoliday) {

		String holidayColor = Constants.DEFAULT_COLOR;

		if (Constants.STATUS_APPROVED.equals(userHoliday.getStatus())) {
			holidayColor = Constants.APPROVED_COLOR;
		} else if (Constants.STATUS_REJECTED.equals(userHoliday.getStatus())) {
			holidayColor = Constants.REJECTED_COLOR;
		}

		final String fullName = userHoliday.getUser().getName() + " " + userHoliday.getUser().getSurnames();

		final List<String> usernames = new ArrayList<>();
		usernames.add(fullName);

		return new YearCalendarDTO(userHoliday.getId(), holidayColor, userHoliday.getDate(), usernames);
	}

	public static List<UserHoliday> mapCreateDatesToUserHolidays(List<Date> dates, User user) {

		final List<UserHoliday> userHolidays = new ArrayList<>();

		dates.forEach(d -> {

			final UserHoliday userHoliday = new UserHoliday();
			userHoliday.setUser(user);
			userHoliday.setDate(d);
			userHoliday.setStatus(Constants.STATUS_PENDING);

			userHolidays.add(userHoliday);
		});

		return userHolidays;
	}
	
	public static String mapAndSerializeHolidaysToJson(List<Holiday> holidays) {
		JSONArray arr = new JSONArray();
	    JSONObject tmp;

		for (Holiday holiday : holidays) {
			try {
				tmp = new JSONObject();
				tmp.put("id", holiday.getId());
				tmp.put("name", holiday.getName());
				tmp.put("month", holiday.getMonth());
				tmp.put("day", holiday.getDay());
				arr.put(tmp);
			} catch (Exception e) { log.error(e); }
		}
		
		return arr.toString();
	}
}
