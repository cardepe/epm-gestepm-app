package com.epm.gestepm.model.customer.dao.entity;

import com.epm.gestepm.lib.audit.AuditCreateClose;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Customer implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String mainEmail;

    private String secondaryEmail;

    @NotNull
    private Integer projectId;

}
