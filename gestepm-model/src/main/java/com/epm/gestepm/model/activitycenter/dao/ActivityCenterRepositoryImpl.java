package com.epm.gestepm.model.activitycenter.dao;

import com.epm.gestepm.modelapi.absencetype.dto.ActivityCenterTableDTO;
import com.epm.gestepm.modelapi.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ActivityCenterRepositoryImpl implements ActivityCenterRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<ActivityCenterTableDTO> findActivityCentersDataTables(PaginationCriteria pagination) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ActivityCenterTableDTO> cq = cb.createQuery(ActivityCenterTableDTO.class);

			/* #BASE_QUERY */
			
			Root<ActivityCenter> pvRoot = cq.from(ActivityCenter.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(pvRoot.get("activity_center_id"), pvRoot.get("country").get("name"), pvRoot.get("name"));

			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, pvRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, pvRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<ActivityCenterTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findActivityCentersCount() {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<ActivityCenter> root = cq.from(ActivityCenter.class);

			cq.select(cb.count(root));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
}
