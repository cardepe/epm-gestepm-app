package com.epm.gestepm.modelapi.signings.service;

import com.epm.gestepm.modelapi.signings.dto.SigningExportDto;

public interface SigningExportService {
    String buildFileName(SigningExportDto signingExportDto);
    byte[] generate(SigningExportDto dto);
}
