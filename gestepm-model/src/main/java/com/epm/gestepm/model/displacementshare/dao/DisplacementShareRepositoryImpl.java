package com.epm.gestepm.model.displacementshare.dao;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class DisplacementShareRepositoryImpl implements DisplacementShareRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Long findDisplacementSharesCountByUserId(Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<DisplacementShare> root = cq.from(DisplacementShare.class);
			
			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<ShareTableDTO> findShareTableByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);
			
			Root<DisplacementShare> root = cq.from(DisplacementShare.class);

			cq.multiselect(
					root.get("id"),
					root.get("project").get("name"),
					cb.concat(cb.concat(root.get("user").get("name"), " "), root.get("user").get("surnames")),
					root.get("displacementDate"),
					cb.function("date_add_minute", LocalDateTime.class, root.get("displacementDate"), root.get("manualHours")),
					cb.literal("ds")
			).where(cb.equal(root.get("project"), projectId));
			
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<DisplacementShareTableDTO> findDisplacementSharesByUserDataTables(Long userId, PaginationCriteria pagination) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DisplacementShareTableDTO> cq = cb.createQuery(DisplacementShareTableDTO.class);

			/* #BASE_QUERY */
			
			Root<DisplacementShare> isRoot = cq.from(DisplacementShare.class);
			Join<DisplacementShare, Project> prRoot = isRoot.join("project", JoinType.INNER);
			
			List<Predicate> predicates = new ArrayList<>();
			
			cq.multiselect(isRoot.get("id").alias("id"), prRoot.get("name").alias("name"), isRoot.get("displacement").get("title"),
					isRoot.get("displacementDate"), isRoot.get("roundTrip"));
			
			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, isRoot, prRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, isRoot, prRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			Predicate predicateUser = cb.equal(isRoot.get("user"), userId);
			predicates.add(predicateUser);
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<DisplacementShareTableDTO> criteriaQuery = entityManager.createQuery(cq);

			/* #PAGE_NUMBER */
			criteriaQuery.setFirstResult(pagination.getPageNumber());
			/* END #PAGE_NUMBER */
			
			/* #PAGE_SIZE */
			criteriaQuery.setMaxResults(pagination.getPageSize());
			/* END #PAGE_SIZE */
			
			return criteriaQuery.getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<DisplacementShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId, Integer manual) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<DisplacementShare> cq = cb.createQuery(DisplacementShare.class);
		
		Root<DisplacementShare> root = cq.from(DisplacementShare.class);
		
		cq.select(root);
		
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(cb.and(cb.between(
				root.get("displacementDate").as(LocalDateTime.class),
				startDate,
				endDate
		), cb.equal(root.get("user"), userId)));
		
		if (manual != null) {
			predicates.add(cb.equal(root.get("manualDisplacement"), manual));
		}
		
		cq.where(cb.and(predicates.toArray(new Predicate[0])));
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<ExpensesMonthDTO> findTimeMonthDTOByProjectId(Long projectId, Integer year) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpensesMonthDTO> cq = cb.createQuery(ExpensesMonthDTO.class);

			Root<DisplacementShare> root = cq.from(DisplacementShare.class);

			Expression<Integer> timeToSec = cb.prod(root.get("manualHours"), 60);

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
							.when(cb.between(root.get("displacementDate"), janStartDate, janEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), febStartDate, febEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), marStartDate, marEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), aprStartDate, aprEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), mayStartDate, mayEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), junStartDate, junEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), julStartDate, julEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), augStartDate, augEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), sepStartDate, sepEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), octStartDate, octEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), novStartDate, novEndDate), timeToSec)
							.otherwise(0.0)),

					cb.sum(cb.<Number>selectCase()
							.when(cb.between(root.get("displacementDate"), decStartDate, decEndDate), timeToSec)
							.otherwise(0.0)));

			cq.where(cb.equal(root.get("project"), projectId));
			cq.groupBy(root.get("user"));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
