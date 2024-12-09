package com.epm.gestepm.model.shares.noprogrammed.dao.entity.updater;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareAttributes;
import com.epm.gestepm.model.shares.noprogrammed.dao.constants.NoProgrammedShareFileAttributes;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.ShareFileUpdateDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import javax.validation.constraints.NotNull;
import java.util.Base64;
import java.util.List;

@Data
public class ShareFileUpdate implements CollectableAttributes {

    @NotNull
    private Integer shareId;

    @NotNull
    private String name;

    @NotNull
    private String ext;

    @NotNull
    @JsonIgnore
    private String content;

    @Override
    public AttributeMap collectAttributes() {

        final AttributeMap map = new AttributeMap();

        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_NPS_ID, this.shareId);
        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_NAME, this.name);
        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_EXT, this.ext);
        map.put(NoProgrammedShareFileAttributes.ATTR_NPSF_CONTENT, Base64.getDecoder().decode(this.content));

        return map;
    }
}
