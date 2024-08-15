package com.epm.gestepm.controller;

import java.time.Instant;
import java.util.Base64;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.forum.model.api.dto.UserForumDTO;
import com.epm.gestepm.forum.model.api.service.UserForumService;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epm.gestepm.modelapi.common.utils.CipherUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.model.common.utils.classes.SessionUtil;

@Controller
@RequestMapping("/forum")
public class ForumController {

	private static final Log log = LogFactory.getLog(ForumController.class);
	
	@Value("${forum.url}")
	private String forumUrl;

	@Autowired
	private SessionUtil sessionUtil;
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserForumService userForumService;

	@GetMapping("/login")
	public String loginForum(Locale locale, Model model, HttpServletRequest request) {

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
	
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<String> createUserForum(@ModelAttribute UserForumDTO userForumDTO, Locale locale, Model model, HttpServletRequest request) {
		
		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Set username to User
			User forumUser = userService.getUserById(userForumDTO.getUserId());
			
			if (StringUtils.isNotEmpty(forumUser.getUsername())) {
				log.error("Usuario " + forumUser.getId() + " ya registrado en el foro: " + forumUser.getUsername());
				return new ResponseEntity<>(messageSource.getMessage("user.create.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
			}
			
			forumUser.setUsername(userForumDTO.getUsername());
			
			// Decrypt user password
			String plainPassword = new String(CipherUtil.decryptMessage(Base64.getDecoder().decode(forumUser.getForumPassword().getBytes()), Constants.ENCRYPTION_KEY.getBytes()));
			
			// Create user in forum DB
			userForumService.createUser(userForumDTO.getUsername(), forumUser.getEmail(), plainPassword);
			
			// Update username in epm DB
			userService.save(forumUser);
			
			// Print log
			log.info("Usuario del Foro " + forumUser.getId() + " creado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.create.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("user.create.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
}
