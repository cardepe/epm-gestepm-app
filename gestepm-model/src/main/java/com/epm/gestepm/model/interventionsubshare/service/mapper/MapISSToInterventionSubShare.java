package com.epm.gestepm.model.interventionsubshare.service.mapper;

import com.epm.gestepm.modelapi.interventionshare.dto.InterventionUpdateFinalDto;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;

public class MapISSToInterventionSubShare {

    public static InterventionSubShare from(InterventionSubShare update, InterventionUpdateFinalDto updateDto) {

        update.setDescription(updateDto.getDescription());
        update.setEquipmentHours(updateDto.getEquipmentHours());
        update.setSignature(updateDto.getSignature());
        update.setSignatureOp(updateDto.getSignatureOp());
        update.setClientName(updateDto.getClientName());

        return update;
    }

}
