package com.epm.gestepm.model.worksharefile.service;

import java.util.List;

import com.epm.gestepm.model.worksharefile.dao.WorkShareFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import com.epm.gestepm.modelapi.worksharefile.service.WorkShareFileService;

@Service
@Transactional
public class WorkShareFileServiceImpl implements WorkShareFileService {

	@Autowired
	private WorkShareFileRepository workShareFileRepository;

	@Override
	public WorkShareFile save(WorkShareFile workShareFile) {
		return workShareFileRepository.save(workShareFile);
	}
	
	@Override
	public WorkShareFile getWorkShareFileById(Long workShareFileId) {
		return workShareFileRepository.findById(workShareFileId).orElse(null);
	}
	
	@Override
	public List<WorkShareFile> getWorkShareFileByWorkShareId(Long workShareId) {
		return workShareFileRepository.findByWorkShareId(workShareId);
	}
}
