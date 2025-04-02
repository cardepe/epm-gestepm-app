package com.epm.gestepm.modelapi.teleworkingsigning.dto.deleter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeleworkingSigningDeleteDto {

    @NotNull
    private Integer id;

}
