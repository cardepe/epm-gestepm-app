package com.epm.gestepm.modelapi.constructionshare.dto;

import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
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
@Table(name = "construction_shares")
public class ConstructionShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "START_DATE", nullable = false)
	private Timestamp startDate;

	@Column(name = "END_DATE")
	private Timestamp endDate;
	
	@Column(name="OBSERVATIONS")
    private String observations;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SIGNING_ID")
	private UserSigning userSigning;
	
	@Column(name = "SIGNATURE_OP")
	private String signatureOp;
	
	@Column(name = "MATERIALS", length = 100)
	private String materials;
	
	@Column(name = "MR_SIGNATURE")
	private String mrSignature;
	
	@Column(name = "DISPLACEMENT_SHARE_ID", nullable = true)
	private Long displacementShareId;
	
	@OneToMany(mappedBy = "constructionShare")
	private List<ConstructionShareFile> constructionShareFiles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserSigning getUserSigning() {
		return userSigning;
	}

	public void setUserSigning(UserSigning userSigning) {
		this.userSigning = userSigning;
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

	public List<ConstructionShareFile> getConstructionShareFiles() {
		return constructionShareFiles;
	}

	public void setConstructionShareFiles(List<ConstructionShareFile> constructionShareFiles) {
		this.constructionShareFiles = constructionShareFiles;
	}
}
