package com.epm.gestepm.forum.model.dao;

import com.epm.gestepm.forum.model.api.dto.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PostRepositoryCustom {

}
