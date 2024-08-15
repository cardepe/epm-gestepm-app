package com.epm.gestepm.modelapi.modifiedsigning.dto;

public class ModifiedSigningDTO {

    private Long id;

    private Long signingId;

    private String signingType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSigningId() {
        return signingId;
    }

    public void setSigningId(Long signingId) {
        this.signingId = signingId;
    }

    public String getSigningType() {
        return signingType;
    }

    public void setSigningType(String signingType) {
        this.signingType = signingType;
    }
}
