package com.epm.gestepm.modelapi.deprecated.dto;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
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
@Table(name = "no_programmed_share_file")
public class InterventionShareFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "no_programmed_share_file_id", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "name", nullable = false, length = 64)
	private String name;

	@Column(name = "extension", nullable = false, length = 6)
	private String ext;

	@Column(name = "content", nullable = false)
	private byte[] content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "no_programmed_share_id", nullable = false)
	private InterventionShare interventionShare;

}
