package com.epm.gestepm.model.interventionshare.dao;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class InterventionShareRepositoryImpl implements InterventionShareRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<InterventionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionShare> cq = cb.createQuery(InterventionShare.class);

		Root<InterventionShare> root = cq.from(InterventionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
	}
}
