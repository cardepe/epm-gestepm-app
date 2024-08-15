package com.epm.gestepm.modelapi.subrole.service;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.role.dto.RoleTableDTO;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;

public interface SubRoleService {
	SubRole getSubRoleById(long subRoleId);
	List<SubRole> getAll();
	void save(SubRole subRole);
	void delete(Long id);
	List<RoleTableDTO> getRolesDataTables(PaginationCriteria pagination);
	Long getRolesCount();
	SubRole getByUserId(long userId);
	SubRole getByRol(String rol);
}
