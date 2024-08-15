package com.epm.gestepm.modelapi.interventionshare.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.epm.gestepm.modelapi.materialrequired.dto.MaterialShareDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Data
public class InterventionNoPrDTO {

	private Long projectId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime noticeDate;
	
	private Integer action;
	
	private Long secondTechnical;
	
	private Long family;
	
	private Long subFamily;
	
	private List<MultipartFile> files;
	
	private MultipartFile materialsFile;
	
	private Long mechanicalProblem;
	
	private Long electricProblem;
	
	private String description;
	
	private String signature;
	
	private String signatureOp;
	
	private String clientName;
	
	private String materialsRequired;
	
	private String mrSignature;
	
	private List<MaterialShareDTO> materials;
	
	private String clientNotif;
	
	private Long dispShareId;

	private Integer equipmentHours;

}
