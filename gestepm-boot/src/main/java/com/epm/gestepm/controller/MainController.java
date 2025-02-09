package com.epm.gestepm.controller;

import com.epm.gestepm.emailapi.dto.emailgroup.OpenNoProgrammedShareGroup;
import com.epm.gestepm.emailapi.service.EmailService;
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

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() {
		// TO DO
	}
}
