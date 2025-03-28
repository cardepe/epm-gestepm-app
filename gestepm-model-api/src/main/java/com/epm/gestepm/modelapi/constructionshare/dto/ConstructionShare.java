package com.epm.gestepm.modelapi.constructionshare.dto;

import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "construction_shares")
public class ConstructionShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
	@Column(name="OBSERVATIONS")
    private String observations;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", nullable = false)
	private Project project;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
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

}
