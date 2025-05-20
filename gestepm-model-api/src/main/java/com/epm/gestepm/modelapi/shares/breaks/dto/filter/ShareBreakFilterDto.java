package com.epm.gestepm.modelapi.shares.breaks.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ShareBreakFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private List<Integer> constructionShareIds;

  private List<Integer> programmedShareIds;

  private List<Integer> inspectionIds;

  private List<Integer> workShareIds;

  private ShareStatusDto status;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("constructionShareIds", this.constructionShareIds);
    cacheKeyBuilder.addElement("programmedShareIds", this.programmedShareIds);
    cacheKeyBuilder.addElement("inspectionIds", this.inspectionIds);
    cacheKeyBuilder.addElement("workShareIds", this.workShareIds);
    cacheKeyBuilder.addElement("status", this.status);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
