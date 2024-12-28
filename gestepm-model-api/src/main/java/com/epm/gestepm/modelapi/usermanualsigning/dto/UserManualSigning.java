package com.epm.gestepm.modelapi.usermanualsigning.dto;

import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Data
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
    private OffsetDateTime startDate;

    @Column(name = "END_DATE")
    private OffsetDateTime endDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "JUSTIFICATION")
    private byte[] justification;

    @Column(name = "JUSTIFICATION_EXT")
    private String justificationExt;

    @Column(name = "LOCATION", length = 32)
    private String location;

}
