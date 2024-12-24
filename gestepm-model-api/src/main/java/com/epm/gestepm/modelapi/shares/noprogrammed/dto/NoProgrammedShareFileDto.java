package com.epm.gestepm.modelapi.shares.noprogrammed.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class NoProgrammedShareFileDto implements Serializable {

    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String content;

}
