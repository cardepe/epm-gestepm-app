package com.epm.gestepm.modelapi.project.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProjectFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private String nameContains;

  private Boolean isStation;

  private List<Integer> activityCenterIds;

  private Boolean isTeleworking;

  private Integer state;

  private List<Integer> responsibleIds;

  private List<Integer> memberIds;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("nameContains", this.nameContains);
    cacheKeyBuilder.addElement("isStation", this.isStation);
    cacheKeyBuilder.addElement("activityCenterIds", this.activityCenterIds);
    cacheKeyBuilder.addElement("isTeleworking", this.isTeleworking);
    cacheKeyBuilder.addElement("state", this.state);
    cacheKeyBuilder.addElement("responsibleIds", this.responsibleIds);
    cacheKeyBuilder.addElement("memberIds", this.memberIds);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
