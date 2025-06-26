package com.epm.gestepm.model.deprecated.holiday.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.epm.gestepm.model.deprecated.holiday.dao.HolidayRepository;
import com.epm.gestepm.model.userholiday.dao.UserHolidaysRepository;
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.holiday.service.HolidayService;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class HolidayServiceImpl implements HolidayService {

	@Autowired
	private HolidayRepository holidayRepository;
	
	@Autowired
	private UserHolidaysRepository userHolidaysRepository;
	
	@Override
	public List<Holiday> findAll() {
		return (List<Holiday>) holidayRepository.findAll();
	}
	
	@Override
	public List<Holiday> getHolidaysByActivityCenter(Long activityCenterId) {
		return holidayRepository.findHolidaysByActivityCenter(activityCenterId);
	}
	
	@Override
	public void save(Holiday holiday) {
		holidayRepository.save(holiday);	
	}
	
	@Override
	public void delete(Long id) {
		holidayRepository.deleteById(id);
	}
	
	@Override
	public List<HolidayTableDTO> getHolidaysDataTables(PaginationCriteria pagination) {
		return holidayRepository.findHolidaysDataTables(pagination);
	}
	
	@Override
	public Long getHolidaysCount() {
		return holidayRepository.findHolidaysCount();
	}
	
	@Override
	public Holiday getHolidayByDayAndMonth(int day, int month) {
		return holidayRepository.findByDayAndMonth(day, month);
	}
	
	@Override
	public Boolean isWorkingDay(Long userId, Long countryId, Long activityCenterId, Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		// Si es fin de semana
		if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||  cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			return false;
		}
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		
		// Fiesta del Centro de Actividad
		Holiday holidayByActivity = holidayRepository.findByDayAndMonthAndActivityCenterId(day, month, activityCenterId);
		
		if (holidayByActivity != null) {
			return false;
		}
		
		// Fiesta del país
		Holiday holidayByCountry = holidayRepository.findByDayAndMonthAndCountryIdAndActivityCenterId(day, month, countryId, null);
		
		if (holidayByCountry != null) {
			return false;
		}
		
		// Vacaciones del usuario
		UserHoliday userHoliday = userHolidaysRepository.findByUserIdAndDateAndStatus(userId, date, Constants.STATUS_APPROVED);
		
		if (userHoliday != null) {
			return false;
		}
		
		return true;
	}

	@Override
	public List<Holiday> listByActivityCenterAndCountry(Long activityCenterId, Long countryId) {
		return this.holidayRepository.listByActivityCenterAndCountry(activityCenterId, countryId);
	}
}
