package com.epm.gestepm.model.deprecated.project.dao;

import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

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
