package com.epm.gestepm.modelapi.customer.dto.creator;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerCreateDto {

    @NotNull
    private String name;

    private String mainEmail;

    private String secondaryEmail;

    @NotNull
    private Integer projectId;

}
