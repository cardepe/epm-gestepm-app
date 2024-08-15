package com.epm.gestepm.modelapi.manualsigningtype.dto;

import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "manual_signing_type")
public class ManualSigningType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false, precision = 10)
    private Long id;

    @Column(name="NAME", nullable=false, length=32)
    private String name;

    @OneToMany(mappedBy="manualSigningType")
    private List<UserManualSigning> userManualSignings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserManualSigning> getUserManualSignings() {
        return userManualSignings;
    }

    public void setUserManualSignings(List<UserManualSigning> userManualSignings) {
        this.userManualSignings = userManualSignings;
    }
}
