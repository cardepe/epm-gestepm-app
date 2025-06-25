package com.epm.gestepm.model.deprecated.project.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.common.utils.datatables.util.DataTableUtil;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ProjectListDTO> findAllProjectsDTOs() {

        try {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<ProjectListDTO> cQuery = cb.createQuery(ProjectListDTO.class);

            Root<Project> root = cQuery.from(Project.class);

            cQuery.multiselect(root.get("id"), root.get("name"), root.get("station"));

            cQuery.orderBy(cb.asc(root.get("name")));

            return entityManager.createQuery(cQuery).getResultList();

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

}
