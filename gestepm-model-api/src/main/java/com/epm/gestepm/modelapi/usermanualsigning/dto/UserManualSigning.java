package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.user.dto.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_manual_signings")
public class UserManualSigning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 10)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANUAL_SIGNING_TYPE_ID", referencedColumnName = "ID", nullable = false)
    private ManualSigningType manualSigningType;

    @Column(name = "START_DATE", nullable = false)
    private Timestamp startDate;

    @Column(name = "END_DATE")
    private Timestamp endDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "JUSTIFICATION")
    private byte[] justification;

    @Column(name = "JUSTIFICATION_EXT")
    private String justificationExt;

    @Column(name = "LOCATION", length = 32)
    private String location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ManualSigningType getManualSigningType() {
        return manualSigningType;
    }

    public void setManualSigningType(ManualSigningType manualSigningType) {
        this.manualSigningType = manualSigningType;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getJustification() {
        return justification;
    }

    public void setJustification(byte[] justification) {
        this.justification = justification;
    }

    public String getJustificationExt() {
        return justificationExt;
    }

    public void setJustificationExt(String justificationExt) {
        this.justificationExt = justificationExt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
