package com.epm.gestepm.modelapi.shares.programmed.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgrammedShareNotEndedException extends RuntimeException {

    private final Integer id;

}
