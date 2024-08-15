package com.epm.gestepm.model.usersigning.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.model.usersigning.dao.UserSigningRepository;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

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
	public void delete(Long userSigningId) {
		userSigningRepository.deleteById(userSigningId);
	}

	@Override
	public List<UserSigningTableDTO> getUserSigningDTOsByUserId(Long userId, PaginationCriteria pagination) {
		return userSigningRepository.findUserSigningDTOsByUserId(userId, pagination);
	}
	
	@Override
	public Long getUserSigningCountByUser(Long userId) {
		return userSigningRepository.findUserSigningCountByUserId(userId);
	}
	
	@Override
	public UserSigning getByUserIdAndEndDate(Long userId, Date endDate) {
		return userSigningRepository.findByUserIdAndEndDate(userId, endDate);
	}
	
	@Override
	public List<UserSigning> getWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		return userSigningRepository.findWeekSigningsByUserId(startDate, endDate, userId);
	}
}
