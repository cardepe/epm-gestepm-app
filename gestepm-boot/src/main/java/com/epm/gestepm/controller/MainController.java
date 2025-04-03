package com.epm.gestepm.controller;

import com.epm.gestepm.emailapi.service.EmailService;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDto;
import com.epm.gestepm.modelapi.timecontrol.dto.filter.TimeControlFilterDto;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.task.PersonalSigningTask;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Controller
public class MainController {

	private final PersonalSigningTask personalSigningTask;

	private final EmailService emailService;

	private final TimeControlService timeControlService;

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() {
		final TimeControlFilterDto filterDto = new TimeControlFilterDto();
		filterDto.setUserId(1);
		filterDto.setStartDate(LocalDateTime.now().minusDays(6));
		filterDto.setEndDate(LocalDateTime.now().plusDays(1));

		final List<TimeControlDto> list = this.timeControlService.list(filterDto);

		int i = 0;
		// TO DO
	}
}
