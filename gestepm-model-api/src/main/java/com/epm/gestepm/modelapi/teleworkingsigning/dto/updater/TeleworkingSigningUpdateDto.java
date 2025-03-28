package com.epm.gestepm.modelapi.teleworkingsigning.dto.updater;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class TeleworkingSigningUpdateDto {

    @NotNull
    private Integer id;

    private LocalDateTime startedAt;

    private String closedLocation;

}
