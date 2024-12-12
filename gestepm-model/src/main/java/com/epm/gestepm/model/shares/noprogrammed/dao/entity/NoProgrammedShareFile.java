package com.epm.gestepm.model.shares.noprogrammed.dao.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NoProgrammedShareFile implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    private String content;

}
