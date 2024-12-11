package com.epm.gestepm.model.inspection.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.inspection.dao.constants.InspectionAttributes;
import com.epm.gestepm.model.inspection.dao.entity.ActionEnum;
import com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater.ShareFileUpdate;
import lombok.Data;
import lombok.Singular;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Data
public class InspectionUpdate implements CollectableAttributes {

    @NotNull
    private Integer id;

    private Integer userSigningId;

    @NotNull
    private Integer shareId;

    @NotNull
    private ActionEnum action;

    @NotNull
    private OffsetDateTime startDate;

    private OffsetDateTime endDate;

    @NotNull
    private String description;

    @NotNull
    private Integer firstTechnicalId;

    private Integer secondTechnicalId;

    private String signature;

    private String operatorSignature;

    private String clientName;

    private List<Integer> materialIds;

    private String materialsFile;

    private String materialsFileExtension;

    private Integer equipmentHours;

    private Integer topicId;
    
    @Singular
    private Set<ShareFileUpdate> files;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(InspectionAttributes.ATTR_I_ID, this.id);
        map.put(InspectionAttributes.ATTR_I_USER_SIGNING_ID, this.userSigningId);
        map.put(InspectionAttributes.ATTR_I_SHARE_ID, this.shareId);
        map.put(InspectionAttributes.ATTR_I_ACTION, this.action.getId());
        map.put(InspectionAttributes.ATTR_I_START_DATE, this.startDate);
        map.put(InspectionAttributes.ATTR_I_END_DATE, this.endDate);
        map.put(InspectionAttributes.ATTR_I_DESCRIPTION, this.description);
        map.put(InspectionAttributes.ATTR_I_FIRST_TECHNICAL_ID, this.firstTechnicalId);
        map.put(InspectionAttributes.ATTR_I_SECOND_TECHNICAL_ID, this.secondTechnicalId);
        map.put(InspectionAttributes.ATTR_I_SIGNATURE, this.signature);
        map.put(InspectionAttributes.ATTR_I_OPERATOR_SIGNATURE, this.operatorSignature);
        map.put(InspectionAttributes.ATTR_I_CLIENT_NAME, this.clientName);
        map.putList(InspectionAttributes.ATTR_I_MATERIALS, this.materialIds);
        map.put(InspectionAttributes.ATTR_I_MATERIALS_FILE, this.materialsFile);
        map.put(InspectionAttributes.ATTR_I_MATERIALS_FILE_EXTENSION, this.materialsFileExtension);
        map.put(InspectionAttributes.ATTR_I_EQUIPMENT_HOURS, this.equipmentHours);
        map.put(InspectionAttributes.ATTR_I_TOPIC_ID, this.topicId);

        return map;
    }

}
