package com.epm.gestepm.modelapi.interventionsubshare.dto;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "inspection")
public class InterventionSubShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INSPECTION_ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_SIGNING_ID")
	private UserSigning userSigning;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "NO_PROGRAMMED_SHARE_ID", nullable = false)
	private InterventionShare interventionShare;

	@Enumerated(EnumType.STRING)
	private ActionEnumDto action;
	
	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
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
	
	@Column(name = "OPERATOR_SIGNATURE")
	private String signatureOp;
	
	@Column(name = "CLIENT_NAME", length = 120)
	private String clientName;
	
	@Column(name = "TOPIC_ID")
	private Long topicId;
	
	@Column(name = "MATERIALS_FILE", nullable = false)
	private byte[] materialsFile;
	
	@Column(name = "MATERIALS_FILE_EXTENSION")
	private String materialsFileExt;

	@Column(name = "EQUIPMENT_HOURS")
	private Integer equipmentHours;
	
	@OneToMany(mappedBy = "interventionSubShare")
	private List<InterventionShareMaterial> interventionShareMaterials;

	@OneToMany(mappedBy = "interventionSubShare")
	private List<InterventionSubShareFile> interventionSubShareFiles;

}
