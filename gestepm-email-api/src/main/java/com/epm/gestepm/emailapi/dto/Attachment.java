package com.epm.gestepm.emailapi.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Attachment {

    @NotNull
    private String fileName;

    @NotNull
    private String fileData;

    @NotNull
    private String contentType;

}
