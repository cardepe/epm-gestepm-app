package com.epm.gestepm.modelapi.interventionprshare.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

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
@Table(name = "intervention_pr_shares")
public class InterventionPrShare {

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
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SIGNING_ID")
	private UserSigning userSigning;
	
	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;
	
	@Column(name = "END_DATE")
	private Timestamp endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECOND_TECHNICAL_ID", referencedColumnName = "ID", nullable = false)
	private User secondTechnical;
	
	@Column(name="OBSERVATIONS")
    private String observations;
	
	@Column(name = "SIGNATURE")
	private String signature;
	
	@Column(name = "SIGNATURE_OP")
	private String signatureOp;
	
	@Column(name = "MATERIALS", length = 100)
	private String materials;
	
	@Column(name = "MR_SIGNATURE")
	private String mrSignature;
	
	@Column(name = "DISPLACEMENT_SHARE_ID", nullable = true)
	private Long displacementShareId;
	
	@OneToMany(mappedBy = "interventionPrShare")
	private List<InterventionPrShareFile> interventionPrShareFiles;

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

	public UserSigning getUserSigning() {
		return userSigning;
	}

	public void setUserSigning(UserSigning userSigning) {
		this.userSigning = userSigning;
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

	public User getSecondTechnical() {
		return secondTechnical;
	}

	public void setSecondTechnical(User secondTechnical) {
		this.secondTechnical = secondTechnical;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignatureOp() {
		return signatureOp;
	}

	public void setSignatureOp(String signatureOp) {
		this.signatureOp = signatureOp;
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

	public List<InterventionPrShareFile> getInterventionPrShareFiles() {
		return interventionPrShareFiles;
	}

	public void setInterventionPrShareFiles(List<InterventionPrShareFile> interventionPrShareFiles) {
		this.interventionPrShareFiles = interventionPrShareFiles;
	}
}
