package com.epm.gestepm.model.manualsigningtype.dao;

import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import org.springframework.data.repository.CrudRepository;

public interface ManualSigningTypeRepository extends CrudRepository<ManualSigningType, Long>, ManualSigningTypeRepositoryCustom {

}
