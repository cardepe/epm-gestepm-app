package com.epm.gestepm.modelapi.projectmaterial.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectMaterialFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private List<Integer> projectIds;

  private String nameContains;

  private Boolean required;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("projectIds", this.projectIds);
    cacheKeyBuilder.addElement("nameContains", this.nameContains);
    cacheKeyBuilder.addElement("required", this.required);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
