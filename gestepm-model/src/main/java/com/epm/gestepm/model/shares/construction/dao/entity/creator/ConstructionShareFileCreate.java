package com.epm.gestepm.model.shares.construction.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

import static com.epm.gestepm.model.shares.construction.dao.constants.ConstructionShareFileAttributes.*;

@Data
public class ConstructionShareFileCreate implements CollectableAttributes {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    @JsonIgnore
    private String content;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(ATTR_CSF_SHARE_ID, this.shareId);
        map.put(ATTR_CSF_NAME, this.name);
        map.put(ATTR_CSF_CONTENT, FileUtils.compressBytes(Base64.getDecoder().decode(this.content)));

        return map;
    }
}
