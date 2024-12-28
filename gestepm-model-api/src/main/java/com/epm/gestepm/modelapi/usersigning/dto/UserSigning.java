package com.epm.gestepm.modelapi.usersigning.dto;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Data
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
	private OffsetDateTime startDate;
	
	@Column(name = "END_DATE")
	private OffsetDateTime endDate;
	
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

}
