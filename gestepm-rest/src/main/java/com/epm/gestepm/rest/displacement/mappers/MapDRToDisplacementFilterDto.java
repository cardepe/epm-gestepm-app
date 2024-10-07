package com.epm.gestepm.rest.displacement.mappers;

import com.epm.gestepm.masterdata.api.displacement.dto.filter.DisplacementFilterDto;
import com.epm.gestepm.rest.displacement.request.DisplacementListRestRequest;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface MapDRToDisplacementFilterDto {

    DisplacementFilterDto from(DisplacementListRestRequest req);

    @AfterMapping
    default void transform(@MappingTarget DisplacementFilterDto filterDto) {
        if (StringUtils.isNoneBlank(filterDto.getOrder())) {
            filterDto.setOrder(filterDto.getOrder().toUpperCase());
        }

        if (StringUtils.isNoneBlank(filterDto.getOrderBy())) {
            final String snakeCase = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(filterDto.getOrderBy()), '_').toLowerCase();
            final String finalText = snakeCase.replace("_._", "_");

            filterDto.setOrderBy(finalText);
        }
    }
}
