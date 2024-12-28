package com.epm.gestepm.modelapi.interventionprshare.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class InterventionPrDTO {

	private Long id;

	private Long projectId;
	
	private Long dispShareId;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;

	private Long secondTechnical;
	
	private String observations;
	
	private List<MultipartFile> files;
	
	private Boolean clientNotif;
	
	private String signature;
	
	private String signatureOp;
	
	private String materials;
	
	private String mrSignature;

}
