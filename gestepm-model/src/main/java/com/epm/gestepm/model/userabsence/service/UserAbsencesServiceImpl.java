package com.epm.gestepm.model.userabsence.service;

import java.util.Date;
import java.util.List;

import com.epm.gestepm.model.userabsence.dao.UserAbsencesRepository;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.modelapi.userabsence.service.UserAbsencesService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class UserAbsencesServiceImpl implements UserAbsencesService {

	@Autowired
	private UserAbsencesRepository userAbsencesRepository;
	
	@Override
	public UserAbsence save(UserAbsence userAbsence) {	
		return userAbsencesRepository.save(userAbsence);
	}
	
	@Override
	public void saveAbsences(List<UserAbsence> userAbsences, Long userId) {	
		List <UserAbsence> myUserAbsences = userAbsencesRepository.findAbsencesByUserId(userId);
		
		for (UserAbsence userAbsence: userAbsences) {
			
			UserAbsence userAbsenceFound = findUserAbsenceByDate(myUserAbsences, userAbsence.getDate());
			
			// UserAbsence currently in db
			if (userAbsenceFound != null) {	
				
				// UserAbsence with same date and different absenceType in db
				if (!userAbsenceFound.getAbsenceType().equals(userAbsence.getAbsenceType())) {
					
					// Update absence type from absence
					userAbsencesRepository.save(userAbsence);
				}
				
				myUserAbsences.remove(userAbsenceFound);
				
			} else {
				
				// Save new absences
				userAbsencesRepository.save(userAbsence);
			}
		}
		
		// Delete from db absences removed
		for (UserAbsence myUserAbsence: myUserAbsences) {
			userAbsencesRepository.deleteById(myUserAbsence.getId());
		}
	}
	
	@Override
	public List<UserAbsence> getAbsencesByUser(Long userId) {
		return userAbsencesRepository.findAbsencesByUserId(userId);
	}
	
	private UserAbsence findUserAbsenceByDate(List<UserAbsence> userAbsences, Date date) {
		return userAbsences.stream()
				.filter(userAbsence -> userAbsence.getDate().equals(date))
				.findAny()
				.orElse(null);
	}

	@Override
	public void deleteById(Long userAbsenceId) {
		userAbsencesRepository.deleteById(userAbsenceId);
	}

	@Override
	public List<UserAbsenceDTO> getUserAbsencesDTOsByUserId(Long userId, PaginationCriteria pagination) {
		return userAbsencesRepository.findUserAbsencesDTOsByUserId(userId, pagination);
	}
	
	@Override
	public Long getUserAbsencesCountByUser(Long userId) {
		return userAbsencesRepository.findUserAbsencesCountByUserId(userId);
	}
}
