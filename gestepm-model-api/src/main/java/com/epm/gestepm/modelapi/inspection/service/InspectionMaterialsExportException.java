package com.epm.gestepm.modelapi.inspection.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InspectionMaterialsExportException extends RuntimeException {

    private final Integer id;

}
