package com.epm.gestepm.model.userholiday.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;

public interface UserHolidaysRepository extends CrudRepository<UserHoliday, Long>, UserHolidaysRepositoryCustom {

	UserHoliday findByUserIdAndDateAndStatus(Long userId, Date date, String status);
}
