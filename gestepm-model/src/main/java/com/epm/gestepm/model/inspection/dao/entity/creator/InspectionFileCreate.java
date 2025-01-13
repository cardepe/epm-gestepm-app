package com.epm.gestepm.model.inspection.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.inspection.dao.constants.InspectionFileAttributes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

@Data
public class InspectionFileCreate implements CollectableAttributes {

    @NotNull
    private Integer inspectionId;

    @NotNull
    private String name;

    @NotNull
    @JsonIgnore
    private String content;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(InspectionFileAttributes.ATTR_IF_INSPECTION_ID, this.inspectionId);
        map.put(InspectionFileAttributes.ATTR_IF_NAME, this.name);
        map.put(InspectionFileAttributes.ATTR_IF_CONTENT, FileUtils.compressBytes(Base64.getDecoder().decode(this.content)));

        return map;
    }
}
