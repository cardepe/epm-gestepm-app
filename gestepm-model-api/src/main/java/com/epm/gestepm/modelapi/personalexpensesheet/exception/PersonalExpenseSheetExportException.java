package com.epm.gestepm.modelapi.personalexpensesheet.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonalExpenseSheetExportException extends RuntimeException {

    private final Integer id;

}
