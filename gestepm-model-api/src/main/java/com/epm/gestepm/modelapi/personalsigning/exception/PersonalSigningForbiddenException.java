package com.epm.gestepm.modelapi.personalsigning.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PersonalSigningForbiddenException extends RuntimeException {

    private final Integer userId;

}
