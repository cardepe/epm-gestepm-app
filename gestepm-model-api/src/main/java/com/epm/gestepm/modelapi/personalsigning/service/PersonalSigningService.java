package com.epm.gestepm.modelapi.personalsigning.service;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface PersonalSigningService {
	
	PersonalSigning save(PersonalSigning personalSigning);
	
	PersonalSigning getById(Long id);
	
	/**
	 * The entity is created and inserted into database.
	 * @param user
	 * @return boolean
	 */
	PersonalSigning createPersonalSigning(User user);
	
	/**
	 * Load the last signing.
	 * @param userId
	 * @return PersonalSigning
	 */
	PersonalSigning getLastSigning(Long userId);
	
	/**
	 * Returns if is a new signing or not.
	 * @param personalSigning
	 * @return boolean
	 */
	boolean isNewSigning(PersonalSigning personalSigning);
	
	/**
	 * Returns week personal signings by user.
	 * @param startDate
	 * @param endDate
	 * @param userId
	 * @return List<PersonalSigning>
	 */
	List<PersonalSigning> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
	
	/**
	 * 
	 * @param userId
	 * @param year
	 * @return
	 */
	List<DailyPersonalSigningDTO> getDailyPersonalSigningDTOByUserIdAndYear(Long userId, int year);
	
	/**
	 * Generate user personal signing
	 * @param user
	 * @param locale
	 * @return XSSFWorkbook
	 */
	XSSFWorkbook generateSigningSheetExcel(final Integer month, final Integer year, final User user, final Locale locale);

	XSSFWorkbook generateSigningSheetWoffuExcel(final Integer month, final Integer year, final User user, final Locale locale);
}
