package com.epm.gestepm.modelapi.deprecated.interventionshare.dto;

import lombok.Data;

@Data
public class PdfFileDTO {

    private byte[] documentBytes;

    private String fileName;

}
