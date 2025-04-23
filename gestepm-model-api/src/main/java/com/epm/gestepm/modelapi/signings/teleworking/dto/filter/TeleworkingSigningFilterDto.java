package com.epm.gestepm.modelapi.signings.teleworking.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeleworkingSigningFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private List<Integer> userIds;

  private List<Integer> projectIds;

  private Boolean current;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("userIds", this.userIds);
    cacheKeyBuilder.addElement("projectIds", this.projectIds);
    cacheKeyBuilder.addElement("current", this.current);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
