package com.epm.gestepm.forum.model.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.List;

import com.epm.gestepm.forum.model.dao.UserForumRepository;
import com.epm.gestepm.forum.model.api.dto.ForumDTO;
import com.epm.gestepm.forum.model.api.dto.UserForum;
import com.epm.gestepm.forum.model.api.service.UserForumService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

@Service
@Transactional("forumTransactionManager")
public class UserForumServiceImpl implements UserForumService {

	private static final Log log = LogFactory.getLog(UserForumServiceImpl.class);

	private static HttpTransport TRANSPORT;
	private static HttpRequestFactory REQ_FACTORY;

	@Autowired
	private UserForumRepository userForumRepository;

	@Override
	public List<UserForum> findAll() {
		return (List<UserForum>) userForumRepository.findAll();
	}

	@Override
	public UserForum save(UserForum entity) {
		return userForumRepository.save(entity);
	}
	
	@Override
	public List<ForumDTO> getAllForumsToDTO() {
		return userForumRepository.findAllForumsToDTO();
	}

	@Override
	public UserForum createUser(String username, String email, String password) {

		// Generate Argon2 Hash
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2i);
		String passwordForumHash = argon2.hash(4, 65536, 2, password.toCharArray());

		// Create UserForum object
		UserForum userForum = new UserForum();
		userForum.setType(0);
		userForum.setGroupId(2); // REGISTERED
		userForum.setPermissions("");
		userForum.setPermFrom(0);
		userForum.setIp(""); // ::1
		userForum.setRegDate(Instant.now().getEpochSecond());
		userForum.setUsername(username);
		userForum.setUsernameClean(username.toLowerCase());
		userForum.setPassword(passwordForumHash);
		userForum.setPassChg(Instant.now().getEpochSecond());
		userForum.setEmail(email);
		userForum.setBirthday(" 0- 0-   0");
		userForum.setLastVisit(0);
		userForum.setLastMark(Instant.now().getEpochSecond());
		userForum.setLastPostTime(0);
		userForum.setLastPage("");
		userForum.setLastConfirmKey("");
		userForum.setLastSearch(0);
		userForum.setWarnings(0);
		userForum.setLastWarning(0);
		userForum.setLoginAttempts(0);
		userForum.setInactiveReason(0);
		userForum.setInactiveTime(0);
		userForum.setPosts(0);
		userForum.setLang("es");
		userForum.setTimeZone("UTC");
		userForum.setDateFormat("D M d, Y g:i a");
		userForum.setStyle(1);
		userForum.setRank(0);
		userForum.setColour("");
		userForum.setNewPrivMsg(0);
		userForum.setUnreadPrivMsg(0);
		userForum.setLastPrivMsg(0);
		userForum.setMessageRules(0);
		userForum.setFullFolder(-3);
		userForum.setEmailTime(0);
		userForum.setTopicShowDays(0);
		userForum.setTopicSortByType("t");
		userForum.setTopicSortByDir("d");
		userForum.setPostShowDays(0);
		userForum.setPostSortByType("t");
		userForum.setPostSortByDir("a");
		userForum.setNotify(0);
		userForum.setNotifyPm(1);
		userForum.setNotifyType(0);
		userForum.setAllowPm(1);
		userForum.setAllowViewOnline(1);
		userForum.setAllowViewEmail(1);
		userForum.setAllowMassEmail(1);
		userForum.setOptions(230271);
		userForum.setAvatar("");
		userForum.setAvatarType("");
		userForum.setAvatarWidth(0);
		userForum.setAvatarHeight(0);
		userForum.setSig("");
		userForum.setSigBbcodeUid("");
		userForum.setSigBbcodeBitField("");
		userForum.setJabber("");
		userForum.setActKey("");
		userForum.setResetToken("");
		userForum.setResetTokenExpiration(0);
		userForum.setNewPasswd("");
		userForum.setFormSalt(RandomStringUtils.randomAlphabetic(16));		
		userForum.setUserNew(0);
		userForum.setReminded(0);
		userForum.setRemindedTime(0);
		
		// Insert UserForum into DB
		userForum = save(userForum);
		
		// Insert User Group into DB
		userForumRepository.createUserGroup(userForum.getId());
		
		// Update Forum Statistics
		userForumRepository.updateForumStatistics(userForum.getId(), username);

		return userForum;
	}
	
	@Override
	public UserForum updateUserPassword(String email, String password) {
		
		// Generate Argon2 Hash
		Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2i);
		String passwordForumHash = argon2.hash(4, 65536, 2, password.toCharArray());
		
		UserForum userForum = userForumRepository.findByEmail(email);
		userForum.setPassword(passwordForumHash);
		userForum.setPassChg(Instant.now().getEpochSecond());
		
		// Save UserForum into DB
		userForum = save(userForum);
			
		return userForum;
	}

	@Override
	@Deprecated
	public String getUserLoginForm() {

		try {

			// create Url Request (POST)
			GenericUrl urlRequest = new GenericUrl("http://localhost/ucp.php?mode=login");

			// log
			log.info("[GET] Login Form url call: " + urlRequest.toString());
			
			HttpRequest req = reqFactory().buildGetRequest(urlRequest);
		    HttpResponse resp = req.execute();
		    
		    InputStream result = resp.getContent();
		    String htmlCode = convert(result, Charset.defaultCharset());

		    Document docInfo = Jsoup.parse(htmlCode);
		    
		    Elements formHtml = docInfo.select("form#login");
		    
		    Element inputCreationTime = formHtml.select("input[name=creation_time]").first();
		    Element inputFormToken = formHtml.select("input[name=form_token]").first();
		    
		    String creationTime = inputCreationTime.val();
		    String formToken = inputFormToken.val();
		    
		    return "creation_time=" + creationTime + "&form_token=" + formToken; 
		    
		} catch (Exception e) {
			log.error(e);
		}
		
		return null;
	}
	
	private static HttpTransport transport() {
	    if (null == TRANSPORT) {
	        TRANSPORT = new NetHttpTransport();
	    }
	    return TRANSPORT;
	}
	
	private static HttpRequestFactory reqFactory() {
	    if (null == REQ_FACTORY) {
	        REQ_FACTORY = transport().createRequestFactory();
	    }
	    return REQ_FACTORY;
	}

	private static String convert(InputStream inputStream, Charset charset) throws IOException {

		StringBuilder stringBuilder = new StringBuilder();
		String line = null;

		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		}

		return stringBuilder.toString();
	}
}
