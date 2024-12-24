package com.epm.gestepm.modelapi.inspection.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionNotEndedException extends RuntimeException {

    private final Integer id;

}
