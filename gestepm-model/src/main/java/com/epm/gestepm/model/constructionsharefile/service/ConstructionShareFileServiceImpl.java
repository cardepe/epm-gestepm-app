package com.epm.gestepm.model.constructionsharefile.service;

import java.util.List;

import com.epm.gestepm.model.constructionsharefile.dao.ConstructionShareFileRepository;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionsharefile.dto.ConstructionShareFile;
import com.epm.gestepm.modelapi.constructionsharefile.service.ConstructionShareFileService;

@Service
@Transactional
public class ConstructionShareFileServiceImpl implements ConstructionShareFileService {
	
	@Autowired
	private ConstructionShareFileRepository constructionShareFileRepository;

	@Override
	public ConstructionShareFile save(ConstructionShareFile constructionShareFile) {
		return constructionShareFileRepository.save(constructionShareFile);
	}
	
	@Override
	public ConstructionShareFile getConstructionShareFileById(Long constructionShareFileId) {
		return constructionShareFileRepository.findById(constructionShareFileId).orElse(null);
	}
	
	@Override
	public ConstructionShareFile getConstructionShareFileByInterventionShareId(Long constructionShareId) {
		return constructionShareFileRepository.findByConstructionShareId(constructionShareId);
	}
	
	@Override
	public void uploadFiles(ConstructionShare constructionShare, List<MultipartFile> files) throws Exception {
		
		if (constructionShare != null) {
			
			for (MultipartFile file : files) {
				
				if (!StringUtils.isNullOrEmpty(file.getOriginalFilename())) {
					ConstructionShareFile constructionShareFile = ShareMapper.mapMultipartFileToConstructionShareFile(file, constructionShare);
					constructionShareFileRepository.save(constructionShareFile);
				}
			}
		}
	}
}
