package com.epm.gestepm.model.usermanualsigning.dao;

import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import org.springframework.data.repository.CrudRepository;

public interface UserManualSigningRepository extends CrudRepository<UserManualSigning, Long>, UserManualSigningRepositoryCustom {

}
