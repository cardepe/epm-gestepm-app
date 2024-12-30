package com.epm.gestepm.modelapi.personalexpensesheet.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseSheetFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer projectId;

  private Integer userId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("projectId", this.projectId);
    cacheKeyBuilder.addElement("userId", this.userId);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
