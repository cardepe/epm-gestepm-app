package com.epm.gestepm.model.shares.noprogrammed.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShareStateEnum;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class NoProgrammedShareCreate implements CollectableAttributes {

    @NotNull
    private Integer userId;

    @NotNull
    private Integer projectId;

    private Integer userSigningId;

    @NotNull
    private OffsetDateTime startDate;

    @NotNull
    private String description;

    @NotNull
    private Integer familyId;

    @NotNull
    private Integer subFamilyId;

    private Integer topicId;

    private String forumTitle;

    @NotNull
    private NoProgrammedShareStateEnum state;

    @Override
    public AttributeMap collectAttributes() {
        return null;
    }
}
