package com.epm.gestepm.model.userholiday.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;

import java.util.List;

public interface UserHolidaysRepositoryCustom {

	List<UserHoliday> findHolidaysByUserId(Long userId, Integer year);

	List<UserHolidayDTO> findUserHolidaysDTOsByUserId(Long userId, PaginationCriteria pagination);

	Long findUserHolidaysCountByUserId(Long userId);

}
