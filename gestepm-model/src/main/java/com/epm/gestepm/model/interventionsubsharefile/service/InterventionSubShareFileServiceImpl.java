package com.epm.gestepm.model.interventionsubsharefile.service;

import java.util.List;

import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.model.interventionsubsharefile.dao.InterventionSubShareFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubsharefile.dto.InterventionSubShareFile;
import com.epm.gestepm.modelapi.interventionsubsharefile.service.InterventionSubShareFileService;

@Service
@Transactional
public class InterventionSubShareFileServiceImpl implements InterventionSubShareFileService {
	
	@Autowired
	private InterventionSubShareFileRepository interventionSubShareFileRepository;

	@Override
	public InterventionSubShareFile save(InterventionSubShareFile interventionSubShareFile) {
		return interventionSubShareFileRepository.save(interventionSubShareFile);
	}
	
	@Override
	public InterventionSubShareFile getInterventionSubShareFileById(Long interventionSubShareFileId) {
		return interventionSubShareFileRepository.findById(interventionSubShareFileId).orElse(null);
	}
	
	@Override
	public InterventionSubShareFile getInterventionSubShareFileByInterventionShareId(Long interventionSubShareId) {
		return interventionSubShareFileRepository.findByInterventionSubShareId(interventionSubShareId);
	}
	
	@Override
	public void uploadFiles(InterventionSubShare interventionSubShare, List<MultipartFile> files) {
		
		if (interventionSubShare != null) {
			
			for (MultipartFile file : files) {
				
				if (!StringUtils.isNullOrEmpty(file.getOriginalFilename())) {
					InterventionSubShareFile interventionSubShareFile = ShareMapper.mapMultipartFileToInterventionSubShareFile(file, interventionSubShare);
					interventionSubShareFileRepository.save(interventionSubShareFile);
				}
			}
		}
	}
}
