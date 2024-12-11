package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class InspectionRSReducer implements BinaryOperator<Inspection> {

  @Override
  public Inspection apply(Inspection total, Inspection current) {

    if (total == null) {
      return current;
    }

    if (!CollectionUtils.isEmpty(total.getMaterialIds()) && !CollectionUtils.isEmpty(current.getMaterialIds())) {

      final Set<Integer> materialIds = new LinkedHashSet<>();
      materialIds.addAll(total.getMaterialIds());
      materialIds.addAll(current.getMaterialIds());

      total.setMaterialIds(materialIds);
    }

    if (!CollectionUtils.isEmpty(total.getFileIds()) && !CollectionUtils.isEmpty(current.getFileIds())) {

      final Set<Integer> fileIds = new LinkedHashSet<>();
      fileIds.addAll(total.getFileIds());
      fileIds.addAll(current.getFileIds());

      total.setFileIds(fileIds);
    }

    return total;
  }

}
