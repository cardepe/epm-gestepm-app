package com.epm.gestepm.modelapi.usersigning.service;

import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

import java.time.LocalDateTime;
import java.util.List;

public interface UserSigningService {

	UserSigning getById(Long id);
	
	UserSigning save(UserSigning userSigning);
	
	List<UserSigning> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);
}
