package com.epm.gestepm.modelapi.usersigning.dto;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_signings")
public class UserSigning {

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

	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;
	
	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	@Column(name = "MATERIALS", length = 100)
	private String materials;
	
	@Column(name = "MR_SIGNATURE")
	private String mrSignature;
	
	@Column(name = "DISPLACEMENT_SHARE_ID")
	private Long displacementShareId;

	@Column(name = "START_LOCATION")
	private String startLocation;

	@Column(name = "END_LOCATION")
	private String endLocation;
	
	@OneToMany(mappedBy = "userSigning", fetch = FetchType.LAZY)
	private List<ConstructionShare> constructionShares;
	
	@OneToMany(mappedBy = "userSigning", fetch = FetchType.LAZY)
	private List<InterventionShare> interventionShares;
	
	@OneToMany(mappedBy = "userSigning", fetch = FetchType.LAZY)
	private List<InterventionPrShare> interventionPrShares;
	
	@OneToMany(mappedBy = "userSigning", fetch = FetchType.LAZY)
	private List<WorkShare> workShares;

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

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getMaterials() {
		return materials;
	}

	public void setMaterials(String materials) {
		this.materials = materials;
	}

	public String getMrSignature() {
		return mrSignature;
	}

	public void setMrSignature(String mrSignature) {
		this.mrSignature = mrSignature;
	}

	public Long getDisplacementShareId() {
		return displacementShareId;
	}

	public void setDisplacementShareId(Long displacementShareId) {
		this.displacementShareId = displacementShareId;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public List<ConstructionShare> getConstructionShares() {
		return constructionShares;
	}

	public void setConstructionShares(List<ConstructionShare> constructionShares) {
		this.constructionShares = constructionShares;
	}

	public List<InterventionShare> getInterventionShares() {
		return interventionShares;
	}

	public void setInterventionShares(List<InterventionShare> interventionShares) {
		this.interventionShares = interventionShares;
	}

	public List<InterventionPrShare> getInterventionPrShares() {
		return interventionPrShares;
	}

	public void setInterventionPrShares(List<InterventionPrShare> interventionPrShares) {
		this.interventionPrShares = interventionPrShares;
	}

	public List<WorkShare> getWorkShares() {
		return workShares;
	}

	public void setWorkShares(List<WorkShare> workShares) {
		this.workShares = workShares;
	}
}
