package com.epm.gestepm.model.expensesheet.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectExpenseSheetDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class ExpenseSheetRepositoryImpl implements ExpenseSheetRepositoryCustom {

	private static final Log log = LogFactory.getLog(ExpenseSheetRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;	

	@Override
	public ExpenseSheet findByIdAndUserId(Long id, Long userId) {
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheet> cQuery = cb.createQuery(ExpenseSheet.class);

			Root<ExpenseSheet> root = cQuery.from(ExpenseSheet.class);
			Join<ExpenseSheet, User> user = root.join("user");

			cQuery.select(root)
					.where(cb.and(cb.equal(root.get("id"), id), cb.equal(user.get("id"), userId)));

			return entityManager.createQuery(cQuery).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ExpenseSheet findByIdAndProjectId(Long id, Long projectId) {
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheet> cQuery = cb.createQuery(ExpenseSheet.class);

			Root<ExpenseSheet> root = cQuery.from(ExpenseSheet.class);
			Join<ExpenseSheet, Project> project = root.join("project");

			cQuery.select(root)
					.where(cb.and(cb.equal(root.get("id"), id), cb.equal(project.get("id"), projectId)));

			return entityManager.createQuery(cQuery).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ExpenseSheet> findExpenseSheetsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheet> cq = cb.createQuery(ExpenseSheet.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(root).where(cb.equal(root.get("project"), projectId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ExpenseSheet> findExpenseSheetsByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheet> cq = cb.createQuery(ExpenseSheet.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(root).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ExpenseSheet> findExpenseSheetsByUserIdAndStatus(Long userId, String status) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheet> cq = cb.createQuery(ExpenseSheet.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(root).where(cb.and(cb.equal(root.get("user"), userId), cb.equal(root.get("status"), status)));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Long findExpenseSheetsCountByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public Long findExpenseSheetsCountByRRHH(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(cb.count(root)).where(cb.and(cb.equal(root.get("user"), userId))); // , cb.equal(root.get("status"), Constants.STATUS_PENDING))); // was cb.not(

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<ExpenseSheetTableDTO> findExpenseSheetsByUserDataTables(Long userId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheetTableDTO> cq = cb.createQuery(ExpenseSheetTableDTO.class);

			/* #BASE_QUERY */

			Root<ExpenseSheet> esRoot = cq.from(ExpenseSheet.class);
			Join<ExpenseSheet, Project> prRoot = esRoot.join("project", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();
			
			Subquery<Double> subquery = cq.subquery(Double.class);
			Root<Expense> subExRoot = subquery.from(Expense.class);
			subquery.select(cb.sum(subExRoot.get("total")));
			subquery.where(cb.equal(subExRoot.get("expenseSheet"), esRoot.get("id")));

			cq.multiselect(esRoot.get("id").alias("id"), esRoot.get("name").alias("name"),
					prRoot.get("name").alias("projectName"), esRoot.get("status").alias("status"),
					esRoot.get("creationDate").alias("creationDate"), subquery.getSelection());

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, esRoot, prRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, esRoot, prRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(esRoot.get("user"), userId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ExpenseSheetTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public List<ExpenseSheetTableDTO> findExpenseSheetsByRRHHDataTables(Long userId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseSheetTableDTO> cq = cb.createQuery(ExpenseSheetTableDTO.class);

			/* #BASE_QUERY */

			Root<ExpenseSheet> esRoot = cq.from(ExpenseSheet.class);
			Join<ExpenseSheet, Project> prRoot = esRoot.join("project", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();
			
			Subquery<Double> subquery = cq.subquery(Double.class);
			Root<Expense> subExRoot = subquery.from(Expense.class);
			subquery.select(cb.sum(subExRoot.get("total")));
			subquery.where(cb.equal(subExRoot.get("expenseSheet"), esRoot.get("id")));

			cq.multiselect(esRoot.get("id").alias("id"), esRoot.get("name").alias("name"),
					prRoot.get("name").alias("projectName"), esRoot.get("status").alias("status"),
					esRoot.get("creationDate").alias("creationDate"), subquery.getSelection());

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, esRoot, prRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, esRoot, prRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(esRoot.get("user"), userId);
			predicates.add(predicateUser);
			
			// Predicate predicateNotPending = cb.equal(esRoot.get("status"), Constants.STATUS_PENDING); // was cb.not(
			// predicates.add(predicateNotPending);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ExpenseSheetTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public List<ProjectExpenseSheetDTO> findProjectExpenseSheetDTOsByProjectId(Long projectId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectExpenseSheetDTO> cq = cb.createQuery(ProjectExpenseSheetDTO.class);

			/* #BASE_QUERY */

			Root<ExpenseSheet> exRoot = cq.from(ExpenseSheet.class);
			Join<ExpenseSheet, Project> prRoot = exRoot.join("project", JoinType.INNER);
			Join<ExpenseSheet, User> usRoot = exRoot.join("user", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			Subquery<Double> subquery = cq.subquery(Double.class);
			Root<Expense> subExRoot = subquery.from(Expense.class);
			subquery.select(cb.sum(subExRoot.get("total")));
			subquery.where(cb.equal(subExRoot.get("expenseSheet"), exRoot.get("id")));
			
			cq.multiselect(exRoot.get("id"), exRoot.get("name"),
					cb.concat(cb.concat(usRoot.get("name"), " "), usRoot.get("surnames")),
					exRoot.get("status"), exRoot.get("creationDate"),
					subquery.getSelection());

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, exRoot, prRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, exRoot, prRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(prRoot.get("id"), projectId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ProjectExpenseSheetDTO> criteriaQuery = entityManager.createQuery(cq);

			/* #PAGE_NUMBER */
			criteriaQuery.setFirstResult(pagination.getPageNumber());
			/* END #PAGE_NUMBER */

			/* #PAGE_SIZE */
			criteriaQuery.setMaxResults(pagination.getPageSize());
			/* END #PAGE_SIZE */

			return criteriaQuery.getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public Long findProjectExpenseSheetDTOsCountByProjectId(Long projectId) {
	
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<ExpenseSheet> root = cq.from(ExpenseSheet.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("project"), projectId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<ExpensesMonthDTO> findExpensesMonthDTOByProjectId(Long projectId, Integer year) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpensesMonthDTO> cq = cb.createQuery(ExpensesMonthDTO.class);

			Root<ExpenseSheet> esRoot = cq.from(ExpenseSheet.class);
			Root<Expense> exRoot = cq.from(Expense.class);
			
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
			
			cq.multiselect(esRoot.get("user").get("id"), 
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), janStartDate, janEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), febStartDate, febEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), marStartDate, marEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), aprStartDate, aprEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), mayStartDate, mayEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), junStartDate, junEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), julStartDate, julEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), augStartDate, augEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), sepStartDate, sepEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), octStartDate, octEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), novStartDate, novEndDate)),
						exRoot.get("total"))
					.otherwise(0.0)),
				
				cb.sum(
					cb.<Double>selectCase()
					.when(
						cb.and(
							cb.equal(esRoot.get("id"), exRoot.get("expenseSheet")),
							cb.between(exRoot.get("startDate"), decStartDate, decEndDate)),
						exRoot.get("total"))
					.otherwise(0.0))
				);
			
			cq.where(cb.equal(esRoot.get("project"), projectId));
			cq.groupBy(esRoot.get("user"));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public Double findTotalYearExpensesByProjectId(Long projectId, Integer year) {
	
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Double> cq = cb.createQuery(Double.class);

			Root<ExpenseSheet> esRoot = cq.from(ExpenseSheet.class);
			Root<Expense> exRoot = cq.from(Expense.class);
			
			Date startDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date endDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(esRoot.get("project"), projectId));
			predicates.add(cb.equal(exRoot.get("expenseSheet"), esRoot.get("id")));
			predicates.add(cb.between(exRoot.get("startDate"), startDate, endDate));
			
			cq.select(cb.sum(exRoot.get("total")));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			Double returnValue = entityManager.createQuery(cq).getSingleResult();
			
			return returnValue == null ? 0.0 : returnValue;

		} catch (Exception e) {
			return 0.0;
		}
	}

}
