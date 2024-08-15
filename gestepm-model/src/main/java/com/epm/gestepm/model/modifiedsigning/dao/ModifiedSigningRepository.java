package com.epm.gestepm.model.modifiedsigning.dao;

import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import org.springframework.data.repository.CrudRepository;

public interface ModifiedSigningRepository extends CrudRepository<ModifiedSigning, Long>, ModifiedSigningRepositoryCustom {

}
