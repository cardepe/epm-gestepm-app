package com.epm.gestepm.modelapi.shares.programmed.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import com.epm.gestepm.modelapi.shares.dto.ShareStatusDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProgrammedShareFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private List<Integer> userIds;

  private List<Integer> projectIds;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private ShareStatusDto status;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("userIds", this.userIds);
    cacheKeyBuilder.addElement("projectIds", this.projectIds);
    cacheKeyBuilder.addElement("startDate", this.startDate);
    cacheKeyBuilder.addElement("endDate", this.endDate);
    cacheKeyBuilder.addElement("status", this.status);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }

}
