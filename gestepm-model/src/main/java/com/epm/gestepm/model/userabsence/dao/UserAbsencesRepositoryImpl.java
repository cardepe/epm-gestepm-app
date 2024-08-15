package com.epm.gestepm.model.userabsence.dao;

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
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsenceDTO;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class UserAbsencesRepositoryImpl implements UserAbsencesRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<UserAbsence> findAbsencesByUserId(Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserAbsence> cq = cb.createQuery(UserAbsence.class);
			
			Root<UserAbsence> root = cq.from(UserAbsence.class);
			
			cq.select(root).where(cb.equal(root.get("user"), userId));
			
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<UserAbsenceDTO> findUserAbsencesDTOsByUserId(Long userId, PaginationCriteria pagination) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UserAbsenceDTO> cq = cb.createQuery(UserAbsenceDTO.class);

			/* #BASE_QUERY */

			Root<UserAbsence> uhRoot = cq.from(UserAbsence.class);
			Join<UserAbsence, AbsenceType> atRoot = uhRoot.join("absenceType", JoinType.INNER);

			List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(uhRoot.get("id"), atRoot.get("name"), uhRoot.get("date"));

			/* END #BASE_QUERY */

			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, uhRoot, atRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */

			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, uhRoot, atRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */

			Predicate predicateUser = cb.equal(uhRoot.get("user").get("id"), userId);
			predicates.add(predicateUser);

			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			TypedQuery<UserAbsenceDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public Long findUserAbsencesCountByUserId(Long userId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<UserAbsence> root = cq.from(UserAbsence.class);

			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
}
