package com.epm.gestepm.modelapi.shares.noprogrammed.dto;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class NoProgrammedShareDto implements Serializable {

    @NotNull
    private Integer id;

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private Integer userSigningId;

    @NotNull
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private String description;

    private Integer familyId;

    private Integer subFamilyId;

    private Integer topicId;

    private String forumTitle;

    @NotNull
    private NoProgrammedShareStateEnumDto state;

    @Singular
    private List<Integer> inspectionIds;

    @Singular
    private Set<Integer> fileIds;

}
