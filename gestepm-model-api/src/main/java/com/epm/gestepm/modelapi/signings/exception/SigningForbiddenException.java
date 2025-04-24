package com.epm.gestepm.modelapi.signings.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigningForbiddenException extends RuntimeException {

    private final Integer id;

}
