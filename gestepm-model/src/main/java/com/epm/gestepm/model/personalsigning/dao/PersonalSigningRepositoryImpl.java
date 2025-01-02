package com.epm.gestepm.model.personalsigning.dao;

import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PersonalSigningRepositoryImpl implements PersonalSigningRepositoryCustom {

	@PersistenceContext	
	private EntityManager entityManager;

	@Override
	public PersonalSigning findLastSigningByUserId(Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<PersonalSigning> cq = cb.createQuery(PersonalSigning.class);
			
			Root<PersonalSigning> root = cq.from(PersonalSigning.class);
			
			cq.select(root).where(cb.and(cb.isNull(root.get("endDate")), cb.equal(root.get("user"), userId)));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<PersonalSigning> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PersonalSigning> cq = cb.createQuery(PersonalSigning.class);
		
		Root<PersonalSigning> root = cq.from(PersonalSigning.class);
		
		cq.select(root).where(cb.and(cb.between(root.get("endDate").as(LocalDateTime.class),
				startDate,
				endDate
		), cb.equal(root.get("user"), userId)));
		
		return entityManager.createQuery(cq).getResultList();
	}
}
