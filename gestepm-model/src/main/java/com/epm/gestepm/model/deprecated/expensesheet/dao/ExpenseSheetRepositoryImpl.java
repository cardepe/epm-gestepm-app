package com.epm.gestepm.model.deprecated.expensesheet.dao;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.deprecated.expense.dto.Expense;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpensesMonthDTO;
import com.epm.gestepm.modelapi.deprecated.expensesheet.dto.ExpenseSheet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class ExpenseSheetRepositoryImpl implements ExpenseSheetRepositoryCustom {

	private static final Log log = LogFactory.getLog(ExpenseSheetRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
