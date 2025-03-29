package com.epm.gestepm.modelapi.timecontrolold.service;

import com.epm.gestepm.modelapi.timecontrolold.dto.TimeControlDetailTableDTO;
import com.epm.gestepm.modelapi.timecontrolold.dto.TimeControlTableDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface TimeControlOldService {
	List<TimeControlTableDTO> getTimeControlTableDTOByDateAndUser(int month, int year, Long userId, Long activityCenter, Locale locale);
	TimeControlTableDTO getTimeControlDetail(LocalDateTime startDate, Long userId);
	List<TimeControlDetailTableDTO> getTimeControlDetailTableDTOByDateAndUser(LocalDateTime startDate, Long userId, Locale locale);
}
