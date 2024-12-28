package com.epm.gestepm.modelapi.interventionprshare.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
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
	private OffsetDateTime startDate;
	
	@Column(name = "END_DATE")
	private OffsetDateTime endDate;

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

}
