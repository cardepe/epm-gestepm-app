package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.deprecated.constructionshare.dto.ConstructionShare;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ConstructionShareRepositoryImpl implements ConstructionShareRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ConstructionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructionShare> cq = cb.createQuery(ConstructionShare.class);

		Root<ConstructionShare> root = cq.from(ConstructionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
	}
}
