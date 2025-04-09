package com.epm.gestepm.modelapi.constructionshare.dto;

import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "construction_share")
public class ConstructionShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "construction_share_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@Column(name = "created_at", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "closed_at")
	private LocalDateTime endDate;
	
	@Column(name="observations")
    private String observations;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "operator_signature")
	private String signatureOp;
	
	@OneToMany(mappedBy = "constructionShare")
	private List<ConstructionShareFile> constructionShareFiles;

}
