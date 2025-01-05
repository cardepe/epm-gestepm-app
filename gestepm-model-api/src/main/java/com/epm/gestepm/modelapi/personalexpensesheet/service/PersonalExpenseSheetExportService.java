package com.epm.gestepm.modelapi.personalexpensesheet.service;

import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetDto;

public interface PersonalExpenseSheetExportService {
    byte[] generate(PersonalExpenseSheetDto personalExpenseSheet);
}
