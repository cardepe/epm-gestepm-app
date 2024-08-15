package com.epm.gestepm.modelapi.interventionshare.mapper;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionFinalDto;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionMaterialDto;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = MapISMToInterventionMaterialDto.class)
public interface MapISSToInterventionFinalDto {

    @Mapping(source = "userSigning.id", target = "userSigningId")
    @Mapping(source = "interventionShare.id", target = "interventionId")
    @Mapping(source = "firstTechnical.id", target = "firstTechnicalId")
    @Mapping(source = "secondTechnical.id", target = "secondTechnicalId")
    InterventionFinalDto from(InterventionSubShare interventionSubShare);

}
