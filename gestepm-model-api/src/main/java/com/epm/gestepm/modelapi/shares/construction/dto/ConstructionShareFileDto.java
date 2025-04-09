package com.epm.gestepm.modelapi.shares.construction.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ConstructionShareFileDto implements Serializable {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String content;

}
