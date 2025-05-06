package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.modelapi.common.helpers.DatesModel;
import com.epm.gestepm.modelapi.deprecated.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.deprecated.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningResumeDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;

import java.time.ZoneOffset;
import java.util.*;

public class SigningMapper {

	public static List<PersonalSigningResumeDTO> mapConstructionShareToPSRDTO(final List<ConstructionShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("cs");
			dto.setStartDate(l.getCreatedAt());
			dto.setEndDate(l.getClosedAt());

			listDto.add(dto);
		});

		return listDto;
	}

	public static List<PersonalSigningResumeDTO> mapDisplacementShareToPSRDTO(final List<DisplacementShare> list) {

		final List<PersonalSigningResumeDTO> listDto = new ArrayList<>();

		list.stream().forEach(l -> {

			final PersonalSigningResumeDTO dto = new PersonalSigningResumeDTO();
			dto.setProjectName(l.getProject().getName());
			dto.setType("ds");
			dto.setStartDate(l.getStartDate());
			dto.setEndDate(l.getEndDate());

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
