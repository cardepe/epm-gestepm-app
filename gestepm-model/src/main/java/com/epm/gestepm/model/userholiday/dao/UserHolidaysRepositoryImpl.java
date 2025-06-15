package com.epm.gestepm.model.userholiday.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserHolidaysRepositoryImpl implements UserHolidaysRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<UserHoliday> findHolidaysByUserId(Long userId, Integer year) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserHoliday> cq = cb.createQuery(UserHoliday.class);
			
			Root<UserHoliday> root = cq.from(UserHoliday.class);

			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));

			if (year != null) {
				predicates.add(cb.equal(cb.function("YEAR", Integer.class, root.get("date")), year));
			}

			cq.select(root);
			cq.where(cb.and(predicates.toArray(new Predicate[0])));

			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<UserHolidayDTO> findUserHolidaysDTOsByUserId(Long userId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserHolidayDTO> cq = cb.createQuery(UserHolidayDTO.class);

			/* #BASE_QUERY */

			Root<UserHoliday> uhRoot = cq.from(UserHoliday.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(uhRoot.get("id"), uhRoot.get("date"), uhRoot.get("status"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, uhRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, uhRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(uhRoot.get("user").get("id"), userId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<UserHolidayDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findUserHolidaysCountByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<UserHoliday> root = cq.from(UserHoliday.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
}
