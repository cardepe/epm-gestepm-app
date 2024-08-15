package com.epm.gestepm.modelapi.expensesheet.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ExpenseSheetTableDTO {

	@SerializedName("es_id")
	private Long id;

	@SerializedName("es_name")
	private String name;

	@SerializedName("pr_projectName")
	private String projectName;

	@SerializedName("es_status")
	private String status;

	@SerializedName("es_creationDate")
	private Date creationDate;

	@SerializedName("es_total")
	private Double total;

	public ExpenseSheetTableDTO() {

	}

	public ExpenseSheetTableDTO(Long id, String name, String projectName, String status, Date creationDate,
			Double total) {
		super();

		JspUtil jspUtil = new JspUtil();

		this.id = id;
		this.name = name;
		this.projectName = projectName;
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

	public String getProjectName() {
		return projectName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
