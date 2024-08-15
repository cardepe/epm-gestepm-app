package com.epm.gestepm.forum.model.dao;

import com.epm.gestepm.forum.model.api.dto.Topic;
import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long>, TopicRepositoryCustom {

}
