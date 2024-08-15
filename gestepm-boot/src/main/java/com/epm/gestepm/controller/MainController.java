package com.epm.gestepm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.constructionshare.dao.ConstructionShareDao;
import com.epm.gestepm.model.constructionshare.dao.entity.ConstructionShare;
import com.epm.gestepm.model.constructionshare.dao.entity.filter.ConstructionShareFilter;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epm.gestepm.model.displacement.service.mapper.DisplacementMapper;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.scheduled.SigningScheduled;
import com.epm.gestepm.modelapi.displacement.service.DisplacementService;

@Controller
public class MainController {
	
	@Autowired
	private DisplacementService displacementService;
	
	@Autowired
	private SigningScheduled signingScheduled;

	@Autowired
	private ConstructionShareDao constructionShareDao;
	
	@GetMapping("/")
	public String loginMessage(Locale locale, Model model, HttpServletRequest request) {

		return "redirect:/signing/personal"; 
	}
	
	@GetMapping("/scheduled/signing")
	public void loadSigningScheduled(Locale locale, Model model, HttpServletRequest request) {
		signingScheduled.dailyPersonalSigningProcess();
	}
	
	@ResponseBody
	@GetMapping("/displacements/{id}")
	public DisplacementDTO getDisplacement(@PathVariable Long id) {

		Displacement displacement = displacementService.getDisplacementById(id);

		return DisplacementMapper.mapDisplacementToDTO(displacement);
	}

	@GetMapping("/test")
	public void testMethod() {

		final ConstructionShareFilter filter = ConstructionShareFilter.builder()
				.ids(List.of(80017))
				.build();

		final Page<ConstructionShare> list = this.constructionShareDao.list(filter, 0L, 10L);

		final List<ConstructionShare> content = list.getContent();

		int i = 0;
	}
}
