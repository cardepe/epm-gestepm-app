package com.epm.gestepm.modelapi.personalsigning.dto;

import com.epm.gestepm.modelapi.userold.dto.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "personal_signings")
public class PersonalSigning {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
	private User user;

	@Column(name = "START_DATE", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "END_DATE")
	private LocalDateTime endDate;

}
