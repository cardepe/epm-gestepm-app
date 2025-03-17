package com.epm.gestepm.model.user.dao;

import com.epm.gestepm.model.personalexpensesheet.dao.entity.PersonalExpenseSheetStatusEnum;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import com.epm.gestepm.modelapi.expense.dto.ExpenseUserValidateDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpenseValidateDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.personalexpensesheet.dto.PersonalExpenseSheetStatusEnumDto;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectMemberDTO;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
					.where(cb.and(cb.equal(projects.get("id"), projectId), cb.equal(root.get("state"), 0)));

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
					.where(cb.and(cb.not(cb.in(root.get("id")).value(subQuery)), cb.equal(root.get("state"), 0)));
			
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
	
	public List<UserDTO> findNotBossDTOsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<User> root = cQuery.from(User.class);

			Subquery<Long> subQuery = cQuery.subquery(Long.class);
			Root<User> subRoot = subQuery.from(User.class);
			Join<User, Project> subProjects = subRoot.join("bossProjects", JoinType.LEFT);
			subQuery.select(subRoot.get("id")).where(cb.equal(subProjects.get("id"), projectId));

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("surnames"))
					.where(cb.and(cb.and(cb.not(cb.in(root.get("id")).value(subQuery)), cb.equal(root.get("role"), Constants.ROLE_PL_ID)), cb.equal(root.get("state"), 0)));

			cQuery.orderBy(cb.asc(cb.concat(cb.concat(root.get("name"), " "), root.get("surnames"))));
			
			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public List<UserDTO> findAllProjectResponsables() {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<Project> prRoot = cQuery.from(Project.class);
			Join<User, Project> usRoot = prRoot.join("responsables", JoinType.INNER);

			cQuery.multiselect(usRoot.get("id"), usRoot.get("name"), usRoot.get("surnames")).where(cb.equal(usRoot.get("state"), 0));
			cQuery.distinct(true);
			cQuery.orderBy(cb.asc(cb.concat(usRoot.get("name"), usRoot.get("surnames"))));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	public List<ProjectMemberDTO> findAllMembersDTOByProjectId(Long projectId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectMemberDTO> cq = cb.createQuery(ProjectMemberDTO.class);
			
			Root<Project> prRoot = cq.from(Project.class);
			Join<User, Project> usRoot = prRoot.join("users", JoinType.INNER);

			cq.multiselect(usRoot.get("id"), cb.concat(cb.concat(usRoot.get("name"), " "), usRoot.get("surnames")))
					.where(cb.and(cb.equal(prRoot.get("id"), projectId), cb.equal(usRoot.get("state"), 0)));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public List<ProjectMemberDTO> findProjectMemberDTOsByProjectId(Long projectId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectMemberDTO> cq = cb.createQuery(ProjectMemberDTO.class);

			/* #BASE_QUERY */

			Root<Project> prRoot = cq.from(Project.class);
			Join<User, Project> usRoot = prRoot.join("users", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(usRoot.get("id"), cb.concat(cb.concat(usRoot.get("name"), " "), usRoot.get("surnames")),
					usRoot.get("subRole").get("rol"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, prRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, prRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.and(cb.equal(prRoot.get("id"), projectId), cb.equal(usRoot.get("state"), 0));
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ProjectMemberDTO> criteriaQuery = entityManager.createQuery(cq);

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
	
	public List<ProjectMemberDTO> findProjectBossDTOsByProjectId(Long projectId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectMemberDTO> cq = cb.createQuery(ProjectMemberDTO.class);

			/* #BASE_QUERY */

			Root<Project> prRoot = cq.from(Project.class);
			Join<User, Project> usRoot = prRoot.join("bossUsers", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect( usRoot.get("id"), cb.concat(cb.concat(usRoot.get("name"), " "), usRoot.get("surnames")), null);

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, prRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, prRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.and(cb.equal(prRoot.get("id"), projectId), cb.equal(usRoot.get("state"), 0));
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<ProjectMemberDTO> criteriaQuery = entityManager.createQuery(cq);

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

	public Long findProjectMembersCountByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Project> root = cq.from(Project.class);
			Join<User, Project> usRoot = root.join("users");

			cq.select(cb.count(usRoot)).where(cb.and(cb.equal(root.get("id"), projectId), cb.equal(usRoot.get("state"), 0)));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	public Long findProjectBossesCountByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Project> root = cq.from(Project.class);
			Join<User, Project> usRoot = root.join("bossUsers");

			cq.select(cb.count(usRoot)).where(cb.and(cb.equal(root.get("id"), projectId), cb.equal(usRoot.get("state"), 0)));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}

	public List<UserDTO> findUserDTOsByRank(Long rankId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserDTO> cQuery = cb.createQuery(UserDTO.class);

			Root<User> root = cQuery.from(User.class);

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("surnames"), root.get("email"), root.get("role").get("id"),root.get("subRole").get("id"))
				.where(cb.and(cb.equal(root.get("role"), rankId), cb.equal(root.get("state"), 0)));

			cQuery.orderBy(cb.asc(cb.concat(cb.concat(root.get("name"), " "), root.get("surnames"))));
			
			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<UserTableDTO> findUsersDataTables(Integer state, List<Long> projectIds, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserTableDTO> cq = cb.createQuery(UserTableDTO.class);

			/* #BASE_QUERY */

			Root<User> usRoot = cq.from(User.class);
			Join<User, Role> roRoot = usRoot.join("role", JoinType.INNER);
			Join<User, SubRole> srRoot = usRoot.join("subRole", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(usRoot.get("id"), usRoot.get("name"), usRoot.get("surnames"), roRoot.get("roleName"),
					srRoot.get("rol"));
			
			if (state != null) {
				predicates.add(cb.equal(usRoot.get("state"), state));
			}

			if (projectIds != null && !projectIds.isEmpty()) {

				Join<User, Project> prRoot = usRoot.join("projects", JoinType.INNER);

				predicates.add(prRoot.get("id").in(projectIds));
			}

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, roRoot, srRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, roRoot, srRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<UserTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findUsersCount(Integer state, List<Long> projectIds) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<User> root = cq.from(User.class);

			cq.select(cb.count(root));
			
			List<Predicate> predicates = new ArrayList<>();
			
			if (state != null) {
				predicates.add(cb.equal(root.get("state"), state));
			}

			if (projectIds != null && !projectIds.isEmpty()) {

				Join<User, Project> prRoot = root.join("projects", JoinType.INNER);

				predicates.add(prRoot.get("id").in(projectIds));
			}
			
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public UserTableDTO findUserDTOByUserId(Long userId, Integer state) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserTableDTO> cq = cb.createQuery(UserTableDTO.class);
			
			Root<User> usRoot = cq.from(User.class);
			Join<InterventionShare, Role> roRoot = usRoot.join("role", JoinType.INNER);
			Join<InterventionShare, SubRole> srRoot = usRoot.join("subRole", JoinType.INNER);

			cq.multiselect(usRoot.get("id"), usRoot.get("name"), usRoot.get("surnames"), roRoot.get("roleName"), srRoot.get("rol"));

			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(cb.equal(usRoot.get("id"), userId));
			
			if (state != null) {
				predicates.add(cb.equal(usRoot.get("state"), state));
			}
			
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return null;
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
			predicates.add(cb.equal(usRoot.get("state"), 0));
			
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
			subPredicates.add(cb.equal(usRoot.get("state"), 0));
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
