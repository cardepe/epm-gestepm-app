package com.epm.gestepm.model.subrole.dao;

import java.util.List;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.role.dto.RoleTableDTO;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;

public interface SubRoleRepositoryCustom {
	List<RoleTableDTO> findRolesDataTables(PaginationCriteria pagination);
	Long findRolesCount();
	SubRole findByUserId(long userId);
}
