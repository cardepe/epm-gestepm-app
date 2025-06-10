package com.epm.gestepm.modelapi.deprecated.timecontrol.service;

import com.epm.gestepm.modelapi.deprecated.timecontrol.dto.TimeControlTableDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

public interface TimeControlOldService {
	List<TimeControlTableDTO> getTimeControlTableDTOByDateAndUser(int month, int year, Long userId, Long activityCenter, Locale locale);
	TimeControlTableDTO getTimeControlDetail(LocalDateTime startDate, Long userId);
}
