package com.epm.gestepm.model.interventionshare.dao;

import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class InterventionShareRepositoryImpl implements InterventionShareRepositoryCustom {

	private static final Log log = LogFactory.getLog(InterventionShareRepositoryImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;

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
			subQueryMember.select(subRootMember.get("id"));

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
	public List<InterventionShare> findWeekSigningsByUserId(LocalDateTime startDate, LocalDateTime endDate, Long userId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionShare> cq = cb.createQuery(InterventionShare.class);

		Root<InterventionShare> root = cq.from(InterventionShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("user"), userId)));

		return entityManager.createQuery(cq).getResultList();
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
			
			strBuilder.append(") N ORDER BY N.START_DATE DESC LIMIT " + pageSize + " OFFSET " + (pageNumber * pageSize));
			
			Query query = entityManager.createNativeQuery(strBuilder.toString());

			@SuppressWarnings("unchecked")
			List<Object[]> results = (List<Object[]>) query.getResultList();
			
			List<ShareTableDTO> dtos = new ArrayList<>();
			
			for (Object[] obj : results) {
				dtos.add(ShareMapper.mapObjectToShareTableDTO(obj));
			}
			
			return dtos;
			
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}
	
	private String generateShareTableFilter(Long id, Long projectId, Integer progress, Long userId) {
		
		String filter = (id != null || projectId != null || progress != null || userId != null) ? "WHERE " : "";

		if (id != null) {
			filter += "sh.ID = " + id + " ";
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
						
			strBuilder.append("SELECT sh.no_programmed_share_id, pr.NAME, sh.start_date, sh.end_date, forum_title, 'is' as TYPE FROM no_programmed_share sh INNER JOIN projects pr ON sh.project_id = pr.ID ");
			
			if (StringUtils.isNoneBlank(filter)) {
				strBuilder.append(filter.replace("sh.ID", "sh.no_programmed_share_id"));
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
