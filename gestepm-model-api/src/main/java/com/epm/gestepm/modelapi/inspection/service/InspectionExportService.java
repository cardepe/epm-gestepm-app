package com.epm.gestepm.modelapi.inspection.service;

import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;

public interface InspectionExportService {
    byte[] generate(InspectionDto inspection);
    byte[] generateMaterials(InspectionDto inspection);
}
