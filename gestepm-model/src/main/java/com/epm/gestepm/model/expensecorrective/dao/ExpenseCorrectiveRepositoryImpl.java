package com.epm.gestepm.model.expensecorrective.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class ExpenseCorrectiveRepositoryImpl implements ExpenseCorrectiveRepositoryCustom {

	private static final Log log = LogFactory.getLog(ExpenseCorrectiveRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long findExpenseCorrectivesCountByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<ExpenseCorrective> root = cq.from(ExpenseCorrective.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<ExpenseCorrectiveTableDTO> findExpenseCorrectivesByUserDataTables(Long userId,
																				  PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseCorrectiveTableDTO> cq = cb.createQuery(ExpenseCorrectiveTableDTO.class);

			/* #BASE_QUERY */

			Root<ExpenseCorrective> ecRoot = cq.from(ExpenseCorrective.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(ecRoot.get("id"), ecRoot.get("project").get("name"), ecRoot.get("creationDate"),
					ecRoot.get("description"), ecRoot.get("cost"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, ecRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, ecRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(ecRoot.get("user"), userId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ExpenseCorrectiveTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public ExpensesMonthDTO findExpensesMonthDTOByProjectId(Long projectId, Integer year) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpensesMonthDTO> cq = cb.createQuery(ExpensesMonthDTO.class);

			Root<ExpenseCorrective> ecRoot = cq.from(ExpenseCorrective.class);
			
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
			
			cq.multiselect(
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), janStartDate, janEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), febStartDate, febEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), marStartDate, marEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), aprStartDate, aprEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), mayStartDate, mayEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), junStartDate, junEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), julStartDate, julEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), augStartDate, augEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), sepStartDate, sepEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), octStartDate, octEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), novStartDate, novEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.between(ecRoot.get("creationDate"), decStartDate, decEndDate),
						ecRoot.get("cost"))
					.otherwise(0.0))
				);
			
			cq.where(cb.equal(ecRoot.get("project"), projectId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@Override
	public Double findTotalYearExpensesByProjectId(Long projectId, Integer year) {
	
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> cq = cb.createQuery(Double.class);

			Root<ExpenseCorrective> ecRoot = cq.from(ExpenseCorrective.class);
			
			Date startDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date endDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(ecRoot.get("project"), projectId));
			predicates.add(cb.between(ecRoot.get("creationDate"), startDate, endDate));
			
			cq.select(cb.sum(ecRoot.get("cost")));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			Double returnValue = entityManager.createQuery(cq).getSingleResult();
			
			return returnValue == null ? 0.0 : returnValue;

		} catch (Exception e) {
			return 0.0;
		}
	}
}
