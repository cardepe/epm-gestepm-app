package com.epm.gestepm.modelapi.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class YearCalendarDTO {

	private Long id;

	private String color;

	private Date date;

	private List<String> usernames;

	public void addUsername(String username) {
		this.usernames.add(username);
	}
}
