package com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater;

import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareFileCreateDto;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class NoProgrammedShareUpdateDto {

    @NotNull
    private Integer id;

    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    private String description;

    private Integer familyId;

    private Integer subFamilyId;

    private Integer topicId;

    private String forumTitle;

    private NoProgrammedShareStateEnumDto state;

    @Singular
    private Set<NoProgrammedShareFileCreateDto> files;

}
