package com.epm.gestepm.model.interventionshare.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShareTableDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.user.dto.DailyPersonalSigningDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class InterventionShareRepositoryImpl implements InterventionShareRepositoryCustom {

	private static final Log log = LogFactory.getLog(InterventionShareRepositoryImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public Long findInterventionSharesCountByUserId(Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<InterventionShare> root = cq.from(InterventionShare.class);
			
			cq.select(cb.count(root)).where(cb.equal(root.get("user"), userId));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<InterventionShareTableDTO> findInterventionSharesByUserDataTables(Long userId, PaginationCriteria pagination) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InterventionShareTableDTO> cq = cb.createQuery(InterventionShareTableDTO.class);

			/* #BASE_QUERY */
			
			Root<InterventionShare> isRoot = cq.from(InterventionShare.class);
			Join<InterventionShare, Project> prRoot = isRoot.join("project", JoinType.INNER);
			
			List<Predicate> predicates = new ArrayList<>();
			
			cq.multiselect(isRoot.get("id").alias("id"), prRoot.get("name").alias("name"), isRoot.get("reference").alias("reference"));
			
			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, isRoot, prRoot);
				
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, isRoot, prRoot);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			Predicate predicateUser = cb.equal(isRoot.get("user"), userId);
			predicates.add(predicateUser);
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<InterventionShareTableDTO> criteriaQuery = entityManager.createQuery(cq);

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
	public List<ShareTableDTO> findShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

			Root<InterventionShare> root = cq.from(InterventionShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("noticeDate"), root.get("endDate"), cb.literal("is"), root.get("forumTitle"));

			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subQueryBoss = cq.subquery(Long.class);
			Root<Project> subRootBoss = subQueryBoss.from(Project.class);

			subQueryBoss.select(subRootBoss.get("id"));

			final List<Predicate> predicatesSqBoss = new ArrayList<>();
			predicatesSqBoss.add(cb.equal(subRootBoss.get("activityCenter"), activityCenterId));

			if (id != null) {
				predicatesSqBoss.add(cb.equal(root.get("id"), id));
			}

			if (progress != null) {

				if (progress == 1) {
					predicatesSqBoss.add(cb.isNull(root.get("endDate")));
				} else {
					predicatesSqBoss.add(cb.isNotNull(root.get("endDate")));
				}
			}

			if (projectId != null) {
				predicatesSqBoss.add(cb.equal(subRootBoss.get("id"), projectId));
			}

			subQueryBoss.where(cb.and(predicatesSqBoss.toArray(new Predicate[predicatesSqBoss.size()])));

			Predicate predicateBoss = cb.in(root.get("project")).value(subQueryBoss);
			predicates.add(predicateBoss);

			Subquery<Long> subQueryMember = cq.subquery(Long.class);
			Root<Project> subRootMember = subQueryMember.from(Project.class);
			// Join<Project, User> subMembers = subRootMember.join("users", JoinType.LEFT);
			subQueryMember.select(subRootMember.get("id"));

//			if (progress != null && progress == 1) {
//				subQueryMember.where(cb.and(cb.equal(subMembers.get("id"), userId), cb.isNull(root.get("endDate"))));
//			} else {
//				subQueryMember.where(cb.equal(subMembers.get("id"), userId));
//			}

			final List<Predicate> predicatesSqMember = new ArrayList<>();
			predicatesSqMember.add(cb.equal(subRootMember.get("activityCenter"), activityCenterId));

			if (id != null) {
				predicatesSqMember.add(cb.equal(root.get("id"), id));
			}

			if (progress != null) {

				if (progress == 1) {
					predicatesSqMember.add(cb.isNull(root.get("endDate")));
				} else {
					predicatesSqMember.add(cb.isNotNull(root.get("endDate")));
				}
			}

			if (projectId != null) {
				predicatesSqMember.add(cb.equal(subRootMember.get("id"), projectId));
			}

			subQueryMember.where(cb.and(predicatesSqMember.toArray(new Predicate[predicatesSqMember.size()])));

			Predicate predicateMember = cb.in(root.get("project")).value(subQueryMember);
			predicates.add(predicateMember);

			// Appending all Predicates
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));

			// Group by
			cq.groupBy(root.get("id"));

			return entityManager.createQuery(cq).getResultList();

		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<ShareTableDTO> findShareTableByUserId(Long userId, Long projectId, Integer progress) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);
			
			Root<InterventionShare> root = cq.from(InterventionShare.class);

			cq.multiselect(root.get("id"), root.get("project").get("name"), root.get("noticeDate"), root.get("endDate"), cb.literal("is"), root.get("forumTitle"));
			
			List<Predicate> predicates = new ArrayList<>();

			Subquery<Long> subQueryBoss = cq.subquery(Long.class);
			Root<Project> subRootBoss = subQueryBoss.from(Project.class);
			Join<Project, User> subBosses = subRootBoss.join("bossUsers", JoinType.LEFT);
			subQueryBoss.select(subRootBoss.get("id"));
			
			final List<Predicate> predicatesSqBoss = new ArrayList<>();
			predicatesSqBoss.add(cb.equal(subBosses.get("id"), userId));

			if (progress != null) {
				
				if (progress == 1) {
					predicatesSqBoss.add(cb.isNull(root.get("endDate")));
				} else {
					predicatesSqBoss.add(cb.isNotNull(root.get("endDate")));
				}
			}

			if (projectId != null) {
				predicatesSqBoss.add(cb.equal(subRootBoss.get("id"), projectId));
			}
			
			subQueryBoss.where(cb.and(predicatesSqBoss.toArray(new Predicate[predicatesSqBoss.size()])));	

			Predicate predicateBoss = cb.in(root.get("project")).value(subQueryBoss);
			predicates.add(predicateBoss);
			
			Subquery<Long> subQueryMember = cq.subquery(Long.class);
			Root<Project> subRootMember = subQueryMember.from(Project.class);
			Join<Project, User> subMembers = subRootMember.join("users", JoinType.LEFT);
			subQueryMember.select(subRootMember.get("id"));
			
