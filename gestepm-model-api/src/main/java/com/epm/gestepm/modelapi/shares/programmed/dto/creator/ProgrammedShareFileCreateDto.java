package com.epm.gestepm.modelapi.shares.programmed.dto.creator;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

@Data
public class ProgrammedShareFileCreateDto {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    @JsonIgnore
    private String content;

}
