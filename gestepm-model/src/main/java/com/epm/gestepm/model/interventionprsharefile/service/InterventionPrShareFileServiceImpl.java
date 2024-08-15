package com.epm.gestepm.model.interventionprsharefile.service;

import java.util.List;

import com.epm.gestepm.model.interventionprsharefile.dao.InterventionPrShareFileRepository;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShareFile;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareFileService;

@Service
@Transactional
public class InterventionPrShareFileServiceImpl implements InterventionPrShareFileService {
	
	@Autowired
	private InterventionPrShareFileRepository interventionPrShareFileRepository;

	@Override
	public InterventionPrShareFile save(InterventionPrShareFile interventionPrShareFile) {
		return interventionPrShareFileRepository.save(interventionPrShareFile);
	}
	
	@Override
	public InterventionPrShareFile getInterventionPrShareFileById(Long interventionPrShareFileId) {
		return interventionPrShareFileRepository.findById(interventionPrShareFileId).orElse(null);
	}
	
	@Override
	public InterventionPrShareFile getInterventionPrShareFileByInterventionShareId(Long interventionPrShareId) {
		return interventionPrShareFileRepository.findByInterventionPrShareId(interventionPrShareId);
	}
	
	@Override
	public void uploadFiles(InterventionPrShare interventionPrShare, List<MultipartFile> files) throws Exception {
		
		if (interventionPrShare != null) {
			
			for (MultipartFile file : files) {
				
				if (!StringUtils.isNullOrEmpty(file.getOriginalFilename())) {
					InterventionPrShareFile interventionPrShareFile = ShareMapper.mapMultipartFileToInterventionPrShareFile(file, interventionPrShare);
					interventionPrShareFileRepository.save(interventionPrShareFile);
				}
			}
		}
	}
}
