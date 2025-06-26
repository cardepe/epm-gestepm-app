package com.epm.gestepm.model.projectmaterial.dao.entity.finder;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import lombok.Data;

import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialAttributes.ATTR_PRMAT_ID;

@Data
public class ProjectMaterialByIdFinder implements CollectableAttributes {

  private Integer id;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.put(ATTR_PRMAT_ID, this.id);

    return map;
  }
}
