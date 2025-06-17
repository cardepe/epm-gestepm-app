package com.epm.gestepm.model.deprecated.holiday.dao;

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
import com.epm.gestepm.modelapi.holiday.dto.HolidayTableDTO;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class HolidayRepositoryImpl implements HolidayRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Holiday> findHolidaysByActivityCenter(Long activityCenterId) {
		
		try {

			final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			final CriteriaQuery<Holiday> cq = cb.createQuery(Holiday.class);

			final Root<Holiday> root = cq.from(Holiday.class);
			final Root<ActivityCenter> rootAc = cq.from(ActivityCenter.class);

			final List<Predicate> countryPredicate = new ArrayList<>();
			countryPredicate.add(cb.isNull(root.get("activityCenter")));
			countryPredicate.add(cb.equal(rootAc.get("country"), root.get("country")));

			final List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("activityCenter"), activityCenterId));
			predicates.add(cb.and(cb.isNull(root.get("activityCenter")), cb.equal(rootAc.get("country"), root.get("country"))));

			cq.select(root).where(cb.or(predicates.toArray(new Predicate[0])));

			final List<Order> orderByList = new ArrayList<>();
			orderByList.add(cb.asc(root.get("month")));
			orderByList.add(cb.asc(root.get("day")));
			
			cq.orderBy(orderByList);
			
			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<HolidayTableDTO> findHolidaysDataTables(PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<HolidayTableDTO> cq = cb.createQuery(HolidayTableDTO.class);

			/* #BASE_QUERY */

			Root<Holiday> hoRoot = cq.from(Holiday.class);
			Join<Holiday, Country> coRoot = hoRoot.join("country", JoinType.LEFT);
			Join<Holiday, ActivityCenter> pvRoot = hoRoot.join("activityCenter", JoinType.LEFT);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(hoRoot.get("id"), hoRoot.get("name"),
					cb.concat(cb.concat(hoRoot.get("day"), "/"), hoRoot.get("month")),
					pvRoot.get("name"), coRoot.get("name"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, hoRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, hoRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<HolidayTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findHolidaysCount() {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<Holiday> root = cq.from(Holiday.class);

			cq.select(cb.count(root));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
}
