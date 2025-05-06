package com.epm.gestepm.modelapi.shares.work.dto.creator;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.NotNull;

@Data
public class WorkShareFileCreateDto {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    @JsonIgnore
    private String content;

}
