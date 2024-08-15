package com.epm.gestepm.model.interventionsubshare.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShareTableDTO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class InterventionSubShareRepositoryImpl implements InterventionSubShareRepositoryCustom {
	
	private static final Log log = LogFactory.getLog(InterventionSubShareRepositoryImpl.class);
	
	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public InterventionSubShare findByShareAndOrder(Long shareId, Long interventionId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InterventionSubShare> cq = cb.createQuery(InterventionSubShare.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			cq.select(root).where(cb.and(cb.equal(root.get("interventionShare"), shareId), cb.equal(root.get("orderId"), interventionId)));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@Override
	public Long findInterventionSubSharesCountByShareId(Long shareId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Long> cq = cb.createQuery(Long.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			cq.select(cb.count(root)).where(cb.and(cb.equal(root.get("interventionShare"), shareId), cb.isNotNull(root.get("endDate"))));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			log.error(e);
			return 0L;
		}
	}
	
	@Override
	public List<InterventionSubShareTableDTO> findInterventionSubSharesByShareTables(Long shareId, PaginationCriteria pagination) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InterventionSubShareTableDTO> cq = cb.createQuery(InterventionSubShareTableDTO.class);

			/* #BASE_QUERY */
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			List<Predicate> predicates = new ArrayList<>();
						
			cq.multiselect(root.get("id"), root.get("orderId"), root.get("action"), root.get("startDate"), root.get("endDate"), root.get("materialsFileExt"), root.get("topicId"));
			
			/* END #BASE_QUERY */
			
			/* #WHERE_CLAUSE */
			Predicate whereFilter = DataTableUtil.generateWhereCondition(pagination, cb, root);
			
			if (whereFilter != null) {
				predicates.add(whereFilter);
			}
			/* END #WHERE_CLAUSE */
			
			/* #ORDER_CLAUSE */
			List<Order> orderList = DataTableUtil.generateOrderByCondition(pagination, cb, root);
			
			if(!orderList.isEmpty()) {
				cq.orderBy(orderList);
			}
			/* END #ORDER_CLAUSE */
			
			Predicate predicateUser = cb.and(cb.equal(root.get("interventionShare"), shareId), cb.isNotNull(root.get("endDate")));
			predicates.add(predicateUser);
			
			// Appending all Predicates
			cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
		
			TypedQuery<InterventionSubShareTableDTO> criteriaQuery = entityManager.createQuery(cq);

			/* #PAGE_NUMBER */
			criteriaQuery.setFirstResult(pagination.getPageNumber());
			/* END #PAGE_NUMBER */
			
			/* #PAGE_SIZE */
			criteriaQuery.setMaxResults(pagination.getPageSize());
			/* END #PAGE_SIZE */
			
			return criteriaQuery.getResultList();
			
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public InterventionSubShare findOpenIntervention(Long shareId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InterventionSubShare> cq = cb.createQuery(InterventionSubShare.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			cq.select(root).where(cb.and(cb.equal(root.get("interventionShare"), shareId), cb.isNull(root.get("endDate"))));
			
			return entityManager.createQuery(cq).getSingleResult();
			
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@Override
	public List<InterventionSubShare> findWeekSigningsByUserId(Date startDate, Date endDate, Long userId) {
		
		try {
			
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<InterventionSubShare> cq = cb.createQuery(InterventionSubShare.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			
			cq.select(root)
				.where(
						cb.and(cb.and(
								cb.between(root.get("endDate"), startDate, endDate), 
								cb.or(cb.equal(root.get("firstTechnical"), userId), cb.equal(root.get("secondTechnical"), userId))
							), cb.notEqual(root.get("action"), 3)));
			
			return entityManager.createQuery(cq).getResultList();
		
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}

	@Override
	public List<InterventionSubShare> findWeekSigningsByProjectId(Date startDate, Date endDate, Long projectId) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<InterventionSubShare> cq = cb.createQuery(InterventionSubShare.class);

		Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);

		cq.select(root).where(cb.and(cb.between(root.get("endDate"), startDate, endDate), cb.equal(root.get("interventionShare").get("project"), projectId)));

		return entityManager.createQuery(cq).getResultList();
	}
	
	@Override
	public List<ShareTableDTO> findShareTableByProjectId(Long projectId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			Join<InterventionShare, InterventionSubShare> shareRoot = root.join("interventionShare", JoinType.INNER);
			
			cq.multiselect(root.get("id"), cb.concat(cb.concat(shareRoot.get("id"), "/"), root.get("orderId")), shareRoot.get("project").get("name"),
					cb.concat(cb.concat(shareRoot.get("user").get("name"), " "), shareRoot.get("user").get("surnames")),
					root.get("startDate"), root.get("endDate"), cb.literal("is"), root.get("action"))
					.where(cb.equal(shareRoot.get("project"), projectId));
			
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public List<ShareTableDTO> findShareTableByUserSigningId(Long userSigningId) {

		try {

			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);
			
			Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
			Join<InterventionShare, InterventionSubShare> shareRoot = root.join("interventionShare", JoinType.INNER);
			
			cq.multiselect(root.get("id"), cb.concat(cb.concat(shareRoot.get("id"), "/"), root.get("orderId")), shareRoot.get("project").get("name"),
					cb.concat(cb.concat(shareRoot.get("user").get("name"), " "), shareRoot.get("user").get("surnames")),
					root.get("startDate"), root.get("endDate"), cb.literal("is"), root.get("action"))
					.where(cb.equal(shareRoot.get("userSigning"), userSigningId));
			
			return entityManager.createQuery(cq).getResultList();
			
		} catch (Exception e) {
			log.error(e);
			return Collections.emptyList();
		}
	}
}
