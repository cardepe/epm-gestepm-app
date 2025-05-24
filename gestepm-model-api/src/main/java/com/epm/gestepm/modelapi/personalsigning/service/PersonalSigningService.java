package com.epm.gestepm.modelapi.personalsigning.service;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.userold.dto.User;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface PersonalSigningService {
	
	PersonalSigning save(PersonalSigning personalSigning);
	PersonalSigning getById(Long id);
	List<PersonalSigning> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);

	XSSFWorkbook generateSigningSheetExcel(final Integer month, final Integer year, final User user, final Locale locale);
	XSSFWorkbook generateSigningSheetWoffuExcel(final Integer month, final Integer year, final User user, final Locale locale);
}
