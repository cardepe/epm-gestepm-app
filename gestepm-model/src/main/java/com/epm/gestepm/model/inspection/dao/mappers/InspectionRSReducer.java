package com.epm.gestepm.model.inspection.dao.mappers;

import com.epm.gestepm.model.inspection.dao.entity.Inspection;
import com.epm.gestepm.model.inspection.dao.entity.Material;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.BinaryOperator;

public class InspectionRSReducer implements BinaryOperator<Inspection> {

  @Override
  public Inspection apply(Inspection total, Inspection current) {

    if (total == null) {
      return current;
    }

    if (!CollectionUtils.isEmpty(total.getMaterials()) && !CollectionUtils.isEmpty(current.getMaterials())) {

      final Set<Material> materials = new HashSet<>();
      materials.addAll(total.getMaterials());
      materials.addAll(current.getMaterials());

      total.setMaterials(materials);
    }

    if (!CollectionUtils.isEmpty(total.getFileIds()) && !CollectionUtils.isEmpty(current.getFileIds())) {

      final List<Integer> fileIds = new ArrayList<>();
      fileIds.addAll(total.getFileIds());
      fileIds.addAll(current.getFileIds());

      total.setFileIds(fileIds);
    }

    return total;
  }

}
