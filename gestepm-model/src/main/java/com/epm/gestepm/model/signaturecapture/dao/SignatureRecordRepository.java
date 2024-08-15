package com.epm.gestepm.model.signaturecapture.dao;

import org.springframework.data.repository.CrudRepository;

import com.epm.gestepm.modelapi.signaturecapture.dto.SignatureRecord;

public interface SignatureRecordRepository extends CrudRepository<SignatureRecord, Long>, SignatureRecordRepositoryCustom {
	
}