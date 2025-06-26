package com.epm.gestepm.modelapi.customer.dto.finder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerByProjectIdFinderDto {

    @NotNull
    private Integer projectId;

}
