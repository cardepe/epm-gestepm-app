package com.epm.gestepm.forum.model.dao;

import com.epm.gestepm.forum.model.api.dto.UserForum;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserForumRepository extends CrudRepository<UserForum, Long>, UserForumRepositoryCustom {
	
	@Query(value = "SELECT u.id FROM UserForum u WHERE u.username = :username")
	public Long findIdByUsername(@Param("username") String username);
	
	public UserForum findByEmail(String email);
}
