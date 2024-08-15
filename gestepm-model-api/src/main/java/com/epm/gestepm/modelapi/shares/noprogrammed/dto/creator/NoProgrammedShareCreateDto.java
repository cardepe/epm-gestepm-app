package com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class NoProgrammedShareCreateDto {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private Integer userSigningId;

    @NotNull
    private String description;

    @NotNull
    private Integer familyId;

    @NotNull
    private Integer subFamilyId;

    @Singular
    private Set<Integer> fileIds;

}
