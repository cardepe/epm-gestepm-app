package com.epm.gestepm.modelapi.deprecated.workshare;

import com.epm.gestepm.modelapi.deprecated.worksharefile.WorkShareFile;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "work_share")
public class WorkShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "work_share_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", referencedColumnName = "ID", nullable = false)
	private Project project;
	
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "end_date")
	private LocalDateTime endDate;
	
	@Column(name="observations")
    private String observations;
	
	@Column(name = "operator_signature")
	private String signatureOp;
	
	@OneToMany(mappedBy = "workShare")
	private List<WorkShareFile> workShareFiles;

}
