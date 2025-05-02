package com.epm.gestepm.modelapi.shares.construction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConstructionShareExportException extends RuntimeException {

    private final Integer id;

}
