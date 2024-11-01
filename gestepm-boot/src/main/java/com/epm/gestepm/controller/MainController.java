package com.epm.gestepm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String loginMessage() {
		return "redirect:/signing/personal"; 
	}
}
