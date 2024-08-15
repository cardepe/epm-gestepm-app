package com.epm.gestepm.model.expense.dao;

import java.util.ArrayList;
import java.util.Collections;
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

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.expense.dto.ExpenseTableDTO;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class ExpenseRepositoryImpl implements ExpenseRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Expense> findExpensesBySheetId(Long sheetId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Expense> cq = cb.createQuery(Expense.class);

			Root<Expense> root = cq.from(Expense.class);

			cq.select(root).where(cb.equal(root.get("expenseSheet"), sheetId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Long findExpensesCountBySheetId(Long sheetId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Expense> root = cq.from(Expense.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("expenseSheet"), sheetId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<ExpenseTableDTO> findExpensesBySheetDataTables(Long sheetId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseTableDTO> cq = cb.createQuery(ExpenseTableDTO.class);

			/* #BASE_QUERY */

			Root<Expense> exRoot = cq.from(Expense.class);
			Join<Expense, PriceType> piRoot = exRoot.join("priceType", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(exRoot.get("id").alias("id"), piRoot.get("name").alias("description"),
					exRoot.get("justification").alias("justification"),
					exRoot.get("date").alias("reportDate"), exRoot.get("total").alias("total"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, exRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, exRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(exRoot.get("expenseSheet"), sheetId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ExpenseTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
}
