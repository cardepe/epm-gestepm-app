package com.epm.gestepm.model.interventionprshare.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;

@Repository
public class InterventionPrShareRepositoryImpl implements InterventionPrShareRepositoryCustom {

	private static final Log log = LogFactory.getLog(InterventionPrShareRepositoryImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("startDate"), root.get("endDate"), cb.literal("ips"));

			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("project").get("activityCenter"), activityCenterId));

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
			
			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("startDate"), root.get("endDate"), cb.literal("ips"));
			
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
			
			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), cb.concat(cb.concat(root.get("user").get("name"), " "), root.get("user").get("surnames")), root.get("startDate"), root.get("endDate"), cb.literal("ips")).where(cb.equal(root.get("project"), projectId));
			
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
			
			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), cb.concat(cb.concat(root.get("user").get("name"), " "), root.get("user").get("surnames")), root.get("startDate"), root.get("endDate"), cb.literal("ips")).where(cb.equal(root.get("userSigning"), userSigningId));
			
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<InterventionPrShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionPrShare> cq = cb.createQuery(InterventionPrShare.class);
		
		Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);
		
		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<InterventionPrShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionPrShare> cq = cb.createQuery(InterventionPrShare.class);

		Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("project"), projectId)));

		return entityManager.createQuery(cq).getResultList();
	}
	
	@Override
	public List<DailyPersonalSigningDTO> findDailyInterventionPrShareDTOByUserIdAndYear(Long userId, int year) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DailyPersonalSigningDTO> cq = cb.createQuery(DailyPersonalSigningDTO.class);

			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);
			
			Date yearStartDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date yearEndDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));
			predicates.add(cb.between(root.get("startDate"), yearStartDate, yearEndDate));
			
			Expression<java.sql.Time> timeDiff = cb.function("TIMEDIFF", java.sql.Time.class, root.get("endDate"), root.get("startDate"));
			Expression<Integer> timeToSec = cb.function("TIME_TO_SEC", Integer.class, timeDiff);
			
			cq.multiselect(cb.sum(timeToSec), root.get("startDate"));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			Expression<Integer> gbYear = cb.function("YEAR", Integer.class, root.get("startDate"));
			Expression<Integer> gbMonth = cb.function("MONTH", Integer.class, root.get("startDate"));
			Expression<Integer> gbDay = cb.function("DAY", Integer.class, root.get("startDate"));
			cq.groupBy(gbYear, gbMonth, gbDay);
			
			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpensesMonthDTO> cq = cb.createQuery(ExpensesMonthDTO.class);

			Root<InterventionPrShare> root = cq.from(InterventionPrShare.class);
			
			Expression<java.sql.Time> timeDiff = cb.function("TIMEDIFF", java.sql.Time.class, root.get("endDate"), root.get("startDate"));
			Expression<Integer> timeToSec = cb.function("TIME_TO_SEC", Integer.class, timeDiff);
			
			Date janStartDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date janEndDate = Utiles.transformSimpleStringToDate("31-01-" + year);			
			
			Date febStartDate = Utiles.transformSimpleStringToDate("01-02-" + year);
			Date febEndDate = Utiles.transformSimpleStringToDate("29-02-" + year);
			
			Date marStartDate = Utiles.transformSimpleStringToDate("01-03-" + year);
			Date marEndDate = Utiles.transformSimpleStringToDate("31-03-" + year);

			Date aprStartDate = Utiles.transformSimpleStringToDate("01-04-" + year);
			Date aprEndDate = Utiles.transformSimpleStringToDate("30-04-" + year);
			
			Date mayStartDate = Utiles.transformSimpleStringToDate("01-05-" + year);
			Date mayEndDate = Utiles.transformSimpleStringToDate("31-05-" + year);
			
			Date junStartDate = Utiles.transformSimpleStringToDate("01-06-" + year);
			Date junEndDate = Utiles.transformSimpleStringToDate("30-06-" + year);
			
			Date julStartDate = Utiles.transformSimpleStringToDate("01-07-" + year);
			Date julEndDate = Utiles.transformSimpleStringToDate("31-07-" + year);
			
			Date augStartDate = Utiles.transformSimpleStringToDate("01-08-" + year);
			Date augEndDate = Utiles.transformSimpleStringToDate("31-08-" + year);
			
			Date sepStartDate = Utiles.transformSimpleStringToDate("01-09-" + year);
			Date sepEndDate = Utiles.transformSimpleStringToDate("30-09-" + year);
			
			Date octStartDate = Utiles.transformSimpleStringToDate("01-10-" + year);
			Date octEndDate = Utiles.transformSimpleStringToDate("31-10-" + year);
			
			Date novStartDate = Utiles.transformSimpleStringToDate("01-11-" + year);
			Date novEndDate = Utiles.transformSimpleStringToDate("30-11-" + year);
			
			Date decStartDate = Utiles.transformSimpleStringToDate("01-12-" + year);
			Date decEndDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			cq.multiselect(root.get("user").get("id"), 
					
					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), janStartDate, janEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), febStartDate, febEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), marStartDate, marEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), aprStartDate, aprEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), mayStartDate, mayEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), junStartDate, junEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), julStartDate, julEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), augStartDate, augEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), sepStartDate, sepEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), octStartDate, octEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), novStartDate, novEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("startDate"), decStartDate, decEndDate), timeToSec)
							.otherwise(0.0)));

			cq.where(cb.equal(root.get("project"), projectId));
			cq.groupBy(root.get("user"));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
