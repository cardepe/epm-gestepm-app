package com.epm.gestepm.modelapi.deprecated.customer.dto;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customer")
public class Customer {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="customer_id", unique=true, nullable=false, precision=10)
    private Long id;

	@Column(name="name", nullable=false, length=128)
    private String name;
	
	@Column(name="main_email", length=64)
    private String mainEmail;
	
	@Column(name="secondary_email", length=64)
    private String secondaryEmail;
	
	@OneToOne
	@JoinColumn(name = "project_id")
	private Project project;

}
