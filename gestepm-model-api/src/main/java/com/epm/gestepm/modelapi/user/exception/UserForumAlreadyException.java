package com.epm.gestepm.modelapi.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserForumAlreadyException extends RuntimeException {

    private final String username;

}
