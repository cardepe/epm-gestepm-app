package com.epm.gestepm.modelapi.project.dto;

import java.util.Date;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.google.gson.annotations.SerializedName;

public class ProjectExpenseSheetDTO {

	@SerializedName("es_id")
	private Long id;

	@SerializedName("es_name")
	private String name;
	
	@SerializedName("us_fullName")
	private String fullName;

	@SerializedName("es_status")
	private String status;

	@SerializedName("es_creationDate")
	private Date creationDate;

	@SerializedName("es_total")
	private Double total;
	
	public ProjectExpenseSheetDTO() {
		
	}

	public ProjectExpenseSheetDTO(Long id, String name, String fullName, String status, Date creationDate,
			Double total) {
		super();
		
		JspUtil jspUtil = new JspUtil();
		
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.status = jspUtil.parseTagToText(status);
		this.creationDate = creationDate;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	
}
