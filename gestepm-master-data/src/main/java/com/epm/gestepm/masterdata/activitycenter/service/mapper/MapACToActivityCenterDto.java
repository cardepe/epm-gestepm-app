package com.epm.gestepm.masterdata.activitycenter.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.activitycenter.dao.entity.ActivityCenter;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapACToActivityCenterDto {

  ActivityCenterDto from(ActivityCenter activityCenter);

  List<ActivityCenterDto> from(List<ActivityCenter> activityCenter);

  default Page<ActivityCenterDto> from(Page<ActivityCenter> page) {
    return new Page<>(page.cursor(), from(page.getContent()));
  }

}
