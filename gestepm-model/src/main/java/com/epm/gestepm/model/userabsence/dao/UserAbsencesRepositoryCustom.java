package com.epm.gestepm.model.userabsence.dao;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsenceDTO;

public interface UserAbsencesRepositoryCustom {
	public List<UserAbsence> findAbsencesByUserId(Long userId);
	public List<UserAbsenceDTO> findUserAbsencesDTOsByUserId(Long userId, PaginationCriteria pagination);
	public Long findUserAbsencesCountByUserId(Long userId);
}
