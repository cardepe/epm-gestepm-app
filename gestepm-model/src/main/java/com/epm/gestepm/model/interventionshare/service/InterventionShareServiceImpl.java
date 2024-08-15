package com.epm.gestepm.model.interventionshare.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.epm.gestepm.model.family.dao.FamilyRepository;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.model.interventionsharefile.dao.InterventionShareFileRepository;
import com.epm.gestepm.model.interventionshare.dao.InterventionShareRepository;
import com.epm.gestepm.model.subfamily.dao.SubFamilyRepository;
import com.epm.gestepm.forum.model.api.dto.Topic;
import com.epm.gestepm.forum.model.api.service.TopicService;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionNoPrDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShareTableDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.interventionsharefile.dto.InterventionShareFile;
import com.epm.gestepm.modelapi.subfamily.dto.SubFamily;
import com.epm.gestepm.modelapi.interventionshare.service.InterventionShareService;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Service
@Transactional
public class InterventionShareServiceImpl implements InterventionShareService {
	
	private static final Log log = LogFactory.getLog(InterventionShareServiceImpl.class);

	@Autowired
	private FamilyRepository familyRepository;
	
	@Autowired
	private InterventionShareRepository interventionShareDao;
	
	@Autowired
	private InterventionShareFileRepository interventionShareFileDao;

	@Autowired
	private SubFamilyRepository subFamilyRepository;
	
	@Autowired
	private TopicService topicService;
	
	@Override
	public InterventionShare getInterventionShareById(Long id) {
		return interventionShareDao.findById(id).orElse(null);
	}
	
	@Override
	public InterventionShare save(InterventionShare interventionShare) {	
		return interventionShareDao.save(interventionShare);
	}
	
	@Override
	public InterventionShare update(InterventionShare share, InterventionNoPrDTO interventionNoPrDTO, User user, String ip, Locale locale) throws Exception {

		Family family = familyRepository.findById(interventionNoPrDTO.getFamily()).orElse(null);
		
		if (family == null) {
			throw new Exception("La familia " + interventionNoPrDTO.getFamily() + " no existe");
		}
		
		share.setFamily(family);
		
		SubFamily subFamily = subFamilyRepository.findById(interventionNoPrDTO.getSubFamily()).orElse(null);
		
		if (subFamily == null) {
			throw new Exception("La sub familia " + interventionNoPrDTO.getSubFamily() + " no existe");
		}
		
		share.setSubFamily(subFamily);
		
		share.setDescription(interventionNoPrDTO.getDescription());
		
		if (interventionNoPrDTO.getFiles() != null && CollectionUtils.isNotEmpty(interventionNoPrDTO.getFiles())) {
			
			for (MultipartFile file : interventionNoPrDTO.getFiles()) {
				
				if (file.isEmpty()) {
					continue;
				}

				try {
					
					InterventionShareFile interventionShareFile = ShareMapper.mapMultipartFileToInterventionShareFile(file, share);
					interventionShareFileDao.save(interventionShareFile);
					
				} catch (IOException e) {
					log.error(e);
				}
			}
		}
		
		Long forumId = share.getProject().getForumId();
		
		if (forumId != null && share.getTopicId() == null) {
			
			String familyStr = ("es".equals(locale.getLanguage()) ? share.getFamily().getNameES() : share.getFamily().getNameFR()) ;
			
			if (share.getFamily().getBrand() != null) {
				familyStr += " " + share.getFamily().getBrand();
			}
			
			if (share.getFamily().getModel() != null) {
				familyStr += " " + share.getFamily().getModel();
			}
			
			if (share.getFamily().getEnrollment() != null) {
				familyStr += " " + share.getFamily().getEnrollment();
			}
			
			String forumTitle = share.getId() + " " + Utiles.getDateFormattedForForum(share.getNoticeDate()) + " " + familyStr + " " + ("es".equals(locale.getLanguage()) ? share.getSubFamily().getNameES() : share.getSubFamily().getNameFR());
			
			// Create Forum Topic
			Topic topic = topicService.create(forumTitle, share.getDescription(), forumId, ip, user.getUsername(), interventionNoPrDTO.getFiles());
		
			if (topic == null) {
				
				// Log info
				log.info("No se ha podido crear la entrada en el foro " + forumId);
			} else {
				
				// Set topic to IShare
				share.setTopicId(topic.getId());
				share.setForumTitle(forumTitle);
				
				// Log info
				log.info("Entrada " + topic.getId() + " creada en el foro " + forumId);
			}
		}
		
		// Save share
		save(share);
		
		return share;
	}
	
	@Override
	public void deleteById(Long shareId) {
		interventionShareDao.deleteById(shareId);
	}

	@Override
	public Long getInterventionSharesCountByUser(Long userId) {
		return interventionShareDao.findInterventionSharesCountByUserId(userId);
	}
	
	@Override
	public List<InterventionShareTableDTO> getInterventionSharesByUserDataTables(Long userId, PaginationCriteria pagination) {
		return interventionShareDao.findInterventionSharesByUserDataTables(userId, pagination);
	}

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return interventionShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
	}

	@Override
	public List<ShareTableDTO> getShareTableByUserId(Long userId, Long projectId, Integer progress) {
		return interventionShareDao.findShareTableByUserId(userId, projectId, progress);
	}
	
	@Override
	public Long getInterventionsCount(Long id) {
		return interventionShareDao.findInterventionsCount(id);
	}
	
	@Override
	public Long getAllShareTableCount(Long id, String type, Long projectId, Integer progress, Long userId) {
		return interventionShareDao.findAllShareTableCount(id, type, projectId, progress, userId);
	}
	
	@Override
	public List<ShareTableDTO> getAllShareTable(Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress, Long userId) {
		return interventionShareDao.findAllShareTable(pageNumber, pageSize, id, type, projectId, progress, userId);
	}
}
