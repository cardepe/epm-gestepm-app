package com.epm.gestepm.modelapi.personalexpense.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer personalExpenseSheetId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("personalExpenseSheetId", this.personalExpenseSheetId);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
