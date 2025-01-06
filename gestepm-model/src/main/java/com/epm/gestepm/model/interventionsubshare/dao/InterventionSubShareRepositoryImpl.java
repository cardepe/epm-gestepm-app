package com.epm.gestepm.model.interventionsubshare.dao;

import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.deprecated.interventionsubshare.dto.InterventionSubShare;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.List;

@Repository
public class InterventionSubShareRepositoryImpl implements InterventionSubShareRepositoryCustom {

    private static final Log log = LogFactory.getLog(InterventionSubShareRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShareTableDTO> findShareTableByProjectId(Long projectId) {

        try {

            final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            final CriteriaQuery<ShareTableDTO> cq = cb.createQuery(ShareTableDTO.class);

            Root<InterventionSubShare> root = cq.from(InterventionSubShare.class);
            Join<InterventionShare, InterventionSubShare> shareRoot = root.join("interventionShare", JoinType.LEFT);

            cq.multiselect(root.get("id"),
                            shareRoot.get("project").get("name"),
                            cb.concat(cb.concat(shareRoot.get("user").get("name"), " "), shareRoot.get("user").get("surnames")),
                            root.get("startDate"),
                            root.get("endDate"),
                            cb.literal("is"),
                            root.get("action"))
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

            cq.multiselect(root.get("id"), shareRoot.get("project").get("name"),
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
