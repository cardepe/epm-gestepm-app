package com.epm.gestepm.modelapi.usermanualsigning.service;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningTableDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface UserManualSigningService {

    UserManualSigning getById(Long id);

    UserManualSigning save(UserManualSigning userManualSigning);

    void delete(Long id);

    List<UserManualSigningTableDTO> getUserManualSigningDTOsByUserId(Long userId, PaginationCriteria pagination);

    Long getUserManualSigningCountByUser(Long userId);

    List<UserManualSigning> getWeekManualSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
