package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.common.utils.CipherUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class UserMapper {

	private static final Log log = LogFactory.getLog(UserMapper.class);
	
	private UserMapper() {

	}

	public static User mapDTOToUser(UserDTO userDTO, ActivityCenter activityCenter, Role role, SubRole subRole) {

		try {
			
			User user = new User();
			user.setId(userDTO.getUserId());
			user.setSigningId(userDTO.getSigningId());
			user.setEmail(userDTO.getEmail());
			user.setPassword(Utiles.textToMD5(userDTO.getPassword()));
			user.setForumPassword(Base64.getEncoder().encodeToString(CipherUtil.encryptMessage(userDTO.getPassword().getBytes(), Constants.ENCRYPTION_KEY.getBytes())));
			user.setActivityCenter(activityCenter);
			user.setRole(role);
			user.setSubRole(subRole);
			user.setName(userDTO.getName());
			user.setSurnames(userDTO.getSurnames());
			user.setWorkingHours(userDTO.getWorkingHours() != null ? userDTO.getWorkingHours() : 8);
			user.setState(userDTO.getState());
			user.setCurrentYearHolidaysCount(activityCenter.getCountry().getId() == 1 ? 22 : 30);
			user.setLastYearHolidaysCount(0);
			
			return user;
			
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	public static User mapUpdateUserDTOToUser(User user, UserDTO userDTO, ActivityCenter activityCenter, Role role, SubRole subRole) {
		
		try {

			user.setSigningId(userDTO.getSigningId());
			user.setEmail(userDTO.getEmail());
			user.setPassword(Utiles.textToMD5(userDTO.getPassword()));
			user.setForumPassword(Base64.getEncoder().encodeToString(CipherUtil.encryptMessage(userDTO.getPassword().getBytes(), Constants.ENCRYPTION_KEY.getBytes())));
			user.setActivityCenter(activityCenter);
			user.setRole(role);
			user.setSubRole(subRole);
			user.setName(userDTO.getName());
			user.setSurnames(userDTO.getSurnames());
			user.setWorkingHours(userDTO.getWorkingHours());
			user.setState(userDTO.getState());

			return user;

		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	private static String stripAccents(String s) {
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		return s;
	}

	@SuppressWarnings("unused")
	private static String getUserName(String name, String surnames) {
		String userName = name.substring(0, 3).toLowerCase();
		String[] surnamesList = surnames.split(StringUtils.SPACE);

		if (surnamesList.length > 1) {
			// Concat 2 chars of each surname if multiple
			userName = userName.concat(surnamesList[0].substring(0, 2));
			userName = userName.concat(surnamesList[1].substring(0, 2));
		} else {
			// Concat 4 chars of lastName
			userName = userName.concat(surnamesList[0].substring(0, 4));
		}

		// Remove accents
		userName = stripAccents(userName);

		// Lowecase username
		return userName.toLowerCase();
	}

	@SuppressWarnings("unused")
	private static String getPassword(String name, String usernames) {
		String password = name.substring(0, 1).toUpperCase();
		String firstLetterUsername = usernames.substring(0, 1).toLowerCase();
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("ddMMyyyy");
		String strDate = formatter.format(date);

		// Concat first letter of username in lowercase
		password = password.concat(firstLetterUsername);

		// Concat date ddMMyyyy
		password = password.concat(strDate);

		// Remove accents
		password = stripAccents(password);

		// Encrypt to MD5
		return Utiles.textToMD5(password);
	}

}
