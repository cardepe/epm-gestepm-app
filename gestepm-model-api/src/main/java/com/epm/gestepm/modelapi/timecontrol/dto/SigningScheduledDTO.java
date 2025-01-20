package com.epm.gestepm.modelapi.timecontrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SigningScheduledDTO {

	private Long userSigningId;

	private LocalDateTime date;

	private int value;

}
