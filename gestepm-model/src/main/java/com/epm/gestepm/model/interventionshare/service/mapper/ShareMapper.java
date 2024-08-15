package com.epm.gestepm.model.interventionshare.service.mapper;

import com.epm.gestepm.model.displacement.service.mapper.DisplacementMapper;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionDTO;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareDTO;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionNoPrDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialShareDTO;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareDTO;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShareMapper {

	private ShareMapper() {
		
	}
	
	public static InterventionShare mapDTOToInterventionShare(InterventionNoPrDTO interventionNoPrDTO, User user, Project project) {
		
		InterventionShare interventionShare = new InterventionShare();
		interventionShare.setUser(user);
		interventionShare.setProject(project);
		interventionShare.setNoticeDate(interventionNoPrDTO.getNoticeDate() != null ? Timestamp.valueOf(interventionNoPrDTO.getNoticeDate()) : null);

		return interventionShare;
	}
	
	public static InterventionShareFile mapMultipartFileToInterventionShareFile(MultipartFile file, InterventionShare interventionShare) throws IOException {
		String fileName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName);
		byte[] data = FileUtils.compressBytes(FileUtils.compressImage(file));

		InterventionShareFile interventionShareFile = new InterventionShareFile();
		interventionShareFile.setName(fileName);
		interventionShareFile.setExt(ext);
		interventionShareFile.setContent(data);
		interventionShareFile.setInterventionShare(interventionShare);

		return interventionShareFile;
	}
	
	public static DisplacementShare mapDTOToDisplacementShare(DisplacementShareDTO displacementShareDTO, User user, Project project, Displacement displacement) {
		
		DisplacementShare displacementShare = new DisplacementShare();
		Boolean roundTrip = BooleanUtils.isTrue(displacementShareDTO.getRoundTrip());
	
		displacementShare.setDisplacementDate(Timestamp.valueOf(displacementShareDTO.getDisplacementDate()));
		displacementShare.setManualHours(Utiles.hourTimeToMinutes(displacementShareDTO.getManualHours()));
		
		if (displacementShareDTO.getManualDisplacement() != null) {
			displacementShare.setManualDisplacement(displacementShareDTO.getManualDisplacement());
		}
		
		displacementShare.setRoundTrip(roundTrip);
		displacementShare.setObservations(displacementShareDTO.getObservations());
		displacementShare.setProject(project);
		displacementShare.setUser(user);
		displacementShare.setDisplacement(displacement);
		
		return displacementShare;
	}
	
	public static DisplacementShareDTO mapDisplacementShareToDTO(DisplacementShare displacementShare) {
		
		DisplacementShareDTO displacementShareDTO = new DisplacementShareDTO();
		
		displacementShareDTO.setDisplacementDate(Utiles.convertToLocalDateTimeViaInstant(displacementShare.getDisplacementDate()));
		displacementShareDTO.setActivityCenter(displacementShare.getDisplacement().getId());
		displacementShareDTO.setManualHours(Utiles.minutesToHoursAndMinutesString(displacementShare.getManualHours()));
		displacementShareDTO.setObservations(displacementShare.getObservations());
		displacementShareDTO.setRoundTrip(displacementShare.getRoundTrip());
		displacementShareDTO.setProjectId(String.valueOf(displacementShare.getProject().getId()));
		displacementShareDTO.setManualDisplacement(displacementShare.getManualDisplacement());
		
		DisplacementDTO displacement = DisplacementMapper.mapDisplacementToDTO(displacementShare.getDisplacement());
		displacementShareDTO.setDisplacement(displacement);
		
		return displacementShareDTO;
	}
	
	public static ConstructionShare mapDTOToConstructionShare(ConstructionDTO constructionDTO, User user, Project project, Long dispShareId) {
		
		ConstructionShare constructionShare = new ConstructionShare();
		
		constructionShare.setStartDate(constructionDTO.getStartDate() != null ? Timestamp.valueOf(constructionDTO.getStartDate()) : null);
		constructionShare.setEndDate(constructionDTO.getEndDate() != null ? Timestamp.valueOf(constructionDTO.getEndDate()) : null);
		constructionShare.setObservations(constructionDTO.getObservations());
		constructionShare.setProject(project);
		constructionShare.setUser(user);
		constructionShare.setMaterials(constructionDTO.getMaterials());
		constructionShare.setMrSignature(constructionDTO.getMrSignature());
		constructionShare.setDisplacementShareId(dispShareId);
		
		return constructionShare;
	}
	
	public static ConstructionDTO mapConstructionShareToDTO(ConstructionShare constructionShare) {
		
		ConstructionDTO constructionDTO = new ConstructionDTO();
		
		constructionDTO.setId(constructionShare.getId());
		constructionDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(constructionShare.getStartDate()));
		constructionDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(constructionShare.getEndDate()));
		constructionDTO.setObservations(constructionShare.getObservations());
		
		return constructionDTO;
	}
	
	public static InterventionDTO mapInterventionShareToDTO(InterventionSubShare interventionShare) {
		
		InterventionDTO interventionDTO = new InterventionDTO();
		
		interventionDTO.setId(interventionShare.getId());
		interventionDTO.setStartDate(interventionShare.getStartDate().toLocalDateTime());
		interventionDTO.setEndDate(interventionShare.getEndDate().toLocalDateTime());
		
		return interventionDTO;
	}

	public static InterventionPrShare mapDTOToInterventionPrShare(InterventionPrDTO interventionPrDTO, User user, Project project, Long dispShareId) {
		
		InterventionPrShare interventionPrShare = new InterventionPrShare();
		
		interventionPrShare.setStartDate(interventionPrDTO.getStartDate() != null ? Timestamp.valueOf(interventionPrDTO.getStartDate()) : null);
		interventionPrShare.setEndDate(interventionPrDTO.getEndDate() != null ? Timestamp.valueOf(interventionPrDTO.getEndDate()) : null);
		interventionPrShare.setObservations(interventionPrDTO.getObservations());
		interventionPrShare.setProject(project);
		interventionPrShare.setUser(user);
		interventionPrShare.setMaterials(interventionPrDTO.getMaterials());
		interventionPrShare.setMrSignature(interventionPrDTO.getMrSignature());
		interventionPrShare.setDisplacementShareId(dispShareId);
		
		return interventionPrShare;
	}
	
	public static InterventionPrDTO mapInterventionPrShareToDTO(InterventionPrShare interventionPrShare) {
		
		InterventionPrDTO interventionPrDTO = new InterventionPrDTO();
		
		interventionPrDTO.setId(interventionPrShare.getId());
		interventionPrDTO.setProjectId(interventionPrShare.getProject().getId());
		interventionPrDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(interventionPrShare.getStartDate()));
		interventionPrDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(interventionPrShare.getEndDate()));
		interventionPrDTO.setObservations(interventionPrShare.getObservations());
		interventionPrDTO.setSignature(interventionPrShare.getSignature());
		interventionPrDTO.setSignatureOp(interventionPrShare.getSignatureOp());
		
		return interventionPrDTO;
	}
	
	public static PersonalSigningDTO mapPersonalSigningToDTO(PersonalSigning personalSigning) {
		
		PersonalSigningDTO personalSigningDTO = new PersonalSigningDTO();
		
		personalSigningDTO.setId(personalSigning.getId());
		personalSigningDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(personalSigning.getStartDate()));
		personalSigningDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(personalSigning.getEndDate()));
		
		return personalSigningDTO;
	}

	public static WorkShare mapDTOToWorkShare(WorkShareDTO workShareDTO, User user, Project project) {
		
		WorkShare workShare = new WorkShare();
		
		workShare.setStartDate(workShareDTO.getStartDate() != null ? Timestamp.valueOf(workShareDTO.getStartDate()) : null);
		workShare.setEndDate(workShareDTO.getEndDate() != null ? Timestamp.valueOf(workShareDTO.getEndDate()) : null);
		workShare.setObservations(workShareDTO.getObservations());
		workShare.setProject(project);
		workShare.setUser(user);
		workShare.setMaterials(workShareDTO.getMaterials());
		workShare.setMrSignature(workShareDTO.getMrSignature());
		
		return workShare;
	}
	
	public static PersonalSigningDTO mapUserSigningToDTO(UserSigning userSigning) {
		
		PersonalSigningDTO userSigningShareDTO = new PersonalSigningDTO();
		
		userSigningShareDTO.setId(userSigning.getId());
		userSigningShareDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(userSigning.getStartDate()));
		userSigningShareDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(userSigning.getEndDate()));
		userSigningShareDTO.setStartLocation(userSigning.getStartLocation());
		userSigningShareDTO.setEndLocation(userSigning.getEndLocation());
		
		return userSigningShareDTO;
	}

	public static UserManualSigningDTO mapUserManualSigningToDTO(UserManualSigning userManualSigning) {

		UserManualSigningDTO userManualSigningDTO = new UserManualSigningDTO();

		userManualSigningDTO.setId(userManualSigning.getId());
		userManualSigningDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(userManualSigning.getStartDate()));
		userManualSigningDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(userManualSigning.getEndDate()));
		userManualSigningDTO.setDescription(userManualSigning.getDescription());
		userManualSigningDTO.setGeolocation(userManualSigning.getLocation());

		return userManualSigningDTO;
	}
	
	public static WorkShareDTO mapWorkShareToDTO(WorkShare workShare) {
		
		WorkShareDTO workShareDTO = new WorkShareDTO();
		
		workShareDTO.setEndDate(Utiles.convertToLocalDateTimeViaInstant(workShare.getEndDate()));
		workShareDTO.setId(workShare.getId());
		workShareDTO.setObservations(workShare.getObservations());
		workShareDTO.setStartDate(Utiles.convertToLocalDateTimeViaInstant(workShare.getStartDate()));
		workShareDTO.setProjectId(workShare.getProject().getId());
		
		return workShareDTO;
	}
	
	public static ConstructionShareFile mapMultipartFileToConstructionShareFile(MultipartFile file, ConstructionShare constructionShare) {
		String fileName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName);

		byte[] data = FileUtils.compressBytes(FileUtils.compressImage(file));

		ConstructionShareFile constructionShareFile = new ConstructionShareFile();
		constructionShareFile.setName(fileName);
		constructionShareFile.setExt(ext);
		constructionShareFile.setContent(data);
		constructionShareFile.setConstructionShare(constructionShare);

		return constructionShareFile;
	}
	
	public static InterventionSubShareFile mapMultipartFileToInterventionSubShareFile(MultipartFile file, InterventionSubShare interventionSubShare) {
		String fileName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName);

		byte[] data = FileUtils.compressBytes(FileUtils.compressImage(file));

		InterventionSubShareFile interventionSubShareFile = new InterventionSubShareFile();
		interventionSubShareFile.setName(fileName);
		interventionSubShareFile.setExt(ext);
		interventionSubShareFile.setContent(data);
		interventionSubShareFile.setInterventionSubShare(interventionSubShare);

		return interventionSubShareFile;
	}
	
	public static InterventionPrShareFile mapMultipartFileToInterventionPrShareFile(MultipartFile file, InterventionPrShare interventionPrShare) {
		String fileName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName);

		byte[] data = FileUtils.compressBytes(FileUtils.compressImage(file));

		InterventionPrShareFile interventionPrShareFile = new InterventionPrShareFile();
		interventionPrShareFile.setName(fileName);
		interventionPrShareFile.setExt(ext);
		interventionPrShareFile.setContent(data);
		interventionPrShareFile.setInterventionPrShare(interventionPrShare);

		return interventionPrShareFile;
	}
	
	public static WorkShareFile mapMultipartFileToWorkShareFile(MultipartFile file, WorkShare workShare) {
		String fileName = file.getOriginalFilename();
		String ext = FilenameUtils.getExtension(fileName);

		byte[] data = FileUtils.compressBytes(FileUtils.compressImage(file));

		WorkShareFile workShareFile = new WorkShareFile();
		workShareFile.setName(fileName);
		workShareFile.setExt(ext);
		workShareFile.setContent(data);
		workShareFile.setWorkShare(workShare);

		return workShareFile;
	}
	
	public static List<FileDTO> mapWorkShareFileToFileDTO(List<WorkShareFile> workShareFiles) {
		List<FileDTO> fileDTOs = new ArrayList<>();
		
		if (workShareFiles != null && !CollectionUtils.isEmpty(workShareFiles)) {
			
			workShareFiles.forEach(workShareFile -> {
				
				byte[] data = FileUtils.decompressBytes(workShareFile.getContent());
				
				FileDTO fileDTO = new FileDTO();
				
				fileDTO.setExt(workShareFile.getExt());
				fileDTO.setName(workShareFile.getName());
				fileDTO.setContent(data);
				
				fileDTOs.add(fileDTO);
			});
		}
		
		return fileDTOs;
	}
	
	public static InterventionShareMaterial mapMaterialDTOToEntity(MaterialShareDTO materialShareDTO, InterventionSubShare interventionSubShare) {
		
		InterventionShareMaterial entity = new InterventionShareMaterial();
		entity.setInterventionSubShare(interventionSubShare);
		entity.setDescription(materialShareDTO.getDescription());
		entity.setUnits(materialShareDTO.getUnits());
		entity.setReference(materialShareDTO.getReference());
		
		return entity;
	}
	
	public static ShareTableDTO mapObjectToShareTableDTO(Object[] obj) {
		
		ShareTableDTO dto = new ShareTableDTO();
		
		dto.setId(String.valueOf(obj[0]) + "_" + (String) obj[5]); 
		dto.setProjectId(String.valueOf(obj[1]));
		dto.setStartDate((Date) obj[2]);
		dto.setEndDate((Date) obj[3]);
		dto.setForumTitle((String) obj[4]);
		dto.setShareType((String) obj[5]);
		
		return dto;
	}
}
