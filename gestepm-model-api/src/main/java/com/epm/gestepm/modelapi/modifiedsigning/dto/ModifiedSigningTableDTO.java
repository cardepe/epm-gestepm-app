package com.epm.gestepm.modelapi.modifiedsigning.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class ModifiedSigningTableDTO {

    @SerializedName("ms_id")
    private Long id;

    @SerializedName("ms_signingId")
    private Long signingId;

    @SerializedName("ms_signingType")
    private String signingType;

    @SerializedName("ms_name")
    private String name;

    @SerializedName("ms_surnames")
    private String surnames;

    @SerializedName("ms_startDate")
    private Date startDate;

    @SerializedName("ms_endDate")
    private Date endDate;

    public ModifiedSigningTableDTO() {
    }

    public ModifiedSigningTableDTO(Long id, Long signingId, String signingType, String name, String surnames, Date startDate, Date endDate) {
        this.id = id;
        this.signingId = signingId;
        this.signingType = signingType;
        this.name = name;
        this.surnames = surnames;
        this.startDate = startDate;
        this.endDate = endDate;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
