package com.epm.gestepm.modelapi.project.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import com.epm.gestepm.modelapi.project.dto.ProjectTypeDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String nameContains;

  private List<ProjectTypeDto> types;

  private List<Integer> activityCenterIds;

  private Integer state;

  private List<Integer> responsibleIds;

  private List<Integer> memberIds;

  private Boolean role;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("nameContains", this.nameContains);
    cacheKeyBuilder.addElement("types", this.types);
    cacheKeyBuilder.addElement("activityCenterIds", this.activityCenterIds);
    cacheKeyBuilder.addElement("state", this.state);
    cacheKeyBuilder.addElement("responsibleIds", this.responsibleIds);
    cacheKeyBuilder.addElement("memberIds", this.memberIds);
    cacheKeyBuilder.addElement("role", this.role);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
