package com.epm.gestepm.modelapi.expense.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class ExpenseDTO {

	private Long id;

	@DateTimeFormat(iso = ISO.DATE)
	private Date startDate;

	@DateTimeFormat(iso = ISO.DATE)
	private Date endDate;

	private String justification;
	private Double kms;
	private double total;
	private Long priceType;
	private Long paymentType;
	private List<FileDTO> files;

	public ExpenseDTO() {

	}

	public ExpenseDTO(Long id, Date startDate, Date endDate, String justification, Double kms, double total, Long project,
			Long priceType, Long paymentType, List<FileDTO> files) {
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.justification = justification;
		this.kms = kms;
		this.total = total;
		this.priceType = priceType;
		this.paymentType = paymentType;
		this.files = files;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public Double getKms() {
		return kms;
	}

	public void setKms(Double kms) {
		this.kms = kms;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Long getPriceType() {
		return priceType;
	}

	public void setPriceType(Long priceType) {
		this.priceType = priceType;
	}

	public Long getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Long paymentType) {
		this.paymentType = paymentType;
	}

	public List<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}

}
