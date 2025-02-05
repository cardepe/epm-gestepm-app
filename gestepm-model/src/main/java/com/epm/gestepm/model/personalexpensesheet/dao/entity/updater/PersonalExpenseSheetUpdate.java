package com.epm.gestepm.model.personalexpensesheet.dao.entity.updater;

import com.epm.gestepm.lib.audit.AuditApprovePaidDischarge;
import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static com.epm.gestepm.model.personalexpensesheet.dao.constants.PersonalExpenseSheetAttributes.*;

@Data
public class PersonalExpenseSheetUpdate implements AuditApprovePaidDischarge, CollectableAttributes {

    @NotNull
    private Integer id;

    private Integer projectId;

    private String description;

    private PersonalExpenseSheetStatusEnum status;

    private String observations;

    private LocalDateTime approvedAt;

    private Integer approvedBy;

    private LocalDateTime paidAt;

    private Integer paidBy;

    private LocalDateTime dischargedAt;

    private Integer dischargedBy;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_PES_ID, this.id);
        map.put(ATTR_PES_PROJECT_ID, this.projectId);
        map.put(ATTR_PES_DESCRIPTION, this.description);
        map.putEnum(ATTR_PES_STATUS, this.status);
        map.put(ATTR_PES_OBSERVATIONS, this.observations);
        map.putTimestamp(ATTR_PES_APPROVED_AT, this.approvedAt);
        map.put(ATTR_PES_APPROVED_BY, this.approvedBy);
        map.putTimestamp(ATTR_PES_PAID_AT, this.paidAt);
        map.put(ATTR_PES_PAID_BY, this.paidBy);
        map.putTimestamp(ATTR_PES_DISCHARGED_AT, this.dischargedAt);
        map.put(ATTR_PES_DISCHARGED_BY, this.dischargedBy);

        return map;
    }

}
