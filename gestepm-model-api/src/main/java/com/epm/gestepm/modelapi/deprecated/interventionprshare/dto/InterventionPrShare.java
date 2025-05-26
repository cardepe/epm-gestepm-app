package com.epm.gestepm.modelapi.deprecated.interventionprshare.dto;

import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.userold.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "programmed_share")
public class InterventionPrShare {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "programmed_share_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "ID", nullable = false)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID", nullable = false)
	private Project project;
	
	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;
	
	@Column(name = "END_DATE")
	private LocalDateTime endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECOND_TECHNICAL_ID", referencedColumnName = "ID", nullable = false)
	private User secondTechnical;
	
	@Column(name="OBSERVATIONS")
    private String observations;
	
	@Column(name = "customer_signature")
	private String signature;
	
	@Column(name = "operator_signature")
	private String signatureOp;
	
	@OneToMany(mappedBy = "interventionPrShare")
	private List<InterventionPrShareFile> interventionPrShareFiles;

}
