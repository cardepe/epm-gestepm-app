package com.epm.gestepm.model.shares.construction.dao.mappers;

import com.epm.gestepm.model.shares.construction.dao.entity.ConstructionShare;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class ConstructionShareRSReducer implements BinaryOperator<ConstructionShare> {

    @Override
    public ConstructionShare apply(ConstructionShare total, ConstructionShare current) {

        if (total == null) {
            return current;
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
