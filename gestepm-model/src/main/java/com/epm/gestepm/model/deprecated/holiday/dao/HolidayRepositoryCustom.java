package com.epm.gestepm.model.deprecated.holiday.dao;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;

public interface HolidayRepositoryCustom {

	List<Holiday> findHolidaysByActivityCenter(Long activityCenterId);

	List<HolidayTableDTO> findHolidaysDataTables(PaginationCriteria pagination);

	Long findHolidaysCount();

}
