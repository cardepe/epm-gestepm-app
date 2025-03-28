package com.epm.gestepm.model.usersigning.service;

import com.epm.gestepm.model.usersigning.dao.UserSigningRepository;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserSigningServiceImpl implements UserSigningService {

	@Autowired
	private UserSigningRepository userSigningRepository;
	
	@Override
	public UserSigning getById(Long id) {
		return userSigningRepository.findById(id).orElse(null);
	}
	
	@Override
	public UserSigning save(UserSigning userSigning) {	
		return userSigningRepository.save(userSigning);
	}
	
	@Override
	public List<UserSigning> getWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
		return userSigningRepository.findWeekSigningsByUserId(startDate, endDate, userId);
	}
}
