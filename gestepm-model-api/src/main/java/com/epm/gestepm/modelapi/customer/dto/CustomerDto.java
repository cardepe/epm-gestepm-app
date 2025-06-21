package com.epm.gestepm.modelapi.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CustomerDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String mainEmail;

    private String secondaryEmail;

    @NotNull
    private Integer projectId;

}
