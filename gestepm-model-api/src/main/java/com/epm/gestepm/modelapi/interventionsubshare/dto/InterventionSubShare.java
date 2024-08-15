package com.epm.gestepm.modelapi.interventionsubshare.dto;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
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
@Table(name = "intervention_sub_shares")
public class InterventionSubShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SIGNING_ID")
	private UserSigning userSigning;
	
	@Column(name = "ORDER_ID")
	private Long orderId;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "INTERVENTION_ID", nullable = false)
	private InterventionShare interventionShare;
	
	@Column(name = "ACTION")
	private Integer action;
	
	@Column(name = "START_DATE", nullable = false)
	private OffsetDateTime startDate;
	
	@Column(name = "END_DATE")
	private OffsetDateTime endDate;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRST_TECHNICAL", nullable = false)
	private User firstTechnical;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "SECOND_TECHNICAL", nullable = false)
	private User secondTechnical;
	
	@Column(name = "SIGNATURE")
	private String signature;
	
	@Column(name = "SIGNATURE_OP")
	private String signatureOp;
	
	@Column(name = "CLIENT_NAME", length = 120)
	private String clientName;
	
	@Column(name = "MATERIALS", length = 100)
	private String materials;
	
	@Column(name = "MR_SIGNATURE")
	private String mrSignature;
	
	@Column(name = "TOPIC_ID")
	private Long topicId;
	
	@Column(name = "MATERIALS_FILE", nullable = false)
	private byte[] materialsFile;
	
	@Column(name = "MATERIALS_FILE_EXT")
	private String materialsFileExt;
	
	@Column(name = "DISPLACEMENT_SHARE_ID")
	private Long displacementShareId;

	@Column(name = "EQUIPMENT_HOURS")
	private Integer equipmentHours;
	
	@OneToMany(mappedBy = "interventionSubShare")
	private List<InterventionShareMaterial> interventionShareMaterials;

	@OneToMany(mappedBy = "interventionSubShare")
	private List<InterventionSubShareFile> interventionSubShareFiles;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserSigning getUserSigning() {
		return userSigning;
	}

	public void setUserSigning(UserSigning userSigning) {
		this.userSigning = userSigning;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public InterventionShare getInterventionShare() {
		return interventionShare;
	}

	public void setInterventionShare(InterventionShare interventionShare) {
		this.interventionShare = interventionShare;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getFirstTechnical() {
		return firstTechnical;
	}

	public void setFirstTechnical(User firstTechnical) {
		this.firstTechnical = firstTechnical;
	}

	public User getSecondTechnical() {
		return secondTechnical;
	}

	public void setSecondTechnical(User secondTechnical) {
		this.secondTechnical = secondTechnical;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public byte[] getMaterialsFile() {
		return materialsFile;
	}

	public void setMaterialsFile(byte[] materialsFile) {
		this.materialsFile = materialsFile;
	}

	public String getMaterialsFileExt() {
		return materialsFileExt;
	}

	public void setMaterialsFileExt(String materialsFileExt) {
		this.materialsFileExt = materialsFileExt;
	}

	public Long getDisplacementShareId() {
		return displacementShareId;
	}

	public void setDisplacementShareId(Long displacementShareId) {
		this.displacementShareId = displacementShareId;
	}

	public Integer getEquipmentHours() {
		return equipmentHours;
	}

	public void setEquipmentHours(Integer equipmentHours) {
		this.equipmentHours = equipmentHours;
	}

	public List<InterventionShareMaterial> getInterventionShareMaterials() {
		return interventionShareMaterials;
	}

	public void setInterventionShareMaterials(List<InterventionShareMaterial> interventionShareMaterials) {
		this.interventionShareMaterials = interventionShareMaterials;
	}

	public List<InterventionSubShareFile> getInterventionSubShareFiles() {
		return interventionSubShareFiles;
	}

	public void setInterventionSubShareFiles(List<InterventionSubShareFile> interventionSubShareFiles) {
		this.interventionSubShareFiles = interventionSubShareFiles;
	}
}