//			if (progress != null && progress == 1) {
//				subQueryMember.where(cb.and(cb.equal(subMembers.get("id"), userId), cb.isNull(root.get("endDate"))));
//			} else {
//				subQueryMember.where(cb.equal(subMembers.get("id"), userId));
//			}
			
			final List<Predicate> predicatesSqMember = new ArrayList<>();
			predicatesSqMember.add(cb.equal(subMembers.get("id"), userId));

			if (progress != null) {
				
				if (progress == 1) {
					predicatesSqMember.add(cb.isNull(root.get("endDate")));
				} else {
					predicatesSqMember.add(cb.isNotNull(root.get("endDate")));
				}
			}
			
			if (projectId != null) {
				predicatesSqMember.add(cb.equal(subRootMember.get("id"), projectId));
			}
			
			subQueryMember.where(cb.and(predicatesSqMember.toArray(new Predicate[predicatesSqMember.size()])));
			
			Predicate predicateMember = cb.in(root.get("project")).value(subQueryMember);
			predicates.add(predicateMember);
			
			// Appending all Predicates
			cq.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
			
			// Group by
			cq.groupBy(root.get("id"));
						
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<InterventionShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionShare> cq = cb.createQuery(InterventionShare.class);

		Root<InterventionShare> root = cq.from(InterventionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public List<DailyPersonalSigningDTO> findDailyInterventionShareDTOByUserIdAndYear(Long userId, int year) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<DailyPersonalSigningDTO> cq = cb.createQuery(DailyPersonalSigningDTO.class);

			Root<InterventionShare> root = cq.from(InterventionShare.class);
			Join<InterventionShare, InterventionSubShare> isRoot = root.join("interventionSubShares", JoinType.LEFT);
			
			Date yearStartDate = Utiles.transformSimpleStringToDate("01-01-" + year);
			Date yearEndDate = Utiles.transformSimpleStringToDate("31-12-" + year);
			
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(cb.or(cb.equal(isRoot.get("firstTechnical"), userId), cb.equal(isRoot.get("secondTechnical"), userId)));
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
	
	@Override
	public Long findInterventionsCount(Long id) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			cq.select(cb.count(root)).where(cb.equal(root.get("interventionShare"), id));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public Long findAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId) {
		
		try {
			
			String filter = generateShareTableFilter(id, projectId, progress, userId);
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("SELECT COUNT(*) FROM (");
			
			strBuilder.append(generateSQLBody(type, filter));

			strBuilder.append(") N");
			
			Query query = entityManager.createNativeQuery(strBuilder.toString());
			
			return ((BigInteger) query.getSingleResult()).longValue();

		} catch (Exception e) {
			return 0L;
		}
	}
	
	@Override
	public List<ShareTableDTO> findAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId) {
		
		try {
			
			String filter = generateShareTableFilter(id, projectId, progress, userId);
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("SELECT * FROM (");
			
			strBuilder.append(generateSQLBody(type, filter));
			
			strBuilder.append(") N ORDER BY N." + ("is".equals(type) ? "NOTICE_DATE" : "START_DATE") + " DESC LIMIT " + pageSize + " OFFSET " + (pageNumber * pageSize));
			
			Query query = entityManager.createNativeQuery(strBuilder.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> results = (List<Object[]>) query.getResultList();
			
			List<ShareTableDTO> dtos = new ArrayList<>();
			
			for (Object[] obj : results) {
				dtos.add(ShareMapper.mapObjectToShareTableDTO(obj));
			}
			
			return dtos;
			
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
	private String generateShareTableFilter(Long id, Long projectId, Integer progress, Long userId) {
		
		String filter = (id != null || projectId != null || progress != null || userId != null) ? "WHERE " : "";

		if (id != null) {
			filter += (filter.length() > 6 ? "AND " : "") + "sh.ID = " + id + " ";
		}

		if (projectId != null) {
			filter += (filter.length() > 6 ? "AND " : "") + "PROJECT_ID = " + projectId + " ";
		} 
		
		if (progress != null) {
			filter += (filter.length() > 6 ? "AND " : "") + "END_DATE " + (progress == 1 ? "IS" : "IS NOT") + " NULL ";
		}

		if (userId != null) {
			filter += (filter.length() > 6 ? "AND " : "") + "USER_ID = " + userId + " ";
		}
		
		return filter;
	}
	
	private String generateSQLBody(String type, String filter) {
		
		StringBuilder strBuilder = new StringBuilder();
		
		if (StringUtils.isBlank(type) || "cs".equals(type)) {
			
			strBuilder.append("SELECT sh.ID, pr.NAME, sh.START_DATE, sh.END_DATE, '' as FORUM_TITLE, 'cs' as TYPE FROM construction_shares sh INNER JOIN projects pr ON sh.PROJECT_ID = pr.ID ");
			
			if (StringUtils.isNoneBlank(filter)) {
				strBuilder.append(filter);
			}
			
			if (StringUtils.isBlank(type)) {
				strBuilder.append("UNION ALL ");
			}
		}
		
		if (StringUtils.isBlank(type) || "ips".equals(type)) {
			
			strBuilder.append("SELECT sh.ID, pr.NAME, sh.START_DATE, sh.END_DATE, '' as FORUM_TITLE, 'ips' as TYPE FROM intervention_pr_shares sh INNER JOIN projects pr ON sh.PROJECT_ID = pr.ID ");
			
			if (StringUtils.isNoneBlank(filter)) {
				strBuilder.append(filter);
			}
			
			if (StringUtils.isBlank(type)) {
				strBuilder.append("UNION ALL ");
			}
		}
		
		if (StringUtils.isBlank(type) || "is".equals(type)) {
						
			strBuilder.append("SELECT sh.ID, pr.NAME, sh.NOTICE_DATE, sh.END_DATE, FORUM_TITLE, 'is' as TYPE FROM intervention_shares sh INNER JOIN projects pr ON sh.PROJECT_ID = pr.ID ");
			
			if (StringUtils.isNoneBlank(filter)) {
				strBuilder.append(filter);
			}
			
			if (StringUtils.isBlank(type)) {
				strBuilder.append("UNION ALL ");
			}
		}
		
		if (StringUtils.isBlank(type) || "ws".equals(type)) {
			
			strBuilder.append("SELECT sh.ID, pr.NAME, sh.START_DATE, sh.END_DATE, '' as FORUM_TITLE, 'ws' as TYPE FROM work_shares sh INNER JOIN projects pr ON sh.PROJECT_ID = pr.ID ");
			
			if (StringUtils.isNoneBlank(filter)) {
				strBuilder.append(filter);
			}
		}
		
		return strBuilder.toString();
	}
}
