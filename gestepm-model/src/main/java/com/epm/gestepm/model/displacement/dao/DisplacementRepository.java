package com.epm.gestepm.model.displacement.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.displacement.dto.Displacement;

public interface DisplacementRepository extends CrudRepository<Displacement, Long>, DisplacementRepositoryCustom {
	List<Displacement> findByActivityCenterId(long activityCenterId);
}
