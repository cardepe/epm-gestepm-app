package com.epm.gestepm.model.deprecated.user.dao;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.deprecated.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<UserDTO> findUserDTOsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<User> root = cQuery.from(User.class);
			Join<User, Project> projects = root.join("projects");

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("surnames"))
					.where(cb.and(cb.equal(projects.get("id"), projectId), cb.equal(root.get("state"), 1)));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public List<UserDTO> findNotUserDTOsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<User> root = cQuery.from(User.class);

			Subquery<Long> subQuery = cQuery.subquery(Long.class);
			Root<User> subRoot = subQuery.from(User.class);
			Join<User, Project> subProjects = subRoot.join("projects", JoinType.LEFT);
			subQuery.select(subRoot.get("id")).where(cb.equal(subProjects.get("id"), projectId));

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("surnames"))
					.where(cb.and(cb.not(cb.in(root.get("id")).value(subQuery)), cb.equal(root.get("state"), 1)));
			
			cQuery.orderBy(cb.asc(cb.concat(cb.concat(root.get("name"), " "), root.get("surnames"))));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public List<UserDTO> findAllUserDTOs() {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<User> root = cQuery.from(User.class);

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("surnames"), root.get("signingId"), cb.toInteger(root.get("state")));
			
			cQuery.orderBy(cb.asc(cb.concat(root.get("name"), root.get("surnames"))));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ExpenseValidateDTO> findExpensesToValidateByUserId(Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseValidateDTO> cq = cb.createQuery(ExpenseValidateDTO.class);
			
			Root<User> usRoot = cq.from(User.class);
			Join<User, Project> projectsRoot = usRoot.join("bossProjects", JoinType.LEFT);
			Join<Project, ExpenseSheet> expenseSheetsRoot = projectsRoot.join("expenseSheets", JoinType.LEFT);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(usRoot.get("id"), userId));
			predicates.add(cb.equal(expenseSheetsRoot.get("status"), PersonalExpenseSheetStatusEnum.PENDING));
			predicates.add(cb.equal(usRoot.get("state"), 1));
			
			/* Subquery ES count */
			
			Subquery<Long> subquery = cq.subquery(Long.class);
			Root<ExpenseSheet> subEsRoot = subquery.from(ExpenseSheet.class);
			
			List<Predicate> subPredicates = new ArrayList<>();
			subPredicates.add(cb.equal(subEsRoot.get("project"), projectsRoot.get("id")));
			subPredicates.add(cb.equal(subEsRoot.get("status"), PersonalExpenseSheetStatusEnumDto.PENDING));
			
			subquery.select(cb.count(subEsRoot.get("id")));
			subquery.where(cb.and(subPredicates.toArray(new Predicate[subPredicates.size()])));
			
			/* End Subquery ES count */
			
			cq.multiselect(projectsRoot.get("id"), projectsRoot.get("name"), subquery.getSelection());
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			cq.groupBy(projectsRoot.get("id"));

			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ExpenseUserValidateDTO> findExpensesToPay() {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ExpenseUserValidateDTO> cq = cb.createQuery(ExpenseUserValidateDTO.class);
			
			Root<User> usRoot = cq.from(User.class);
			Join<User, ExpenseSheet> expenseSheetsRoot = usRoot.join("expenseSheets", JoinType.LEFT);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(expenseSheetsRoot.get("status"), PersonalExpenseSheetStatusEnumDto.APPROVED));
			
			/* Subquery ES count */
			
			Subquery<Long> subquery = cq.subquery(Long.class);
			Root<ExpenseSheet> subEsRoot = subquery.from(ExpenseSheet.class);
			
			List<Predicate> subPredicates = new ArrayList<>();
			subPredicates.add(cb.equal(subEsRoot.get("user"), usRoot.get("id")));
			subPredicates.add(cb.equal(usRoot.get("state"), 1));
			subPredicates.add(cb.equal(subEsRoot.get("status"), PersonalExpenseSheetStatusEnumDto.APPROVED));
			
			subquery.select(cb.count(subEsRoot.get("id")));
			subquery.where(cb.and(subPredicates.toArray(new Predicate[subPredicates.size()])));
			
			/* End Subquery ES count */
			
			cq.multiselect(usRoot.get("id"), cb.concat(cb.concat(usRoot.get("name"), " "), usRoot.get("surnames")), subquery.getSelection());
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			cq.groupBy(usRoot.get("id"));

			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public String findFullNameById(Long userId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<String> cq = cb.createQuery(String.class);

			Root<User> root = cq.from(User.class);

			cq.select(cb.concat(cb.concat(root.get("name"), " "), root.get("surnames"))).where(cb.equal(root.get("id"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
