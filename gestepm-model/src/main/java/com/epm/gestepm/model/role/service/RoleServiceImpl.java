package com.epm.gestepm.model.role.service;

import java.util.List;

import com.epm.gestepm.model.role.dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.role.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleDao;

	public Role getRoleById(long roleId) {
		return roleDao.findById(roleId).orElse(null);
	}
	
	public List<Role> getAll() {
		return (List<Role>) roleDao.findAll();
	}
}
