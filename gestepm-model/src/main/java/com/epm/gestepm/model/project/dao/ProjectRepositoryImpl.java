package com.epm.gestepm.model.project.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.customer.dto.Customer;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

	private static final Log log = LogFactory.getLog(ProjectRepositoryImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Project findByIdAndUserId(Long id, Long userId) {
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Project> cQuery = cb.createQuery(Project.class);

			Root<Project> root = cQuery.from(Project.class);
			Join<Project, User> users = root.join("users");

			cQuery.select(root)
					.where(cb.and(cb.equal(root.get("id"), id), cb.equal(users.get("id"), userId)));

			return entityManager.createQuery(cQuery).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
	
	@Override 
	public Project findByIdAndBossId(Long id, Long bossId) {
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Project> cQuery = cb.createQuery(Project.class);

			Root<Project> root = cQuery.from(Project.class);
			Join<Project, User> users = root.join("bossUsers");

			cQuery.select(root)
					.where(cb.and(cb.equal(root.get("id"), id), cb.equal(users.get("id"), bossId)));

			return entityManager.createQuery(cQuery).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<ProjectListDTO> findAllProjectsDTOs() {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectListDTO> cQuery = cb.createQuery(ProjectListDTO.class);

			Root<Project> root = cQuery.from(Project.class);

			cQuery.multiselect(root.get("id"), root.get("name"), root.get("station"));
			
			cQuery.orderBy(cb.asc(root.get("name")));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ProjectListDTO> findProjectsDTOByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectListDTO> cQuery = cb.createQuery(ProjectListDTO.class);

			Root<User> root = cQuery.from(User.class);
			Join<User, Project> projects = root.join("projects");
			
			Join<Project, Customer> customer = projects.join("customer", JoinType.LEFT);

			cQuery.multiselect(projects.get("id"), projects.get("name"), projects.get("station"), customer.get("mainEmail"))
					.where(cb.equal(root.get("id"), userId));
			
			cQuery.orderBy(cb.asc(projects.get("name")));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ProjectListDTO> findBossProjectsDTOByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectListDTO> cQuery = cb.createQuery(ProjectListDTO.class);

			Root<User> root = cQuery.from(User.class);
			Join<User, Project> projects = root.join("bossProjects");

			cQuery.multiselect(projects.get("id"), projects.get("name"), projects.get("station"))
					.where(cb.equal(root.get("id"), userId));
			
			cQuery.orderBy(cb.asc(projects.get("name")));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ProjectListDTO> findStationDTOs() {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectListDTO> cQuery = cb.createQuery(ProjectListDTO.class);

			Root<Project> root = cQuery.from(Project.class);

			cQuery.multiselect(root.get("id"), root.get("name"))
					.where(cb.isNull(root.get("station")));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<ProjectTableDTO> findAllProjectsDataTables(PaginationCriteria pagination, Object[] params) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectTableDTO> cq = cb.createQuery(ProjectTableDTO.class);

			/* #BASE_QUERY */
			
			Root<Project> prRoot = cq.from(Project.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(prRoot.get("id"), prRoot.get("name"), prRoot.get("startDate"), prRoot.get("objectiveDate"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, prRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			
			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, prRoot, params);
				predicates.addAll(filterPredicates);
			}
			
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, prRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<ProjectTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public List<ProjectTableDTO> findProjectsByUserMemberDataTables(Long userId, PaginationCriteria pagination, Object[] params) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectTableDTO> cq = cb.createQuery(ProjectTableDTO.class);

			/* #BASE_QUERY */
			
			Root<Project> prRoot = cq.from(Project.class);
			Join<Project, User> usRoot = prRoot.join("users", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(prRoot.get("id"), prRoot.get("name"), prRoot.get("startDate"), prRoot.get("objectiveDate"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, prRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}

			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, prRoot, params);
				predicates.addAll(filterPredicates);
			}

			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, prRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			Predicate predicateUser = cb.equal(usRoot.get("id"), userId);
			predicates.add(predicateUser);
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<ProjectTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public List<ProjectTableDTO> findProjectsByUserBossDataTables(Long userId, PaginationCriteria pagination, Object[] params) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectTableDTO> cq = cb.createQuery(ProjectTableDTO.class);

			/* #BASE_QUERY */
			
			Root<Project> prRoot = cq.from(Project.class);
			Join<Project, User> usRoot = prRoot.join("bossUsers", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(prRoot.get("id"), prRoot.get("name"), prRoot.get("startDate"), prRoot.get("objectiveDate"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, usRoot, prRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}

			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, prRoot, params);
				predicates.addAll(filterPredicates);
			}

			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, usRoot, prRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			Predicate predicateUser = cb.equal(usRoot.get("id"), userId);
			predicates.add(predicateUser);
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<ProjectTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findProjectsCountByUserMember(Long userId, Object[] params) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<Project> root = cq.from(Project.class);
			Join<Project, User> uRoot = root.join("users");
			
			List<Predicate> predicates = new ArrayList<>();

			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, root, params);
				predicates.addAll(filterPredicates);
			}
			
			Predicate userFilter = cb.equal(uRoot.get("id"), userId);
			predicates.add(userFilter);
			
			cq.select(cb.count(root));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public Long findProjectsCountByUserBoss(Long userId, Object[] params) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<Project> root = cq.from(Project.class);
			Join<Project, User> uRoot = root.join("bossUsers");
			
			List<Predicate> predicates = new ArrayList<>();

			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, root, params);
				predicates.addAll(filterPredicates);
			}
			
			Predicate userFilter = cb.equal(uRoot.get("id"), userId);
			predicates.add(userFilter);
			
			cq.select(cb.count(root));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public Long findAllProjectsCount(Object[] params) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<Project> root = cq.from(Project.class);
			
			List<Predicate> predicates = new ArrayList<>();

			if (params != null) {
				List<Predicate> filterPredicates = filterProjects(cb, root, params);
				predicates.addAll(filterPredicates);
			}
			
			cq.select(cb.count(root));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<ProjectDTO> findNotProjectDTOsByUserId(Long userId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ProjectDTO> cQuery = cb.createQuery(ProjectDTO.class);

			Root<Project> root = cQuery.from(Project.class);

			Subquery<Long> subQuery = cQuery.subquery(Long.class);
			Root<Project> subRoot = subQuery.from(Project.class);
			Join<Project, User> subUsers = subRoot.join("users", JoinType.LEFT);
			subQuery.select(subRoot.get("id")).where(cb.equal(subUsers.get("id"), userId));

			cQuery.multiselect(root.get("id"), root.get("name"))
					.where(cb.not(cb.in(root.get("id")).value(subQuery)));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void createMember(Long projectId, Long userId) {
		
		Query query = entityManager.createNativeQuery("INSERT INTO users_projects VALUES (" + userId +  ", " + projectId + ")");
		query.executeUpdate();
	}
	
	@Override
	public void deleteMember(Long projectId, Long userId) {
		
		Query query = entityManager.createNativeQuery("DELETE FROM users_projects WHERE user_id = " + userId +  " AND project_id = " + projectId);
		query.executeUpdate();
	}
	
	@Override
	public void createUserBoss(Long projectId, Long userId) {
		
		Query query = entityManager.createNativeQuery("INSERT INTO project_bosses VALUES (" + projectId +  ", " + userId + ")");
		query.executeUpdate();
	}
	
	@Override
	public void deleteUserBoss(Long projectId, Long userId) {
		
		Query query = entityManager.createNativeQuery("DELETE FROM project_bosses WHERE user_id = " + userId +  " AND project_id = " + projectId);
		query.executeUpdate();
	}
	
	@Override
	public void deleteAllUserBossByUserId(Long userId) {
		
		Query query = entityManager.createNativeQuery("DELETE FROM project_bosses WHERE user_id = " + userId);
		query.executeUpdate();
	}
	
	private List<Predicate> filterProjects(CriteriaBuilder cb, Root<Project> root, Object[] params) {
		
		List<Predicate> predicates = new ArrayList<>();

		Long projectId = (Long) params[0];
		
		if (projectId != null) {
			Predicate projectFilter = cb.equal(root.get("id"), projectId);
			predicates.add(projectFilter);
		}
		
		Long responsableId = (Long) params[1];
		
		if (responsableId != null) {
			
			Join<Project, User> usRoot = root.join("responsables", JoinType.INNER);
			
			Predicate responsableFilter = cb.equal(usRoot.get("id"), responsableId);
			predicates.add(responsableFilter);
		}
		
		Integer station = (Integer) params[2];
		
		if (station != null) {
			Predicate stationFilter = cb.equal(root.get("station"), station);
			predicates.add(stationFilter);
		}

		return predicates;
	}
}
