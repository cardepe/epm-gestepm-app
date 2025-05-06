package com.epm.gestepm.modelapi.shares.programmed.service;

import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;

public interface ProgrammedShareExportService {
    byte[] generate(ProgrammedShareDto dto);
}
