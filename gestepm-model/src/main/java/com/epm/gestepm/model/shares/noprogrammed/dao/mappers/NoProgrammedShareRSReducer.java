package com.epm.gestepm.model.shares.noprogrammed.dao.mappers;

import com.epm.gestepm.model.shares.noprogrammed.dao.entity.NoProgrammedShare;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class NoProgrammedShareRSReducer implements BinaryOperator<NoProgrammedShare> {

  @Override
  public NoProgrammedShare apply(NoProgrammedShare total, NoProgrammedShare current) {

    if (total == null) {
      return current;
    }

    if (!CollectionUtils.isEmpty(total.getInterventionIds()) && !CollectionUtils.isEmpty(current.getInterventionIds())) {

      final Set<Integer> interventionIds = new LinkedHashSet<>();
      interventionIds.addAll(total.getInterventionIds());
      interventionIds.addAll(current.getInterventionIds());

      total.setInterventionIds(interventionIds);
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
