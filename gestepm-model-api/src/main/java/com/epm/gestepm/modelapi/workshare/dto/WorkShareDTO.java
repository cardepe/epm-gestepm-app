package com.epm.gestepm.modelapi.workshare.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WorkShareDTO {

	private Long id;

	private Long projectId;
	
	private Long dispShareId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDate;
	
	private String observations;
	
	private Boolean clientNotif;
	
	private List<MultipartFile> files;
	
	private String signatureOp;
	
	private String materials;
	
	private String mrSignature;

}
