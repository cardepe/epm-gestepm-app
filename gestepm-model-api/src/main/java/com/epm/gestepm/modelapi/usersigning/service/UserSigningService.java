package com.epm.gestepm.modelapi.usersigning.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningTableDTO;

public interface UserSigningService {

	UserSigning getById(Long id);
	
	UserSigning save(UserSigning userSigning);

	void delete(Long userSigningId);
	
	List<UserSigningTableDTO> getUserSigningDTOsByUserId(Long userId, PaginationCriteria pagination);

	Long getUserSigningCountByUser(Long userId);

	UserSigning getByUserIdAndEndDate(Long userId, Date endDate);
	
	List<UserSigning> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId);
}
