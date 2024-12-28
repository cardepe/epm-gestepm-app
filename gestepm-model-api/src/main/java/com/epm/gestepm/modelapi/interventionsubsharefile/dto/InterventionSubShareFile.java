package com.epm.gestepm.modelapi.interventionsubsharefile.dto;

import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
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
@Table(name = "inspection_file")
public class InterventionSubShareFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false, precision = 10)
	private Long id;

	@Column(name = "NAME", nullable = false, length = 64)
	private String name;

	@Column(name = "EXT", nullable = false, length = 6)
	private String ext;

	@Column(name = "CONTENT", nullable = false)
	private byte[] content;

	@ManyToOne(optional = false)
	@JoinColumn(name = "SUB_SHARE_ID", nullable = false)
	private InterventionSubShare interventionSubShare;

}
