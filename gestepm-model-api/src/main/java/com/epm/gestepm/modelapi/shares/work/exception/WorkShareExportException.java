package com.epm.gestepm.modelapi.shares.work.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkShareExportException extends RuntimeException {

    private final Integer id;

}
