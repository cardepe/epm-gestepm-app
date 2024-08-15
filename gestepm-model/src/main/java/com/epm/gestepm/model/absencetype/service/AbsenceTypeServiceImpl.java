package com.epm.gestepm.model.absencetype.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.model.absencetype.dao.AbsenceTypeRepository;
import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import com.epm.gestepm.modelapi.absencetype.service.AbsenceTypeService;

@Service
@Transactional
public class AbsenceTypeServiceImpl implements AbsenceTypeService {

	@Autowired
	private AbsenceTypeRepository absenceTypeRepository;

	@Override
	public List<AbsenceType> findAll() {
		return (List<AbsenceType>) absenceTypeRepository.findAll();
	}
}
