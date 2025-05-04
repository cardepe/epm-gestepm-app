package com.epm.gestepm.modelapi.shares.programmed.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ProgrammedShareFileDto implements Serializable {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String content;

}
