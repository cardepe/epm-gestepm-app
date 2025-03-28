package com.epm.gestepm.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	private static final Log log = LogFactory.getLog(LoginController.class);

	@Value("${app.version}")
	private String appVersion;

	@GetMapping("/login")
	public String loginMessage(@RequestParam(value = "error_type", required = false) Integer errorType, Model model) {

		log.info("Carga de la vista de autenticación (/login)");

		model.addAttribute("appVersion", appVersion);

		if (errorType != null) {
			model.addAttribute("errorType", errorType);
		}
		
		return "login";
	}
}
