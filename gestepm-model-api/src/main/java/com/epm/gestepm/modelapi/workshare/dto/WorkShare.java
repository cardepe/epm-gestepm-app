package com.epm.gestepm.modelapi.workshare.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "work_shares")
public class WorkShare {

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
	private LocalDateTime startDate;
	
	@Column(name = "END_DATE")
	private LocalDateTime endDate;
	
	@Column(name="OBSERVATIONS")
    private String observations;
	
	@Column(name = "SIGNATURE_OP")
	private String signatureOp;
	
	@Column(name = "MATERIALS", length = 100)
	private String materials;
	
	@Column(name = "MR_SIGNATURE")
	private String mrSignature;
	
	@OneToMany(mappedBy = "workShare")
	private List<WorkShareFile> workShareFiles;

}
