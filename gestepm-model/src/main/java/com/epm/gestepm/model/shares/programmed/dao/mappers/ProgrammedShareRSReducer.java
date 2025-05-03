package com.epm.gestepm.model.shares.programmed.dao.mappers;

import com.epm.gestepm.model.shares.programmed.dao.entity.ProgrammedShare;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class ProgrammedShareRSReducer implements BinaryOperator<ProgrammedShare> {

    @Override
    public ProgrammedShare apply(ProgrammedShare total, ProgrammedShare current) {

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
