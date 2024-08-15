package com.epm.gestepm.model.materialrequired.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequiredTableDTO;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class MaterialRequiredRepositoryImpl implements MaterialRequiredRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<MaterialRequiredTableDTO> findMaterialsRequiredDataTablesByProjectId(Long projectId, PaginationCriteria pagination) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<MaterialRequiredTableDTO> cq = cb.createQuery(MaterialRequiredTableDTO.class);

			/* #BASE_QUERY */
			Root<MaterialRequired> root = cq.from(MaterialRequired.class);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("project"), projectId));
			
			cq.multiselect(root.get("id"), root.get("nameES"), root.get("nameFR"), root.get("required"));
			
			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, root);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, root);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<MaterialRequiredTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findMaterialsRequiredCountByProjectId(Long projectId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<MaterialRequired> root = cq.from(MaterialRequired.class);

			cq.select(cb.count(root));
			cq.where(cb.equal(root.get("project"), projectId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<MaterialRequiredDTO> findMaterialsRequiredByProjectId(Long projectId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<MaterialRequiredDTO> cq = cb.createQuery(MaterialRequiredDTO.class);

			Root<MaterialRequired> root = cq.from(MaterialRequired.class);

			cq.multiselect(root.get("id"), root.get("nameES"), root.get("nameFR"), root.get("required"))
					.where(cb.equal(root.get("project"), projectId));
			
			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
