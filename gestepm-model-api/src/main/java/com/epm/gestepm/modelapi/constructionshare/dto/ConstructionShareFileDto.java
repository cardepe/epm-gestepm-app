package com.epm.gestepm.modelapi.constructionshare.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ConstructionShareFileDto {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    private byte[] content;

}
