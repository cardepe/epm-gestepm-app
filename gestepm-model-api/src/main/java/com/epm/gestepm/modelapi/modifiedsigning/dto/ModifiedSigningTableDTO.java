package com.epm.gestepm.modelapi.modifiedsigning.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ModifiedSigningTableDTO {

    @JsonProperty("ms_id")
    private Long id;

    @JsonProperty("ms_signingId")
    private Long signingId;

    @JsonProperty("ms_signingType")
    private String signingType;

    @JsonProperty("ms_name")
    private String name;

    @JsonProperty("ms_surnames")
    private String surnames;

    @JsonProperty("ms_startDate")
    private LocalDateTime startDate;

    @JsonProperty("ms_endDate")
    private LocalDateTime endDate;

}
