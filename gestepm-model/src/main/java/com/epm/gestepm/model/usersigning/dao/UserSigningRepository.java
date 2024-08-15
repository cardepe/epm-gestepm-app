package com.epm.gestepm.model.usersigning.dao;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;

public interface UserSigningRepository extends CrudRepository<UserSigning, Long>, UserSigningRepositoryCustom {
	UserSigning findByUserIdAndEndDate(Long userId, Date endDate);
}
