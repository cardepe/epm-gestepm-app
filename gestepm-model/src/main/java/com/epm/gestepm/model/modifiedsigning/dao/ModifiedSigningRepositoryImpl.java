package com.epm.gestepm.model.modifiedsigning.dao;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;
import com.epm.gestepm.modelapi.user.dto.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ModifiedSigningRepositoryImpl implements ModifiedSigningRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public List<ModifiedSigningTableDTO> findModifiedSigningsDataTables(List<Long> projectIds, Long userId, PaginationCriteria pagination) {

		try {

			final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			final CriteriaQuery<ModifiedSigningTableDTO> cq = cb.createQuery(ModifiedSigningTableDTO.class);

			final Root<ModifiedSigning> root = cq.from(ModifiedSigning.class);
			final Join<ModifiedSigning, User> uRoot = root.join("user", JoinType.INNER);

			final List<Predicate> predicates = new ArrayList<>();

			cq.multiselect(root.get("id"), root.get("signingId"), root.get("typeId"), uRoot.get("name"), uRoot.get("surnames"), root.get("startDate"),
					root.get("endDate"));

			if (userId != null) {
				predicates.add(cb.equal(uRoot.get("id"), userId));
			}

			if (projectIds != null) {

				Join<User, Project> prRoot = uRoot.join("projects", JoinType.INNER);

				predicates.add(prRoot.get("id").in(projectIds));
			}

			final Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, root, uRoot);

			if (whereFilter != null) {
				predicates.add(whereFilter);
			}

			final List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, root, uRoot);

			if (!orderList.isEmpty()) {
				cq.orderBy(orderList);
			} else {
				cq.orderBy(cb.desc(root.get("requestDate")));
			}

			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			final TypedQuery<ModifiedSigningTableDTO> criteriaQuery = entityManager.createQuery(cq);
			criteriaQuery.setFirstResult(pagination.getPageNumber());
			criteriaQuery.setMaxResults(pagination.getPageSize());

			return criteriaQuery.getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public Long findModifiedSigningsCount(List<Long> projectIds, Long userId) {

		try {

			final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			final CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			final Root<ModifiedSigning> root = cq.from(ModifiedSigning.class);
			final Join<ModifiedSigning, User> uRoot = root.join("user", JoinType.INNER);

			cq.select(cb.count(root));

			final List<Predicate> predicates = new ArrayList<>();

			if (userId != null) {
				predicates.add(cb.equal(uRoot.get("id"), userId));
			}

			if (projectIds != null) {

				Join<User, Project> prRoot = uRoot.join("projects", JoinType.INNER);

				predicates.add(prRoot.get("id").in(projectIds));
			}

			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
}
