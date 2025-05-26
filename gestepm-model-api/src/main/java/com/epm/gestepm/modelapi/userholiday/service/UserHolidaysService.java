package com.epm.gestepm.modelapi.userholiday.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.holiday.dto.YearCalendarDTO;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;

import java.util.Date;
import java.util.List;

public interface UserHolidaysService {

	UserHoliday save(UserHoliday userHoliday);
	
	UserHoliday getUserHolidayById(Long userHolidayId);

	void createHolidays(List<Date> holidays, User user) throws Exception;

	void deleteHolidays(List<Date> holidays, User user);

	List<UserHoliday> getHolidaysByUser(Long userId, Integer year);

	List<YearCalendarDTO> getHolidaysByUserList(List<Long> userIds, Integer year);

	List<UserHolidayDTO> getUserHolidaysDTOsByUserId(Long userId, PaginationCriteria pagination);

	void deleteById(Long userHolidayId);

	Long getUserHolidaysCountByUser(Long userId);

}
