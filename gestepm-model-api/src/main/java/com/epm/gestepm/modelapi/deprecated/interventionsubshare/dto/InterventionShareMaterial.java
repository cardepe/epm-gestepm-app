package com.epm.gestepm.modelapi.deprecated.interventionsubshare.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "inspection_material")
public class InterventionShareMaterial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inspection_material_id", unique = true, nullable = false, precision = 10)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "inspection_id", nullable = false)
	private InterventionSubShare interventionSubShare;

	@Column(name = "units", nullable = false, length = 11)
	private Integer units;
	
	@Column(name = "description", length = 256)
	private String description;
	
	@Column(name = "reference", nullable = false, length = 11)
	private String reference;

}
