package com.epm.gestepm.model.subfamily.service;

import java.util.ArrayList;
import java.util.List;

import com.epm.gestepm.model.subfamily.dao.SubFamilyRepository;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;

@Service
@Transactional
public class SubFamilyServiceImpl implements SubFamilyService {
	
	@Autowired
	private SubFamilyRepository subFamilyRepository;

	@Override
	public SubFamily getById(Long id) {
		return subFamilyRepository.findById(id).get();
	}
	
	@Override
	public List<RoleDTO> getSubRolsById(Long id) {
		return subFamilyRepository.findSubRolsById(id);
	}
	
	@Override
	public List<SubFamilyDTO> getByFamily(Long familyId) {
		
		List<SubFamilyDTO> subFamilyDTOs = subFamilyRepository.findByFamily(familyId);
		
		for (SubFamilyDTO subFamilyDTO : subFamilyDTOs) {
			
			List<RoleDTO> subRoles = subFamilyRepository.findSubRolsById(subFamilyDTO.getId());
			
			if (subRoles != null && !subRoles.isEmpty()) {
				
				String subRolesName = "";
				List<Long> subRolesId = new ArrayList<>();
				
				for (RoleDTO roleDTO : subRoles) {
					subRolesName += roleDTO.getName() + ", ";
					subRolesId.add(roleDTO.getId());
				}
				
				if (subRolesName.length() > 0) {
					subRolesName = subRolesName.substring(0, subRolesName.length() - 2);
				}
				
				subFamilyDTO.setSubRoleNames(subRolesName);
				subFamilyDTO.setSubRoles(subRolesId);
			}
		}
		
		return subFamilyDTOs;
	}
	
	@Override
	public List<SubFamily> findAll() {
		return (List<SubFamily>) subFamilyRepository.findAll();
	}
	
	@Override
	public SubFamily save(SubFamily subFamily) {
		return subFamilyRepository.save(subFamily);
	}
	
	@Override
	public void delete(Long subFamilyId) {
		subFamilyRepository.deleteById(subFamilyId);
	}
}
