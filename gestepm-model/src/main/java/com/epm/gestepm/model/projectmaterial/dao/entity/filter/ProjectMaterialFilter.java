package com.epm.gestepm.model.projectmaterial.dao.entity.filter;

import com.epm.gestepm.lib.entity.AttributeMap;
import com.epm.gestepm.lib.entity.CollectableAttributes;
import com.epm.gestepm.lib.entity.Orderable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static com.epm.gestepm.model.projectmaterial.dao.constants.ProjectMaterialAttributes.*;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectMaterialFilter extends Orderable implements CollectableAttributes {

  private List<Integer> ids;

  private List<Integer> projectIds;

  private String nameContains;

  private Boolean required;

  @Override
  public AttributeMap collectAttributes() {

    final AttributeMap map = new AttributeMap();

    map.putList(ATTR_PRMAT_IDS, this.ids);
    map.putList(ATTR_PRMAT_PROJECT_IDS, this.projectIds);
    map.putLike(ATTR_PRMAT_NAME_CONTAINS, this.nameContains);
    map.put(ATTR_PRMAT_REQUIRED, this.required);

    return map;
  }

}
