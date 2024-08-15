package com.epm.gestepm.modelapi.interventionshare.mapper;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionMaterialDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MapISMToInterventionMaterialDto {

    @Mapping(source = "interventionSubShare.id", target = "interventionId")
    InterventionMaterialDto from(InterventionShareMaterial interventionShareMaterial);

    List<InterventionMaterialDto> from(List<InterventionShareMaterial> list);
}
