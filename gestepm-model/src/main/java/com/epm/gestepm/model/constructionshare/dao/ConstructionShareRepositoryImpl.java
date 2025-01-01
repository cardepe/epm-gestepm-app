package com.epm.gestepm.model.constructionshare.dao;

import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.*;

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
	public List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);
			
			Root<ConstructionShare> root = cq.from(ConstructionShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("startDate"), root.get("endDate"), cb.literal("cs"));
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));

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
	public List<ConstructionShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		
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
	
	@Override
	public List<DailyPersonalSigningDTO> findDailyConstructionShareDTOByUserIdAndDate(final Long userId, final Integer month, final Integer year) {

		try {

			final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			final CriteriaQuery<DailyPersonalSigningDTO> cq = cb.createQuery(DailyPersonalSigningDTO.class);

			final Root<ConstructionShare> root = cq.from(ConstructionShare.class);

			final Calendar startCalendar = Calendar.getInstance();
			startCalendar.set(Calendar.MONTH, month);
			startCalendar.set(Calendar.YEAR, year);
			startCalendar.set(Calendar.DAY_OF_MONTH, startCalendar.getActualMinimum(Calendar.DAY_OF_MONTH));

			final Calendar endCalendar = Calendar.getInstance();
			endCalendar.set(Calendar.MONTH, month);
			endCalendar.set(Calendar.YEAR, year);
			endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));

			final Date startDate = Utiles.getStartDayDate(startCalendar);
			final Date endDate = Utiles.getEndDayDate(endCalendar);
			
			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));
			predicates.add(cb.between(root.get("startDate"), startDate, endDate));
			predicates.add(cb.isNotNull(root.get("endDate")));
			
			final Expression<java.sql.Time> timeDiff = cb.function("TIMEDIFF", java.sql.Time.class, root.get("endDate"), root.get("startDate"));
			final Expression<Integer> timeToSec = cb.function("TIME_TO_SEC", Integer.class, timeDiff);
			
			cq.multiselect(cb.sum(timeToSec), root.get("startDate"));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			final Expression<Integer> gbYear = cb.function("YEAR", Integer.class, root.get("startDate"));
			final Expression<Integer> gbMonth = cb.function("MONTH", Integer.class, root.get("startDate"));
			final Expression<Integer> gbDay = cb.function("DAY", Integer.class, root.get("startDate"));

			cq.groupBy(gbYear, gbMonth, gbDay);
			
			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
}
