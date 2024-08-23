package com.epm.gestepm.model.displacement.service;

import com.epm.gestepm.model.displacement.dao.DisplacementRepository;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementTableDTO;
import com.epm.gestepm.modelapi.displacement.service.DisplacementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DisplacementServiceImpl implements DisplacementService {

	@Autowired
	private DisplacementRepository displacementRepository;

	public Displacement getDisplacementById(long displacementId) {
		return displacementRepository.findById(displacementId).orElse(null);
	}
	
	public List<Displacement> getAll() {
		return (List<Displacement>) displacementRepository.findAll();
	}

	@Override
	public void save(Displacement displacement) {
		displacementRepository.save(displacement);	
	}
	
	@Override
	public void createProjectDisplacement(Long projectId, Long displacementId) {
		displacementRepository.createProjectDisplacement(projectId, displacementId);
	}
	
	@Override
	public void deleteProjectDisplacement(Long projectId, Long displacementId) {
		displacementRepository.deleteProjectDisplacement(projectId, displacementId);
	}
	
	@Override
	public void delete(Long id) {
		displacementRepository.deleteById(id);
	}
	
	@Override
	public List<DisplacementTableDTO> getDisplacementsDataTablesByProjectId(Long projectId, PaginationCriteria pagination) {
		return displacementRepository.findDisplacementsDataTablesByProjectId(projectId, pagination);
	}
	
	@Override
	public Long getDisplacementsCountByProjectId(Long projectId) {
		return displacementRepository.findDisplacementsCountByProjectId(projectId);
	}
	
	@Override
	public List<DisplacementDTO> getNotDisplacementDTOsByProjectId(Long projectId) {
		return displacementRepository.findNotDisplacementDTOsByProjectId(projectId);
	}
	
	@Override
	public List<DisplacementDTO> getDisplacementDTOsByProjectId(Long projectId) {
		return displacementRepository.findDisplacementDTOsByProjectId(projectId);
	}
}
