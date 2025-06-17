package com.epm.gestepm.modelapi.deprecated.project.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class ProjectDTO {

	private Long id;
	private String projectName;
	private List<Long> responsables;
	private Double objectiveCost;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date startDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private Date objectiveDate;
	
	private Long activityCenter;
	private int station;
	private Long forumId;
	
	public ProjectDTO() {
		
	}

	public ProjectDTO(Long id, String projectName) {
		super();
		this.id = id;
		this.projectName = projectName;
	}

	public ProjectDTO(String projectName, List<Long> responsables, Double objectiveCost, Date startDate, Date objectiveDate,
			Long activityCenter, int station, Long forumId) {
		super();
		this.projectName = projectName;
		this.responsables = responsables;
		this.objectiveCost = objectiveCost;
		this.startDate = startDate;
		this.objectiveDate = objectiveDate;
		this.activityCenter = activityCenter;
		this.station = station;
		this.forumId = forumId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Long> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Long> responsables) {
		this.responsables = responsables;
	}

	public Double getObjectiveCost() {
		return objectiveCost;
	}

	public void setObjectiveCost(Double objectiveCost) {
		this.objectiveCost = objectiveCost;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getObjectiveDate() {
		return objectiveDate;
	}

	public void setObjectiveDate(Date objectiveDate) {
		this.objectiveDate = objectiveDate;
	}

	public Long getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(Long activityCenter) {
		this.activityCenter = activityCenter;
	}

	public int getStation() {
		return station;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
}
