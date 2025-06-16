package com.epm.gestepm.modelapi.timecontrol.service;

import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlExportDto;

public interface TimeControlWoffuExportService {
    String buildFileName(TimeControlExportDto timeControlExportDto);
    byte[] generate(TimeControlExportDto dto);
}
