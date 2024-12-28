package com.epm.gestepm.modelapi.displacementshare.dto;

import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "displacement_shares")
public class DisplacementShare {

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
	
	@Column(name = "MANUAL_DISPLACEMENT", nullable = false)
	private int manualDisplacement;
	
	@Column(name = "ORIGINAL_DATE", nullable = false)
	private OffsetDateTime originalDate;
	
	@Column(name = "DISPLACEMENT_DATE", nullable = false)
	private OffsetDateTime displacementDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISPLACEMENT_ID", referencedColumnName = "displacement_id", nullable = false)
	private Displacement displacement;
	
	@Column(name = "MANUAL_HOURS", nullable = false, length = 11)
	private int manualHours;
	
	@Column(name="OBSERVATIONS")
    private String observations;
	
	@Column(name = "ROUND_TRIP")
	private Boolean roundTrip;

}
