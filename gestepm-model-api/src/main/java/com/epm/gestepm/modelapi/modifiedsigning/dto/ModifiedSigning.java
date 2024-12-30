package com.epm.gestepm.modelapi.modifiedsigning.dto;

import com.epm.gestepm.modelapi.user.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
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
    private LocalDateTime requestDate;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

}
