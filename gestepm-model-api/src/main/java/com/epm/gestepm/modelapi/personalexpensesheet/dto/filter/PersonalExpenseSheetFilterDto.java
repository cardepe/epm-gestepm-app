package com.epm.gestepm.modelapi.personalexpensesheet.dto.filter;

import com.epm.gestepm.lib.cache.CacheKeyBuilder;
import com.epm.gestepm.lib.cache.UsableAsCacheKey;
import com.epm.gestepm.lib.dto.OrderableDto;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonalExpenseSheetFilterDto extends OrderableDto implements UsableAsCacheKey {

  private List<Integer> ids;

  private Integer projectId;

  private String description;

  private PersonalExpenseSheetStatusEnumDto status;

  private String observations;

  private LocalDateTime createdAt;

  private Integer createdBy;

  @Override
  public String asCacheKey() {

    final CacheKeyBuilder cacheKeyBuilder = new CacheKeyBuilder();

    cacheKeyBuilder.addElement("ids", this.ids);
    cacheKeyBuilder.addElement("projectId", this.projectId);
    cacheKeyBuilder.addElement("description", this.description);
    cacheKeyBuilder.addElement("status", this.status);
    cacheKeyBuilder.addElement("observations", this.observations);
    cacheKeyBuilder.addElement("createdAt", this.createdAt);
    cacheKeyBuilder.addElement("createdBy", this.createdBy);
    cacheKeyBuilder.addElement("orderable", super.toString());

    return cacheKeyBuilder.toString();
  }
}
