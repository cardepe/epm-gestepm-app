package com.epm.gestepm.model.interventionshare.service.mapper;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareDTO;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import org.apache.commons.io.FilenameUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ShareMapper {

	public static InterventionPrShare mapDTOToInterventionPrShare(InterventionPrDTO interventionPrDTO, User user, Project project, Long dispShareId) {
		
		InterventionPrShare interventionPrShare = new InterventionPrShare();
		
		interventionPrShare.setStartDate(interventionPrDTO.getStartDate());
		interventionPrShare.setEndDate(interventionPrDTO.getEndDate());
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
		interventionPrDTO.setStartDate(interventionPrShare.getStartDate());
		interventionPrDTO.setEndDate(interventionPrShare.getEndDate());
		interventionPrDTO.setObservations(interventionPrShare.getObservations());
		interventionPrDTO.setSignature(interventionPrShare.getSignature());
		interventionPrDTO.setSignatureOp(interventionPrShare.getSignatureOp());
		
		return interventionPrDTO;
	}
	
	public static PersonalSigningDTO mapPersonalSigningToDTO(PersonalSigning personalSigning) {
		
		PersonalSigningDTO personalSigningDTO = new PersonalSigningDTO();
		
		personalSigningDTO.setId(personalSigning.getId());
		personalSigningDTO.setStartDate(personalSigning.getStartDate());
		personalSigningDTO.setEndDate(personalSigning.getEndDate());
		
		return personalSigningDTO;
	}

	public static WorkShare mapDTOToWorkShare(WorkShareDTO workShareDTO, User user, Project project) {
		
		WorkShare workShare = new WorkShare();
		
		workShare.setStartDate(workShareDTO.getStartDate());
		workShare.setEndDate(workShareDTO.getEndDate());
		workShare.setObservations(workShareDTO.getObservations());
		workShare.setProject(project);
		workShare.setUser(user);
		workShare.setMaterials(workShareDTO.getMaterials());
		workShare.setMrSignature(workShareDTO.getMrSignature());
		
		return workShare;
	}
	
	public static WorkShareDTO mapWorkShareToDTO(WorkShare workShare) {
		
		WorkShareDTO workShareDTO = new WorkShareDTO();
		
		workShareDTO.setEndDate(workShare.getEndDate());
		workShareDTO.setId(workShare.getId());
		workShareDTO.setObservations(workShare.getObservations());
		workShareDTO.setStartDate(workShare.getStartDate());
		workShareDTO.setProjectId(workShare.getProject().getId());
		
		return workShareDTO;
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
	
	public static ShareTableDTO mapObjectToShareTableDTO(Object[] obj) {

		final Timestamp startDate = ((Timestamp) obj[2]);
		final Timestamp endDate = ((Timestamp) obj[3]);

		ShareTableDTO dto = new ShareTableDTO();
		dto.setId(obj[0] + "_" + obj[5]);
		dto.setProjectId(String.valueOf(obj[1]));
		dto.setStartDate(startDate != null ? startDate.toLocalDateTime() : null);
		dto.setEndDate(endDate != null ? endDate.toLocalDateTime() : null);
		dto.setForumTitle((String) obj[4]);
		dto.setShareType((String) obj[5]);
		
		return dto;
	}
}
