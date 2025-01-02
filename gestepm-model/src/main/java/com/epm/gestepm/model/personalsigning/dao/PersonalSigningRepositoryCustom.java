package com.epm.gestepm.model.personalsigning.dao;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonalSigningRepositoryCustom {

	PersonalSigning findLastSigningByUserId(Long userId);
	List<PersonalSigning> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
