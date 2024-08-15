package com.epm.gestepm.model.displacement.dao;

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
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementTableDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class DisplacementRepositoryImpl implements DisplacementRepositoryCustom {

	private static final Log log = LogFactory.getLog(DisplacementRepositoryImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<DisplacementTableDTO> findDisplacementsDataTables(PaginationCriteria pagination) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DisplacementTableDTO> cq = cb.createQuery(DisplacementTableDTO.class);

			/* #BASE_QUERY */
			
			Root<Displacement> diRoot = cq.from(Displacement.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(diRoot.get("id"), diRoot.get("activityCenter").get("name"), diRoot.get("title"),
					diRoot.get("displacementType"), diRoot.get("totalTime"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, diRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, diRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<DisplacementTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findDisplacementsCount() {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Displacement> root = cq.from(Displacement.class);

			cq.select(cb.count(root));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<DisplacementTableDTO> findDisplacementsDataTablesByProjectId(Long projectId, PaginationCriteria pagination) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DisplacementTableDTO> cq = cb.createQuery(DisplacementTableDTO.class);

			/* #BASE_QUERY */
			Root<Project> prRoot = cq.from(Project.class);
			Join<Displacement, Project> diRoot = prRoot.join("displacements", JoinType.INNER);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(prRoot.get("id"), projectId));
			
			cq.multiselect(diRoot.get("id"), diRoot.get("activityCenter").get("name"), diRoot.get("title"),
					diRoot.get("displacementType"), diRoot.get("totalTime"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, diRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, diRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<DisplacementTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findDisplacementsCountByProjectId(Long projectId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Project> prRoot = cq.from(Project.class);
			Join<Displacement, Project> diRoot = prRoot.join("displacements", JoinType.INNER);

			cq.select(cb.count(diRoot));
			cq.where(cb.equal(prRoot.get("id"), projectId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<DisplacementDTO> findNotDisplacementDTOsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DisplacementDTO> cQuery = cb.createQuery(DisplacementDTO.class);

			Root<Displacement> root = cQuery.from(Displacement.class);

			Subquery<Long> subQuery = cQuery.subquery(Long.class);
			Root<Displacement> subRoot = subQuery.from(Displacement.class);
			Join<Displacement, Project> subProjects = subRoot.join("projects", JoinType.LEFT);
			subQuery.select(subRoot.get("id")).where(cb.equal(subProjects.get("id"), projectId));

			cQuery.multiselect(root.get("id"), root.get("title"))
				  .where(cb.not(cb.in(root.get("id")).value(subQuery)));
			
			cQuery.orderBy(cb.asc(root.get("title")));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<DisplacementDTO> findDisplacementDTOsByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DisplacementDTO> cq = cb.createQuery(DisplacementDTO.class);

			Root<Project> prRoot = cq.from(Project.class);
			Join<Displacement, Project> diRoot = prRoot.join("displacements", JoinType.INNER);

			cq.multiselect(diRoot.get("id"), diRoot.get("title"), diRoot.get("displacementType"), diRoot.get("totalTime"));
			cq.where(cb.equal(prRoot.get("id"), projectId));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public void createProjectDisplacement(Long projectId, Long displacementId) {
		
		Query query = entityManager.createNativeQuery("INSERT INTO project_displacements VALUES (" + projectId +  ", " + displacementId + ")");
		query.executeUpdate();
	}
	
	@Override
	public void deleteProjectDisplacement(Long projectId, Long displacementId) {
		
		Query query = entityManager.createNativeQuery("DELETE FROM project_displacements WHERE displacement_id = " + displacementId +  " AND project_id = " + projectId);
		query.executeUpdate();
	}
}
