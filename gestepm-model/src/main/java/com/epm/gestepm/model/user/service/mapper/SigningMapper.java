package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.modelapi.common.helpers.DatesModel;
import com.epm.gestepm.modelapi.common.utils.CalendarDTO;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningResumeDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class SigningMapper {

	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

	public static List<CalendarDTO> mapDisplacementSharesToCalendarDTOs(List<DisplacementShare> displacementShares, MessageSource messageSource, Locale locale) {

		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (DisplacementShare displacementShare : displacementShares) {
			
			String id = displacementShare.getId() + "_ds";
			
			Calendar datee = Calendar.getInstance();
			datee.setTime(Date.from(displacementShare.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			long t = datee.getTimeInMillis();
			Date afterAddingMins = new Date(t + (displacementShare.getManualHours() * 60000));
			
			String startDate = Utiles.transformDateToString(Date.from(displacementShare.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			String endDate = Utiles.transformDateToString(afterAddingMins);
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.displacement.title", null, locale), startDate, endDate, Constants.SIGNIN_DISPLACEMENT_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapPersonalSigningsToCalendarDTOs(List<PersonalSigning> personalSignings, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (PersonalSigning personalSigning : personalSignings) {
			
			String id = personalSigning.getId() + "_ps";
			
			String startDate = Utiles.transform(personalSigning.getStartDate(), DATE_FORMAT);
			String endDate = Utiles.transform(personalSigning.getEndDate(), DATE_FORMAT);
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("signing.personal.title", null, locale), startDate, endDate, Constants.SIGNIN_PERSONAL_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}
	
	public static List<CalendarDTO> mapUserSigningsToCalendarDTOs(List<UserSigning> userSignings, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (UserSigning userSigning : userSignings) {
			
			String id = userSigning.getId() + "_us";

			String startDate = Utiles.transform(userSigning.getStartDate(), DATE_FORMAT);
			String endDate = Utiles.transform(userSigning.getEndDate(), DATE_FORMAT);
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("signing.calendar.title", null, locale), startDate, endDate, Constants.SIGNIN_USER_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapUserManualSigningsToCalendarDTOs(List<UserManualSigning> userManualSignings, MessageSource messageSource, Locale locale) {

		List<CalendarDTO> calendarDTOs = new ArrayList<>();

		for (UserManualSigning userManualSigning : userManualSignings) {

			String id = userManualSigning.getId() + "_ums";

			final String name = userManualSigning.getManualSigningType().getName();

			String startDate = Utiles.transform(userManualSigning.getStartDate(), DATE_FORMAT);
			String endDate = Utiles.transform(userManualSigning.getEndDate(), DATE_FORMAT);

			CalendarDTO calendarDTO = new CalendarDTO(id, name, startDate, endDate, Constants.SIGNIN_MANUAL_USER_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}

		return calendarDTOs;
	}
	
	public static UserSigning mapUserSigningDTOToEntity(UserSigningDTO userSigningDTO, User user, Project project) {
		
		final UserSigning userSigning = new UserSigning();
		userSigning.setId(userSigningDTO.getId());
		userSigning.setUser(user);
		userSigning.setProject(project);
		userSigning.setStartDate(userSigningDTO.getStartDate());

		if (userSigningDTO.getEndDate() != null) {
			userSigning.setEndDate(userSigningDTO.getEndDate());
		}

		userSigning.setMaterials(StringUtils.isEmpty(userSigningDTO.getMaterials()) ? null : userSigningDTO.getMaterials());
		userSigning.setMrSignature(StringUtils.isEmpty(userSigningDTO.getMrSignature()) ? null : userSigningDTO.getMrSignature());
		userSigning.setDisplacementShareId(userSigningDTO.getDispShareId());
		
		return userSigning;
	}

	public static List<PersonalSigningResumeDTO> mapConstructionShareToPSRDTO(final List<ConstructionShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("cs");
			dto.setStartDate(l.getStartDate());
			dto.setEndDate(l.getEndDate());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapDisplacementShareToPSRDTO(final List<DisplacementShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final Calendar displacementCalendar = Calendar.getInstance();
			displacementCalendar.setTime(Date.from(l.getDisplacementDate().toInstant(ZoneOffset.UTC)));
			displacementCalendar.add(Calendar.MINUTE, l.getManualHours());

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("ds");
			dto.setStartDate(l.getDisplacementDate());
			dto.setEndDate(LocalDateTime.from(displacementCalendar.getTime().toInstant().atOffset(ZoneOffset.UTC)));

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapInterventionShareToPSRDTO(final List<InterventionShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("is");
			dto.setStartDate(l.getNoticeDate());
			dto.setEndDate(l.getEndDate());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapInterventionPrShareToPSRDTO(final List<InterventionPrShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("ips");
			dto.setStartDate(l.getStartDate());
			dto.setEndDate(l.getEndDate());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapPersonalSigningToPSRDTO(final List<PersonalSigning> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName("-");
			dto.setType("ps");
			dto.setStartDate(l.getStartDate());
			dto.setEndDate(l.getEndDate());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapWorkShareToPSRDTO(final List<WorkShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("ws");
			dto.setStartDate(l.getStartDate());
			dto.setEndDate(l.getEndDate());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<DatesModel> mapPersonalSigningResumeToDM(final List<PersonalSigningResumeDTO> list) {

		final List<DatesModel> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final DatesModel dm = new DatesModel();
			dm.setStartDate(Date.from(l.getStartDate().toInstant(ZoneOffset.UTC)));
			dm.setEndDate(Date.from(l.getEndDate().toInstant(ZoneOffset.UTC)));

			listDto.add(dm);
		});

		return listDto;
	}
}
