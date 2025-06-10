package com.epm.gestepm.modelapi.userholiday.dto;

import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user_holidays")
public class UserHoliday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "DATE", nullable = false)
	private Date date;
	
	@Column(name="STATUS", length = 15)
    private String status;
	
	@Column(name="OBSERVATIONS")
    private String observations;

}
