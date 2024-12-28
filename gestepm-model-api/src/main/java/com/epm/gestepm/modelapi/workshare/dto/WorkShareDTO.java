package com.epm.gestepm.modelapi.workshare.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class WorkShareDTO {

	private Long id;

	private Long projectId;
	
	private Long dispShareId;

	private OffsetDateTime startDate;

	private OffsetDateTime endDate;
	
	private String observations;
	
	private Boolean clientNotif;
	
	private List<MultipartFile> files;
	
	private String signatureOp;
	
	private String materials;
	
	private String mrSignature;

}
