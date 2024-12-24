package com.epm.gestepm.modelapi.subfamily.dto;

import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class SubFamilyDto implements Serializable {

    @NotNull
    private Integer id;

    private Integer familyId;

    private String name;

    @Singular
    private List<Integer> subRoleIds;

}
