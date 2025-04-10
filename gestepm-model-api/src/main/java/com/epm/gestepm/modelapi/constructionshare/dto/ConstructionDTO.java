package com.epm.gestepm.modelapi.constructionshare.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ConstructionDTO {

	private Long id;
	
	private Long projectId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDate;
	
	private String observations;
	
	private List<MultipartFile> files;

	private Boolean clientNotif;
	
	private String signatureOp;

}
