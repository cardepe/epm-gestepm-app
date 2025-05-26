package com.epm.gestepm.model.subrole.dao;

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
import com.epm.gestepm.modelapi.role.dto.RoleTableDTO;
import com.epm.gestepm.modelapi.userold.dto.User;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class SubRoleRepositoryImpl implements SubRoleRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<RoleTableDTO> findRolesDataTables(PaginationCriteria pagination) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<RoleTableDTO> cq = cb.createQuery(RoleTableDTO.class);

			/* #BASE_QUERY */
			
			Root<SubRole> srRoot = cq.from(SubRole.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(srRoot.get("id"), srRoot.get("rol"), srRoot.get("price"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, srRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, srRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<RoleTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findRolesCount() {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<SubRole> root = cq.from(SubRole.class);

			cq.select(cb.count(root));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public SubRole findByUserId(long userId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<SubRole> cq = cb.createQuery(SubRole.class);

			Root<SubRole> root = cq.from(SubRole.class);
			Join<SubRole, User> usRoot = root.join("users", JoinType.INNER);

			cq.select(root);
			cq.where(cb.equal(usRoot.get("id"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return null;
		}
	}
}
