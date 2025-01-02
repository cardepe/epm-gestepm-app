package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ConstructionShareRepositoryImpl implements ConstructionShareRepositoryCustom {

	private static final Log log = LogFactory.getLog(ConstructionShareRepositoryImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {

		try {

			final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			final CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

			final Root<ConstructionShare> root = cq.from(ConstructionShare.class);
			final Join<Project, ConstructionShare> projectRoot = root.join("project", JoinType.INNER);
			final Join<ActivityCenter, Project> activityCenterRoot = projectRoot.join("activityCenter", JoinType.INNER);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("startDate"), root.get("endDate"), cb.literal("cs"));

			final List<Predicate> predicates = new ArrayList<>();

			predicates.add(cb.equal(activityCenterRoot.get("id"), activityCenterId));

			if (id != null) {
				predicates.add(cb.equal(root.get("id"), id));
			}

			if (progress != null) {

				if (progress == 1) {
					predicates.add(cb.isNull(root.get("endDate")));
				} else {
					predicates.add(cb.isNotNull(root.get("endDate")));
				}
			}

			if (projectId != null) {
				predicates.add(cb.equal(root.get("project"), projectId));
			}

			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ShareTableDTO> findShareTableByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

			Root<ConstructionShare> root = cq.from(ConstructionShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), cb.concat(cb.concat(root.get("user").get("name"), " "), root.get("user").get("surnames")), root.get("startDate"), root.get("endDate"), cb.literal("cs")).where(cb.equal(root.get("project"), projectId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

			Root<ConstructionShare> root = cq.from(ConstructionShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), cb.concat(cb.concat(root.get("user").get("name"), " "), root.get("user").get("surnames")), root.get("startDate"), root.get("endDate"), cb.literal("cs")).where(cb.equal(root.get("userSigning"), userSigningId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ConstructionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructionShare> cq = cb.createQuery(ConstructionShare.class);

		Root<ConstructionShare> root = cq.from(ConstructionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<ConstructionShare> findWeekSigningsByProjectId(LocalDateTime startDate, LocalDateTime endDate, Long projectId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<ConstructionShare> cq = cb.createQuery(ConstructionShare.class);

		Root<ConstructionShare> root = cq.from(ConstructionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("project"), projectId)));

		return entityManager.createQuery(cq).getResultList();
	}
}
