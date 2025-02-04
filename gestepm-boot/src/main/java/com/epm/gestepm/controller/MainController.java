package com.epm.gestepm.controller;

import com.epm.gestepm.task.PersonalSigningTask;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Data
@Controller
public class MainController {

	private final PersonalSigningTask personalSigningTask;

    @GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}

	@GetMapping("/v1/test")
	public void toTest() throws IOException {
		this.personalSigningTask.dailyPersonalSigningProcess();
	}
}
