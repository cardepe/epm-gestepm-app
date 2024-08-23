package com.epm.gestepm.modelapi.displacementshare.dto;

import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "displacement_shares")
public class DisplacementShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID", nullable = false)
	private Project project;
	
	@Column(name = "MANUAL_DISPLACEMENT", nullable = false)
	private int manualDisplacement;
	
	@Column(name = "ORIGINAL_DATE", nullable = false)
	private Timestamp originalDate;
	
	@Column(name = "DISPLACEMENT_DATE", nullable = false)
	private Date displacementDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISPLACEMENT_ID", referencedColumnName = "displacement_id", nullable = false)
	private Displacement displacement;
	
	@Column(name = "MANUAL_HOURS", nullable = false, length = 11)
	private int manualHours;
	
	@Column(name="OBSERVATIONS")
    private String observations;
	
	@Column(name = "ROUND_TRIP")
	private Boolean roundTrip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getManualDisplacement() {
		return manualDisplacement;
	}

	public void setManualDisplacement(int manualDisplacement) {
		this.manualDisplacement = manualDisplacement;
	}

	public Timestamp getOriginalDate() {
		return originalDate;
	}

	public void setOriginalDate(Timestamp originalDate) {
		this.originalDate = originalDate;
	}

	public Date getDisplacementDate() {
		return displacementDate;
	}

	public void setDisplacementDate(Date displacementDate) {
		this.displacementDate = displacementDate;
	}

	public Displacement getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Displacement displacement) {
		this.displacement = displacement;
	}

	public int getManualHours() {
		return manualHours;
	}

	public void setManualHours(int manualHours) {
		this.manualHours = manualHours;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Boolean getRoundTrip() {
		return roundTrip;
	}

	public void setRoundTrip(Boolean roundTrip) {
		this.roundTrip = roundTrip;
	}	
}
