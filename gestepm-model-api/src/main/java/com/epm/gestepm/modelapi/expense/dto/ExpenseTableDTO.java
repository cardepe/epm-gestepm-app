package com.epm.gestepm.modelapi.expense.dto;

import com.epm.gestepm.modelapi.common.utils.JspUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ExpenseTableDTO {

	@JsonProperty("ex_id")
	private Long id;

	@JsonProperty("pi_description")
	private String description;

	@JsonProperty("ex_justification")
	private String justification;

	@JsonProperty("ex_reportDate")
	private Date reportDate;

	@JsonProperty("ex_total")
	private Double total;

	public ExpenseTableDTO() {

	}

	public ExpenseTableDTO(Long id, String description, String justification, Date reportDate, Double total) {
		super();

		JspUtil jspUtil = new JspUtil();

		this.id = id;
		this.description = jspUtil.parseTagToText(description);
		this.justification = justification;
		this.reportDate = reportDate;
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
