package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.modelapi.common.utils.CalendarDTO;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.common.helpers.DatesModel;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningResumeDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import java.sql.Timestamp;
import java.util.*;

public class SigningMapper {

	public static List<CalendarDTO> mapConstructionSharesToCalendarDTOs(List<ConstructionShare> constructionShares, MessageSource messageSource, Locale locale) {

		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (ConstructionShare constructionShare : constructionShares) {
			
			String id = constructionShare.getId() + "_cs";
			String startDate = Utiles.transformDateToString(constructionShare.getStartDate());
			String endDate = Utiles.transformDateToString(constructionShare.getEndDate());
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.construction.title", null, locale), startDate, endDate, Constants.SIGNIN_CONSTRUCTION_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapDisplacementSharesToCalendarDTOs(List<DisplacementShare> displacementShares, MessageSource messageSource, Locale locale) {

		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (DisplacementShare displacementShare : displacementShares) {
			
			String id = displacementShare.getId() + "_ds";
			
			Calendar datee = Calendar.getInstance();
			datee.setTime(displacementShare.getDisplacementDate());
			long t = datee.getTimeInMillis();
			Date afterAddingMins = new Date(t + (displacementShare.getManualHours() * 60000));
			
			String startDate = Utiles.transformDateToString(displacementShare.getDisplacementDate());
			String endDate = Utiles.transformDateToString(afterAddingMins);
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.displacement.title", null, locale), startDate, endDate, Constants.SIGNIN_DISPLACEMENT_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}
	
	public static List<CalendarDTO> mapInterventionPrSharesToCalendarDTOs(List<InterventionPrShare> interventionPrShares, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (InterventionPrShare interventionPrShare : interventionPrShares) {
			
			String id = interventionPrShare.getId() + "_ips";
			
			String startDate = Utiles.transformDateToString(interventionPrShare.getStartDate());
			String endDate = Utiles.transformDateToString(interventionPrShare.getEndDate());
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.pr.intervention.title", null, locale), startDate, endDate, Constants.SIGNIN_INTERVENTION_PR_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapInterventionSharesToCalendarDTOs(List<InterventionSubShare> interventionSubShares, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (InterventionSubShare interventionShare : interventionSubShares) {
			
			String id = interventionShare.getId() + "_is";
			
			String startDate = Utiles.transformToString(interventionShare.getStartDate());
			String endDate = Utiles.transformToString(interventionShare.getEndDate());
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.no.intervention.title", null, locale), startDate, endDate, Constants.SIGNIN_INTERVENTION_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapPersonalSigningsToCalendarDTOs(List<PersonalSigning> personalSignings, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (PersonalSigning personalSigning : personalSignings) {
			
			String id = personalSigning.getId() + "_ps";
			
			String startDate = Utiles.transformDateToString(personalSigning.getStartDate());
			String endDate = Utiles.transformDateToString(personalSigning.getEndDate());
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("signing.personal.title", null, locale), startDate, endDate, Constants.SIGNIN_PERSONAL_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}

	public static List<CalendarDTO> mapWorkSharesToCalendarDTOs(List<WorkShare> workShares, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (WorkShare workShare : workShares) {
			
			String id = workShare.getId() + "_ws";
			
			String startDate = Utiles.transformDateToString(workShare.getStartDate());
			String endDate = Utiles.transformDateToString(workShare.getEndDate());
			
			CalendarDTO calendarDTO = new CalendarDTO(id, messageSource.getMessage("shares.work.titlee", null, locale), startDate, endDate, Constants.SIGNIN_WORK_COLOR, "#ffffff");
			calendarDTOs.add(calendarDTO);
		}
		
		return calendarDTOs;
	}
	
	public static List<CalendarDTO> mapUserSigningsToCalendarDTOs(List<UserSigning> userSignings, MessageSource messageSource, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		for (UserSigning userSigning : userSignings) {
			
			String id = userSigning.getId() + "_us";
			
			String startDate = Utiles.transformDateToString(userSigning.getStartDate());
			String endDate = Utiles.transformDateToString(userSigning.getEndDate());
			
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

			String startDate = Utiles.transformDateToString(userManualSigning.getStartDate());
			String endDate = Utiles.transformDateToString(userManualSigning.getEndDate());

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
		userSigning.setStartDate(new Timestamp(Utiles.localDateTimeToDate(userSigningDTO.getStartDate()).getTime()));

		if (userSigningDTO.getEndDate() != null) {
			userSigning.setEndDate(new Timestamp(Utiles.localDateTimeToDate(userSigningDTO.getEndDate()).getTime()));
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
			displacementCalendar.setTime(l.getDisplacementDate());
			displacementCalendar.add(Calendar.MINUTE, l.getManualHours());

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("ds");
			dto.setStartDate(l.getDisplacementDate());
			dto.setEndDate(displacementCalendar.getTime());

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
			dm.setStartDate(l.getStartDate());
			dm.setEndDate(l.getEndDate());

			listDto.add(dm);
		});

		return listDto;
	}
}
