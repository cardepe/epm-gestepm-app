package com.epm.gestepm.modelapi.user.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserByEmailAndPasswordFinderDto {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
