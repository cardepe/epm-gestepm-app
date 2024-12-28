package com.epm.gestepm.model.shares.noprogrammed.mapper;

import com.epm.gestepm.model.shares.noprogrammed.mapper.decorator.ShareTableDecorator;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = ShareTableDecorator.class)
public interface MapIToShareTableDto {

    ShareTableDTO from(InspectionDto inspection);

    List<ShareTableDTO> from(List<InspectionDto> inspections);

}
