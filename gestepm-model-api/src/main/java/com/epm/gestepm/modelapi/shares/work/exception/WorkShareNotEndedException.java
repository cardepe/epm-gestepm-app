package com.epm.gestepm.modelapi.shares.work.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkShareNotEndedException extends RuntimeException {

    private final Integer id;

}
