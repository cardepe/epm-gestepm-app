package com.epm.gestepm.modelapi.personalexpense.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import lombok.Data;

import java.util.List;

@Data
public class PersonalExpenseFileFilterDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer personalExpenseSheetId;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("personalExpenseSheetId", this.personalExpenseSheetId);

    return cacheKeyBuilder.toString();
  }

}
