package com.epm.gestepm.modelapi.user.dto.deleter;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDeleteDto {

    @NotNull
    private Integer id;

}
