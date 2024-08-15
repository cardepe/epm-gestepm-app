package com.epm.gestepm.model.expensefile.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import com.epm.gestepm.modelapi.user.dto.User;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;

@Repository
public class ExpenseFileRepositoryImpl implements ExpenseFileRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public ExpenseFile findByExpenseId(Long expenseId) {
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseFile> cQuery = cb.createQuery(ExpenseFile.class);

			Root<ExpenseFile> root = cQuery.from(ExpenseFile.class);
			Join<Expense, User> expense = root.join("expense");

			cQuery.select(root)
					.where(cb.equal(expense.get("id"), expenseId));

			return entityManager.createQuery(cQuery).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
