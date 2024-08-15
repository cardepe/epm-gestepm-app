package com.epm.gestepm.modelapi.userholiday.dto;

import com.epm.gestepm.modelapi.user.dto.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_holidays")
public class UserHoliday {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
	private User user;

	@Column(name = "DATE", nullable = false)
	private Date date;
	
	@Column(name="STATUS", length = 15)
    private String status;
	
	@Column(name="OBSERVATIONS")
    private String observations;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObservations() {
		return observations;
	}

	public void setObservations(String observations) {
		this.observations = observations;
	}
}
