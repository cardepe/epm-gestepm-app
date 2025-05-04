package com.epm.gestepm.modelapi.shares.work.service;

import com.epm.gestepm.modelapi.shares.work.dto.WorkShareDto;

public interface WorkShareExportService {
    byte[] generate(WorkShareDto dto);
}
