package com.epm.gestepm.model.subfamily.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamilyOldDTO;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;

@Repository
public class SubFamilyRepositoryImpl implements SubFamilyRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public List<SubFamilyOldDTO> findByFamily(Long familyId) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<SubFamilyOldDTO> cQuery = cb.createQuery(SubFamilyOldDTO.class);

			Root<SubFamily> root = cQuery.from(SubFamily.class);

			cQuery.multiselect(root.get("id"), root.get("nameES"), root.get("nameFR"))
					.where(cb.equal(root.get("family"), familyId));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<RoleDTO> findSubRolsById(Long id) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<RoleDTO> cQuery = cb.createQuery(RoleDTO.class);

			Root<SubFamily> root = cQuery.from(SubFamily.class);
			Join<SubRole, SubFamily> subRoleRoot = root.join("subRoles", JoinType.INNER);

			cQuery.multiselect(subRoleRoot.get("id"), subRoleRoot.get("rol"))
					.where(cb.equal(root.get("id"), id));

			return entityManager.createQuery(cQuery).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
