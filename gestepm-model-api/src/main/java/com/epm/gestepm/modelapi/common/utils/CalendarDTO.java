package com.epm.gestepm.modelapi.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalendarDTO {

	private String id;

	private String title;

	private String start;

	private String end;

	private String color;

	private String textColor;

}
