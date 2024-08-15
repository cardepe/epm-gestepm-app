package com.epm.gestepm.model.holiday.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HolidayRepository extends CrudRepository<Holiday, Long>, HolidayRepositoryCustom {

	Holiday findByDayAndMonth(int day, int month);

	Holiday findByDayAndMonthAndActivityCenterId(int day, int month, Long activityCenterId);

	Holiday findByDayAndMonthAndCountryIdAndActivityCenterId(int day, int month, Long countryId, Long activityCenterId);

	@Query("select h from Holiday h left join ActivityCenter ac on h.activityCenter = ac where ac.id = :activityCenterId or (h.activityCenter is null and h.country.id = :countryId)")
	List<Holiday> listByActivityCenterAndCountry(@Param("activityCenterId") Long activityCenterId, @Param("countryId") Long countryId);

}
