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

	@Autowired
	private InterventionShareRepository interventionShareDao;
	
	@Override
	public InterventionShare getInterventionShareById(Long id) {
		return interventionShareDao.findById(id).orElse(null);
	}
	
	@Override
	public InterventionShare save(InterventionShare interventionShare) {	
		return interventionShareDao.save(interventionShare);
	}

	@Override
	public List<ShareTableDTO> getShareTableByActivityCenterId(Long id, Long activityCenterId, Long projectId, Integer progress) {
		return interventionShareDao.findShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
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
