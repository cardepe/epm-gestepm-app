package com.epm.gestepm.model.shares.programmed.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProgrammedShareFile {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String content;

}
