package com.epm.gestepm.modelapi.interventionshare.mapper;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionFinalDto;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionMaterialDto;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionMaterialUpdateDto;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapIMToInterventionInterventionShareMaterial {

    InterventionShareMaterial from(InterventionMaterialUpdateDto updateDto);

}
