package com.epm.gestepm.model.personalsigning.dao;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	public List<PersonalSigning> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PersonalSigning> cq = cb.createQuery(PersonalSigning.class);
		
		Root<PersonalSigning> root = cq.from(PersonalSigning.class);
		
		cq.select(root).where(cb.and(cb.between(root.get("endDate").as(OffsetDateTime.class),
				startDate.toInstant().atOffset(ZoneOffset.UTC),
				endDate.toInstant().atOffset(ZoneOffset.UTC)
		), cb.equal(root.get("user"), userId)));
		
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public Long findYearSecondsPersonalSigningByUserIdAndYear(Long userId, int year) {
		
		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);

			Root<PersonalSigning> root = cq.from(PersonalSigning.class);
			
			Date yearStartDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date yearEndDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));
			predicates.add(cb.between(root.get("startDate"), yearStartDate, yearEndDate));
			
			Expression<java.sql.Time> timeDiff = cb.function("TIMEDIFF", java.sql.Time.class, root.get("endDate"), root.get("startDate"));
			Expression<Integer> timeToSec = cb.function("TIME_TO_SEC", Integer.class, timeDiff);
			
			cq.multiselect(cb.sum(timeToSec));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
			
			return entityManager.createQuery(cq).getSingleResult();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<DailyPersonalSigningDTO> findDailyPersonalSigningDTOByUserIdAndYear(Long userId, int year) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DailyPersonalSigningDTO> cq = cb.createQuery(DailyPersonalSigningDTO.class);

			Root<PersonalSigning> root = cq.from(PersonalSigning.class);
			
			Date yearStartDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date yearEndDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.equal(root.get("user"), userId));
			predicates.add(cb.between(root.get("startDate"), yearStartDate, yearEndDate));
			
			Expression<java.sql.Time> timeDiff = cb.function("TIMEDIFF", java.sql.Time.class, root.get("endDate"), root.get("startDate"));
			Expression<Integer> timeToSec = cb.function("TIME_TO_SEC", Integer.class, timeDiff);
			
			cq.multiselect(cb.sum(timeToSec), root.get("startDate"));
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

			Expression<Integer> gbYear = cb.function("YEAR", Integer.class, root.get("startDate"));
			Expression<Integer> gbMonth = cb.function("MONTH", Integer.class, root.get("startDate"));
			Expression<Integer> gbDay = cb.function("DAY", Integer.class, root.get("startDate"));
			cq.groupBy(gbYear, gbMonth, gbDay);
			
			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
}
