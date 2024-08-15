package com.epm.gestepm.model.absencetype.service.mapper;

import java.util.Date;

import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;

public class AbsencesMapper {

	private AbsencesMapper() {
		
	}

	public static UserAbsence mapFormToUserAbsence(User user, Long absenceTypeId, Date date, SingletonUtil singletonUtil) {
		UserAbsence userAbsence = new UserAbsence();
		
		userAbsence.setDate(date);
		userAbsence.setAbsenceType(singletonUtil.getAbsenceTypeById(absenceTypeId));
		userAbsence.setUser(user);
		
		return userAbsence;
	}
}
