package com.epm.gestepm.model.usermanualsigning.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningTableDTO;

import java.util.Date;
import java.util.List;

public interface UserManualSigningRepositoryCustom {

    List<UserManualSigningTableDTO> findUserManualSigningDTOsByUserId(Long userId, PaginationCriteria pagination);
    Long findUserManualSigningCountByUserId(Long userId);
    List<UserManualSigning> findWeekManualSigningsByUserId(Date startDate, Date endDate, Long userId);

}
