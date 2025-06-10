package com.epm.gestepm.controller;

import java.time.Instant;

import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.exception.InvalidUserSessionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.model.common.utils.classes.SessionUtil;

@Controller
@RequestMapping("/forum")
public class ForumController {

	private static final Log log = LogFactory.getLog(ForumController.class);
	
	@Value("${gestepm.forum.url}")
	private String forumUrl;

	@Autowired
	private SessionUtil sessionUtil;

	@GetMapping("/login")
	public String loginForum(Model model) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			if (user.getUsername() == null) {
				return "redirect:/unauthorized";
			}

			String username = user.getUsername();
			String password = sessionUtil.getPassword();
			
			long creationTime = Instant.now().getEpochSecond();

			model.addAttribute("forumUrl", forumUrl);
			model.addAttribute("username", username);
			model.addAttribute("password", password);
			model.addAttribute("creationTime", creationTime);
			model.addAttribute("formToken", "");

			return "script-login-forum";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
}
