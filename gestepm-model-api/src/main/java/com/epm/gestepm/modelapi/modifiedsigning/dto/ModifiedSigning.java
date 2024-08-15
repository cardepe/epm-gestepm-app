package com.epm.gestepm.modelapi.modifiedsigning.dto;

import com.epm.gestepm.modelapi.user.dto.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "modified_signings")
public class ModifiedSigning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name="SIGNING_ID", nullable = false, length = 11)
    private Long signingId;

    @Column(name="TYPE_ID", nullable = false, length = 5)
    private String typeId;

    @Column(name = "REQUEST_DATE", nullable = false)
    private Timestamp requestDate;

    @Column(name = "START_DATE", nullable = false)
    private Timestamp startDate;

    @Column(name = "END_DATE", nullable = false)
    private Timestamp endDate;

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

    public Long getSigningId() {
        return signingId;
    }

    public void setSigningId(Long signingId) {
        this.signingId = signingId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Timestamp getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
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
}
