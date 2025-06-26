package com.epm.gestepm.model.deprecated.materialrequired.service;

import java.util.List;

import com.epm.gestepm.model.deprecated.materialrequired.dao.MaterialRequiredRepository;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequiredTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.deprecated.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.deprecated.materialrequired.service.MaterialRequiredService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class MaterialRequiredServiceImpl implements MaterialRequiredService {

	@Autowired
	private MaterialRequiredRepository materialRequiredRepository;

	@Override
	public MaterialRequired getById(Long id) {
		return materialRequiredRepository.findById(id).get();
	}
	
	@Override
	public List<MaterialRequired> findAll() {
		return (List<MaterialRequired>) materialRequiredRepository.findAll();
	}
	
	@Override
	public MaterialRequired save(MaterialRequired materialRequired) {
		return materialRequiredRepository.save(materialRequired);
	}
	
	@Override
	public void delete(Long materialRequiredId) {
		materialRequiredRepository.deleteById(materialRequiredId);
	}
	
	@Override
	public List<MaterialRequiredTableDTO> getMaterialsRequiredDataTablesByProjectId(Long projectId, PaginationCriteria pagination) {
		return materialRequiredRepository.findMaterialsRequiredDataTablesByProjectId(projectId, pagination);
	}
	
	@Override
	public Long getMaterialsRequiredCountByProjectId(Long projectId) {
		return materialRequiredRepository.findMaterialsRequiredCountByProjectId(projectId);
	}
	
	@Override
	public List<MaterialRequiredDTO> getMaterialsRequiredByProjectId(Long projectId) {
		return materialRequiredRepository.findMaterialsRequiredByProjectId(projectId);
	}
}
