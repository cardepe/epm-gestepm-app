package com.epm.gestepm.modelapi.interventionsubshare.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class InterventionSubShareTableDTO {

	@JsonProperty("iss_id")
	private Long id;

	@JsonProperty("iss_orderId")
	private Long orderId;

	@JsonProperty("iss_action")
	private Integer action;

	@JsonProperty("iss_startDate")
	private OffsetDateTime startDate;

	@JsonProperty("iss_endDate")
	private OffsetDateTime endDate;

	@JsonProperty("iss_materialsFileExt")
	private String materialsFileExt;

	@JsonProperty("iss_topicId")
	private Long topicId;

	public InterventionSubShareTableDTO() {

	}

	public InterventionSubShareTableDTO(Long id, Long orderId, Integer action, OffsetDateTime startDate, OffsetDateTime endDate,
			String materialsFileExt, Long topicId) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.action = action;
		this.startDate = startDate;
		this.endDate = endDate;
		this.materialsFileExt = materialsFileExt;
		this.topicId = topicId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getAction() {
		return action;
	}

	public void setAction(Integer action) {
		this.action = action;
	}

	public OffsetDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(OffsetDateTime startDate) {
		this.startDate = startDate;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(OffsetDateTime endDate) {
		this.endDate = endDate;
	}

	public String getMaterialsFileExt() {
		return materialsFileExt;
	}

	public void setMaterialsFileExt(String materialsFileExt) {
		this.materialsFileExt = materialsFileExt;
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
