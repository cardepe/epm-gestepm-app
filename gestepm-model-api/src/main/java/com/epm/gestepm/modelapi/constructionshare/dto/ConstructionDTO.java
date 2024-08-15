package com.epm.gestepm.modelapi.constructionshare.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class ConstructionDTO {

	private Long id;
	
	private Long projectId;
	
	private Long dispShareId;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime startDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime endDate;
	
	private String observations;
	
	private List<MultipartFile> files;
	
	private Boolean clientNotif;
	
	private String signatureOp;
	
	private String materials;
	
	private String mrSignature;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getDispShareId() {
		return dispShareId;
	}

	public void setDispShareId(Long dispShareId) {
		this.dispShareId = dispShareId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public Boolean getClientNotif() {
		return clientNotif;
	}

	public void setClientNotif(Boolean clientNotif) {
		this.clientNotif = clientNotif;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getSignatureOp() {
		return signatureOp;
	}

	public void setSignatureOp(String signatureOp) {
		this.signatureOp = signatureOp;
	}

	public String getMrSignature() {
		return mrSignature;
	}

	public void setMrSignature(String mrSignature) {
		this.mrSignature = mrSignature;
	}
}
