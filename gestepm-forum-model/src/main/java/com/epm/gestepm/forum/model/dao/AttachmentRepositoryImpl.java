package com.epm.gestepm.forum.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepositoryCustom {

	@PersistenceContext(unitName = "forumEntityManager")
	private EntityManager entityManager;
	
}
