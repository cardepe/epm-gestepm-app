package com.epm.gestepm.modelapi.shares.noprogrammed.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ShareFileDto implements Serializable {

    private Integer id;

    @NotNull
    private String fileName;

    @NotNull
    private byte[] content;

}
