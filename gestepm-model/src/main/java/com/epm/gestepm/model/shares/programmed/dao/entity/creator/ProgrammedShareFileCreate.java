package com.epm.gestepm.model.shares.programmed.dao.entity.creator;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.file.FileUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Base64;

import static com.epm.gestepm.model.shares.programmed.dao.constants.ProgrammedShareFileAttributes.*;

@Data
public class ProgrammedShareFileCreate implements CollectableAttributes {

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

        map.put(ATTR_PSF_SHARE_ID, this.shareId);
        map.put(ATTR_PSF_NAME, this.name);
        map.put(ATTR_PSF_CONTENT, FileUtils.compressBytes(Base64.getDecoder().decode(this.content)));

        return map;
    }
}
