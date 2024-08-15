package com.epm.gestepm.modelapi.interventionshare.dto;

import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class PdfFileDTO {

    private byte[] documentBytes;

    private String fileName;

    public byte[] getDocumentBytes() {
        return documentBytes;
    }

    public void setDocumentBytes(byte[] documentBytes) {
        this.documentBytes = documentBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PdfFileDTO that = (PdfFileDTO) o;
        return Arrays.equals(documentBytes, that.documentBytes) && Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName);
        result = 31 * result + Arrays.hashCode(documentBytes);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PdfFileDTO.class.getSimpleName() + "[", "]")
                .add("documentBytes=" + Arrays.toString(documentBytes))
                .add("fileName='" + fileName + "'")
                .toString();
    }
}
