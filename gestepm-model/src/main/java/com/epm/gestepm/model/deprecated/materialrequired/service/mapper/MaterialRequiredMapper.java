package com.epm.gestepm.model.deprecated.materialrequired.service.mapper;

import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;

public class MaterialRequiredMapper {

	public static MaterialRequired mapNewDTOToEntity(MaterialRequiredDTO materialRequiredDTO, Project project) {
		
		MaterialRequired materialRequired = new MaterialRequired();
		materialRequired.setProject(project);
		materialRequired.setNameES(materialRequiredDTO.getNameES());
		materialRequired.setNameFR(materialRequiredDTO.getNameFR());
		materialRequired.setRequired(materialRequiredDTO.getRequired());
		
		return materialRequired;
	}
}
