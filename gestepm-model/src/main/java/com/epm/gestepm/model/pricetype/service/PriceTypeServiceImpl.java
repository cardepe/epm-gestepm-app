package com.epm.gestepm.model.pricetype.service;

import java.util.List;

import com.epm.gestepm.model.pricetype.dao.PriceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.pricetype.service.PriceTypeService;

@Service
@Transactional
public class PriceTypeServiceImpl implements PriceTypeService {

	@Autowired
	private PriceTypeRepository priceTypeRepository;

	@Override
	public List<PriceType> findAll() {
		return (List<PriceType>) priceTypeRepository.findAll();
	}
}
