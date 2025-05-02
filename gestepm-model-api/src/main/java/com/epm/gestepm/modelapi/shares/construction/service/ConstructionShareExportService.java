package com.epm.gestepm.modelapi.shares.construction.service;

import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;

public interface ConstructionShareExportService {
    byte[] generate(ConstructionShareDto dto);
}
