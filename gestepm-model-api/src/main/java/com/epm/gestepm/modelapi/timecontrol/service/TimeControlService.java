package com.epm.gestepm.modelapi.timecontrol.service;

import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDetailTableDTO;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTableDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface TimeControlService {
	List<TimeControlTableDTO> getTimeControlTableDTOByDateAndUser(int month, int year, Long userId, Long activityCenter, Locale locale);
	TimeControlTableDTO getTimeControlDetail(LocalDateTime startDate, Long userId);
	List<TimeControlDetailTableDTO> getTimeControlDetailTableDTOByDateAndUser(LocalDateTime startDate, Long userId, Locale locale);
}
