package com.epm.gestepm.model.displacement.service.mapper;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;

public class DisplacementMapper {

	private DisplacementMapper() {
		
	}
	
	public static DisplacementDTO mapDisplacementToDTO(Displacement displacement) {
		
		DisplacementDTO displacementDTO = new DisplacementDTO();
	
		displacementDTO.setTitle(displacement.getTitle());
		displacementDTO.setActivityCenter(displacement.getActivityCenter().getId());
		displacementDTO.setDisplacementType(displacement.getDisplacementType());
		displacementDTO.setTotalTime(Utiles.minutesToHoursAndMinutesString(displacement.getTotalTime()));
		
		return displacementDTO;
	}
}
