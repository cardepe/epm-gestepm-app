package com.epm.gestepm.modelapi.interventionshare.dto;

import lombok.Data;

@Data
public class PdfFileDTO {

    private byte[] documentBytes;

    private String fileName;

}
