package com.epm.gestepm.model.constructionshare.dao.mappers;

import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShareFile;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BinaryOperator;

public class ConstructionShareRSReducer implements BinaryOperator<ConstructionShare> {

    @Override
    public ConstructionShare apply(ConstructionShare total, ConstructionShare current) {

        if (total == null) {
            return current;
        }

        final Set<ConstructionShareFile> set = new LinkedHashSet<>();
        set.addAll(total.getFiles());
        set.addAll(current.getFiles());

        total.setFiles(new ArrayList<>(set));

        return total;
    }
}
