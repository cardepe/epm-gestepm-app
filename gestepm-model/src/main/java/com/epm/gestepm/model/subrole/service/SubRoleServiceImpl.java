package com.epm.gestepm.model.subrole.service;

import com.epm.gestepm.model.subrole.dao.SubRoleRepository;
import com.epm.gestepm.modelapi.role.dto.RoleTableDTO;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SubRoleServiceImpl implements SubRoleService {
	
	@Autowired
	private SubRoleRepository subRoleDao;

	public SubRole getSubRoleById(long subRoleId) {
		return subRoleDao.findById(subRoleId).orElse(null);
	}
	
	public List<SubRole> getAll() {
		return (List<SubRole>) subRoleDao.findAll();
	}

	@Override
	public void save(SubRole subRole) {
		subRoleDao.save(subRole);	
	}
	
	@Override
	public void delete(Long id) {
		subRoleDao.deleteById(id);
	}
	
	@Override
	public List<RoleTableDTO> getRolesDataTables(PaginationCriteria pagination) {
		return subRoleDao.findRolesDataTables(pagination);
	}
	
	@Override
	public Long getRolesCount() {
		return subRoleDao.findRolesCount();
	}
	
	@Override
	public SubRole getByUserId(long userId) {
		return subRoleDao.findByUserId(userId);
	}
	
	@Override
	public SubRole getByRol(String rol) {
		return subRoleDao.findByRol(rol);
	}
}
