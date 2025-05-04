package com.epm.gestepm.modelapi.deprecated.interventionprshare.dto;

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
@Table(name = "intervention_pr_share_files")
public class InterventionPrShareFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "programmed_share_file_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;

	@Column(name = "CONTENT", nullable = false)
	private byte[] content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "programmed_share_id", nullable = false)
	private InterventionPrShare interventionPrShare;

}
