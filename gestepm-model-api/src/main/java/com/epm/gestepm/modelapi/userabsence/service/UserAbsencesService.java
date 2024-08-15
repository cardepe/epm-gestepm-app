package com.epm.gestepm.modelapi.userabsence.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsenceDTO;

public interface UserAbsencesService {

	UserAbsence save(UserAbsence userAbsence);

	void saveAbsences(List<UserAbsence> userAbsences, Long userId);

	List<UserAbsence> getAbsencesByUser(Long userId);

	List<UserAbsenceDTO> getUserAbsencesDTOsByUserId(Long userId, PaginationCriteria pagination);

	void deleteById(Long userAbsenceId);

	Long getUserAbsencesCountByUser(Long userId);

}
