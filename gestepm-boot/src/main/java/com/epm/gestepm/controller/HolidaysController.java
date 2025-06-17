package com.epm.gestepm.controller;

import com.epm.gestepm.modelapi.holiday.dto.YearCalendarDTO;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;
import com.epm.gestepm.modelapi.deprecated.user.exception.InvalidUserSessionException;
import com.epm.gestepm.model.holiday.service.mapper.HolidayMapper;
import com.epm.gestepm.modelapi.holiday.dto.Holiday;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.holiday.service.HolidayService;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectService;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/holidays")
public class HolidaysController {

	private static final Log log = LogFactory.getLog(HolidaysController.class);
	
	@Autowired
	private HolidayService holidayService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private SMTPService smtpService;

	@Autowired
	private UserServiceOld userServiceOld;

	@Autowired
	private UserHolidaysService userHolidayService;

	@Value("#{'${gestepm.mails.rrhh}'.split(',')}")
	private List<String> rrhhMails;

	@GetMapping
	public String holidays(Locale locale, Model model, HttpServletRequest request) {

		try {
			
			ModelUtil.loadConstants(locale, model, request);
			
			final User user = Utiles.getUsuario();
	
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Vacaciones");
						
			final Long activityCenterId = user.getActivityCenter().getId();
			final Long countryId = user.getActivityCenter().getCountry().getId();

			final List<Holiday> holidays = holidayService.listByActivityCenterAndCountry(activityCenterId, countryId);
			final String holidaysJson =  HolidayMapper.mapAndSerializeHolidaysToJson(holidays);

			model.addAttribute("holidays", holidays);
			model.addAttribute("holidaysJson", holidaysJson);
	
			return "holidays";
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping(value = "/calendar")
	public ResponseEntity<List<YearCalendarDTO>> getHolidaysCalendar(@RequestParam(required = false) Integer year) {

		try {

			year = year == null ? Year.now().getValue() : year;

			final User user = Utiles.getUsuario();

			final List<UserHoliday> userHolidays = userHolidayService.getHolidaysByUser(user.getId(), year);

			final List<YearCalendarDTO> yearCalendarDTOs = HolidayMapper.mapUserHolidaysToYearCalendarDTOs(userHolidays);

			return new ResponseEntity<>(yearCalendarDTOs, OK);

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping(value = "/create")
	public ResponseEntity<String> createUserHolidays(@RequestBody List<Date> holidays, HttpServletRequest request) {

		try {

			final User user = Utiles.getUsuario();
			final Locale locale =  request.getLocale();

			this.userHolidayService.createHolidays(holidays, user);

			log.info("Se han solicitado un total de " + holidays.size() + " vacaciones por parte del usuario " + user.getId());

			if (!rrhhMails.isEmpty()) {

				String userHtmlHolidays = generateHTMLHolidays(holidays);

				for (String mail : rrhhMails) {
					smtpService.sendCreateHolidaysRRHHMail(mail, user, userHtmlHolidays, locale);
				}
			}

			return new ResponseEntity<>("OK", OK);

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(e.getMessage(), UNAUTHORIZED);
		}
	}

	@ResponseBody
	@PostMapping(value = "/delete")
	public ResponseEntity<String> deleteUserHolidays(@RequestBody List<Date> holidays, HttpServletRequest request) {

		try {

			final User user = Utiles.getUsuario();
			final Locale locale =  request.getLocale();

			this.userHolidayService.deleteHolidays(holidays, user);

			log.info("Se han eliminado un total de " + holidays.size() + " vacaciones por parte del usuario " + user.getId());

			if (!rrhhMails.isEmpty()) {

				String userHtmlHolidays = generateHTMLHolidays(holidays);

				for (String mail : rrhhMails) {
					smtpService.sendDeleteHolidaysRRHHMail(mail, user, userHtmlHolidays, locale);
				}
			}

			return new ResponseEntity<>(null, OK);

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
		}
	}

	@ResponseBody
	@PostMapping("/validate/{id}")
	public ResponseEntity<String> validateHoliday(@PathVariable("id") String id, Locale locale) {
		
		try {
			
			final User user = Utiles.getUsuario();
			
			final UserHoliday userHoliday = userHolidayService.getUserHolidayById(Long.parseLong(id));
			
			final Role role = user.getRole();
						
			if (role.getId() >= Constants.ROLE_RRHH_ID && Constants.STATUS_PENDING.equals(userHoliday.getStatus())) {
				userHoliday.setStatus(Constants.STATUS_APPROVED);
			} else {
				return new ResponseEntity<>(messageSource.getMessage("holiday.validate.unauthorized", new Object[] { }, locale), UNAUTHORIZED);
			}
			
			userHolidayService.save(userHoliday);
			
			log.info("Vacación " + id + " validada por parte del usuario " + user.getId());

			return new ResponseEntity<>(messageSource.getMessage("holiday.validate.success", new Object[] { }, locale), OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("holiday.validate.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/decline/{id}")
	public ResponseEntity<String> declineExpense(@PathVariable("id") String id, @RequestParam(required = false) String observations, Locale locale) {
		
		try {
			
			final User user = Utiles.getUsuario();
			
			final UserHoliday userHoliday = userHolidayService.getUserHolidayById(Long.parseLong(id));
			
			final Role role = user.getRole();
						
			if (role.getId() >= Constants.ROLE_RRHH_ID && Constants.STATUS_PENDING.equals(userHoliday.getStatus())) {
				userHoliday.setStatus(Constants.STATUS_REJECTED);
			} else {
				return new ResponseEntity<>(messageSource.getMessage("holiday.decline.unauthorized", new Object[] { }, locale), UNAUTHORIZED);
			}
			
			if (StringUtils.isNoneBlank(observations)) {
				userHoliday.setObservations(observations);
			}
			
			userHolidayService.save(userHoliday);

			if (userHoliday.getUser() != null && StringUtils.isNoneBlank(userHoliday.getUser().getEmail())) {
				smtpService.sendHolidayDeclineMail(userHoliday.getUser().getEmail(), user, userHoliday, locale);
			}
						
			log.info("Vacación " + id + " declinada por parte del usuario " + user.getId());
						
			return new ResponseEntity<>(messageSource.getMessage("holiday.decline.success", new Object[] { }, locale), OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("holiday.decline.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable("id") String id, Locale locale) {
		
		try {
			
			final User user = Utiles.getUsuario();
			
			userHolidayService.deleteById(Long.parseLong(id));
			
			log.info("Vacación " + id + " eliminada por parte del usuario " + user.getId());

			return new ResponseEntity<>(messageSource.getMessage("holidays.admin.delete.success", new Object[] { }, locale), OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("holidays.admin.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/admin")
	public String holidaysAdmin(Locale locale, Model model, HttpServletRequest request) {

		try {

			ModelUtil.loadConstants(locale, model, request);

			final User user = Utiles.getUsuario();

			log.info("El usuario " + user.getId() + " ha accedido a la vista de Administración de Vacaciones");

			final List<ProjectListDTO> projects;

			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				projects = projectService.getAllProjectsDTOs();
			} else {
				projects = projectService.getProjectsByUser(user);
			}

			List<UserDTO> usersDTO;

			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_RRHH_ID) {
				usersDTO = userServiceOld.getAllUserDTOs();
			} else {

				usersDTO = new ArrayList<>();

				for (ProjectListDTO projectListDTO : projects) {
					List<UserDTO> userDTOsByProjectId = userServiceOld.getUserDTOsByProjectId(projectListDTO.getId());
					usersDTO.addAll(userDTOsByProjectId);
				}

				usersDTO = usersDTO.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(UserDTO::getUserId))), ArrayList::new));
			}

			model.addAttribute("projects", projects);
			model.addAttribute("usersDTO", usersDTO);

			return "holidays-admin";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping(value = "/admin/calendar")
	public ResponseEntity<List<YearCalendarDTO>> getHolidaysAdminCalendar(@RequestParam(required = false) Integer year, @RequestParam(required = false) Long userId, @RequestParam(required = false) Long projectId) {

		year = year == null ? Year.now().getValue() : year;

		final List<Long> userIds = new ArrayList<>();

		if (userId != null) {

			userIds.add(userId);

		} else if (projectId != null) {

			final Project project = this.projectService.getProjectById(projectId);

			final List<User> projectUsers = project.getUsers();

			final List<Long> projectUsersIds = projectUsers.stream().map(User::getId).collect(Collectors.toList());

			userIds.addAll(projectUsersIds);
		}

		if (!userIds.isEmpty()) {

			final List<YearCalendarDTO> yearCalendarDTOs = userHolidayService.getHolidaysByUserList(userIds, year);

			return new ResponseEntity<>(yearCalendarDTOs, OK);

		} else {
			return new ResponseEntity<>(OK);
		}
	}
	
	private String generateHTMLHolidays(List<Date> holidays) {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<ul>");
		
		for (Date date : holidays) {
			strBuilder.append("<li>");
			strBuilder.append(Utiles.getDateFormatted(date));
			strBuilder.append("</li>");
		}
		
		strBuilder.append("</ul>");
		
		return strBuilder.toString();
	}
}
