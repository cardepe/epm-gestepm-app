package com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

@Data
public class NoProgrammedShareFileCreateDto {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    @JsonIgnore
    private String content;

}
