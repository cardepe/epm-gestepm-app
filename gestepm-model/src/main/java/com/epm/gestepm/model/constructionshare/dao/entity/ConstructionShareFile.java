package com.epm.gestepm.model.constructionshare.dao.entity;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ConstructionShareFile {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    private byte[] content;

}
