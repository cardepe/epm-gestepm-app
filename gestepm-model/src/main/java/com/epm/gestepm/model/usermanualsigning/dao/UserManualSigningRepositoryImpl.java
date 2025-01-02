package com.epm.gestepm.model.usermanualsigning.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningTableDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class UserManualSigningRepositoryImpl implements UserManualSigningRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<UserManualSigningTableDTO> findUserManualSigningDTOsByUserId(Long userId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserManualSigningTableDTO> cq = cb.createQuery(UserManualSigningTableDTO.class);

			/* #BASE_QUERY */

			Root<UserManualSigning> root = cq.from(UserManualSigning.class);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(
					root.get("id"),
					root.get("manualSigningType").get("name"),
					root.get("startDate"),
					root.get("endDate"),
					cb.isNotNull(root.get("justification"))
			);

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, root);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, root);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(root.get("user"), userId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[0])));

			TypedQuery<UserManualSigningTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findUserManualSigningCountByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<UserManualSigning> root = cq.from(UserManualSigning.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<UserManualSigning> findWeekManualSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<UserManualSigning> cq = cb.createQuery(UserManualSigning.class);

		Root<UserManualSigning> root = cq.from(UserManualSigning.class);

		cq.select(root).where(cb.and(cb.between(
				root.get("endDate").as(LocalDateTime.class),
				startDate,
				endDate
		), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
	}
}
