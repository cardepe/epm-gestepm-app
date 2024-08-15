package com.epm.gestepm.modelapi.holiday.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;

public interface HolidayService {

	List<Holiday> findAll();

	List<Holiday> getHolidaysByActivityCenter(Long activityCenterId);

	void save(Holiday holiday);

	void delete(Long id);

	List<HolidayTableDTO> getHolidaysDataTables(PaginationCriteria pagination);

	Long getHolidaysCount();

	Holiday getHolidayByDayAndMonth(int day, int month);
	
	Boolean isWorkingDay(Long userId, Long countryId, Long activityCenterId, Date date);

	List<Holiday> listByActivityCenterAndCountry(Long activityCenterId, Long countryId);

}
