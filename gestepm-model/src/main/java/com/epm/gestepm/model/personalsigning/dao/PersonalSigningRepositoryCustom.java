package com.epm.gestepm.model.personalsigning.dao;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;

public interface PersonalSigningRepositoryCustom {

	/**
	 * Get the last signing by user id
	 * @param userId
	 * @return PersonalSigning
	 */
	public PersonalSigning findLastSigningByUserId(Long userId);
	
	/**
	 * Returns week personal signings by user.
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return
	 */
	public List<PersonalSigning> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	
	/**
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public Long findYearSecondsPersonalSigningByUserIdAndYear(Long userId, int year);
	
	/**
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	public List<DailyPersonalSigningDTO> findDailyPersonalSigningDTOByUserIdAndYear(Long userId, int year);
}
