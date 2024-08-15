package com.epm.gestepm.model.interventionsharefile.service;

import com.epm.gestepm.model.interventionsharefile.dao.InterventionShareFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;
import com.epm.gestepm.modelapi.interventionsharefile.service.InterventionShareFileService;

@Service
@Transactional
public class InterventionShareFileServiceImpl implements InterventionShareFileService {

	@Autowired
	private InterventionShareFileRepository interventionShareFileRepository;

	@Override
	public InterventionShareFile save(InterventionShareFile interventionShareFile) {
		return interventionShareFileRepository.save(interventionShareFile);
	}
	
	@Override
	public InterventionShareFile getInterventionShareFileById(Long interventionShareFileId) {
		return interventionShareFileRepository.findById(interventionShareFileId).orElse(null);
	}
	
	@Override
	public InterventionShareFile getInterventionShareFileByInterventionShareId(Long interventionShareId) {
		return interventionShareFileRepository.findByInterventionShareId(interventionShareId);
	}
}
