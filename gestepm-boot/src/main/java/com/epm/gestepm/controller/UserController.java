package com.epm.gestepm.controller;

import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.forum.model.api.service.UserForumService;
import com.epm.gestepm.model.absencetype.service.mapper.AbsencesMapper;
import com.epm.gestepm.model.user.service.mapper.UserMapper;
import com.epm.gestepm.modelapi.absencetype.dto.AbsenceType;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsence;
import com.epm.gestepm.modelapi.userabsence.dto.UserAbsenceDTO;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.service.RoleService;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.userabsence.service.UserAbsencesService;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import com.epm.gestepm.model.activitycenter.service.ActivityCenterServiceImpl;
import com.epm.gestepm.model.country.service.CountryServiceOldImpl;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.model.common.utils.classes.SessionUtil;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Log log = LogFactory.getLog(UserController.class);
	
	@Value("${gestepm.first-year}")
	private int firstYear;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserHolidaysService userHolidayService;
	
	@Autowired
	private UserAbsencesService userAbsenceService;
	
	@Autowired
	private SubRoleService subRoleService;
	
	@Autowired
	private CountryServiceOldImpl countryService;
	
	@Autowired
	private ActivityCenterServiceImpl activityCenterServiceOld;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private SessionUtil sessionUtil;
	
	@Autowired
	private SingletonUtil singletonUtil;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserForumService userForumService;

	@Autowired
	private ManualSigningTypeService manualSigningTypeService;

	@GetMapping
	public String getUsers(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();

			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Usuarios");

			List<Role> roles = roleService.getAll();
			List<SubRole> subRoles = subRoleService.getAll();
			
			// Recover all users in a project
			List<UserDTO> usersDTO;

			if (user.getRole().getId() == Constants.ROLE_PL_ID) {

				usersDTO = new ArrayList<>();

				for (Project project : user.getBossProjects()) {
					List<UserDTO> userDTOsByProjectId = userService.getUserDTOsByProjectId(project.getId());
					usersDTO.addAll(userDTOsByProjectId);
				}

				usersDTO = usersDTO.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(UserDTO::getUserId))), ArrayList::new));

			} else {
				usersDTO = userService.getAllUserDTOs();
			}

			// Load all countries
			List<Country> countries = countryService.findAll();
			
			// Load all activity centers
			List<ActivityCenter> activityCenters = activityCenterServiceOld.findAll();

			// Add to model
			model.addAttribute("countries", countries);
			model.addAttribute("activityCenters", activityCenters);
			model.addAttribute("roles", roles);
			model.addAttribute("subRoles", subRoles);
			model.addAttribute("tableActionButtons", ModelUtil.getTableActionButtons());
			model.addAttribute("usersDTO", usersDTO);

			// Loading view
			return "users";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/dt")
	public DataTableResults<UserTableDTO> userBossUsersDatatable(@RequestParam(required = false) Long userId, @RequestParam(required = false) Integer state, HttpServletRequest request, Locale locale) {

		try {

			DataTableRequest<User> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			User user = Utiles.getUsuario();

			List<UserTableDTO> users = new ArrayList<>();
			Long totalRecords = 0L;

			if (userId == null) {

				List<Long> projectIds = null;

				if (user.getRole().getId() == Constants.ROLE_PL_ID) {
					projectIds = user.getBossProjects().stream().map(Project::getId).collect(Collectors.toList());
				}

				users = userService.getUsersDataTables(state, projectIds, pagination);
				totalRecords = userService.getUsersCount(state, projectIds);

			} else {

				UserTableDTO userTableDTO = userService.getUserDTOByUserId(userId, state);

				if (userTableDTO != null) {

					users.add(userTableDTO);
					totalRecords = 1L;
				}
			}

			DataTableResults<UserTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(users);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (!users.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(users.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}
	
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<String> createUser(@ModelAttribute UserDTO userDTO, Locale locale) {
		
		try {
			
			// Recover user
			User user = Utiles.getUsuario();

			ActivityCenter activityCenter = activityCenterServiceOld.getById(userDTO.getActivityCenterId());
			Role role = roleService.getRoleById(userDTO.getRoleId());
			SubRole subRole = subRoleService.getSubRoleById(userDTO.getSubRoleId());
			
			// Set State to Active
			userDTO.setState(0);
			
			User newUser = UserMapper.mapDTOToUser(userDTO, activityCenter, role, subRole);
			
			newUser = userService.save(newUser);

			log.info("Usuario " + newUser.getId() + " creado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.create.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("user.create.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public String userDetail(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		try {
			
			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
	
			// Recover user
			User me = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + me.getId() + " ha accedido a la vista de Detalles de Usuario " + id);
						
			// Load user
			User user = userService.getUserById(id);
			
			// Load absence types
			List<AbsenceType> absenceTypes = singletonUtil.getAbsenceTypes();
			
			// Load all countries
			List<Country> countries = countryService.findAll();
			
			// Order by name
			countries.sort(Comparator.comparing(Country::getName));
			
			// Load all activity centers
			List<ActivityCenter> activityCenters = activityCenterServiceOld.findAll();
			
			// Order by name
			activityCenters.sort(Comparator.comparing(ActivityCenter::getName));
			
			// Load roles and subroles
			List<Role> roles = roleService.getAll();
			List<SubRole> subRoles = subRoleService.getAll();
			
			// Order by name
			roles.sort(Comparator.comparing(Role::getRoleName));
			subRoles.sort(Comparator.comparing(SubRole::getRol));

			// Months
			final Map<Integer, String> months = ModelUtil.loadMonths(messageSource, locale);

			// Years
			int actualYear = Calendar.getInstance().get(Calendar.YEAR);
			int[] yearsDropdown = new int[(actualYear - firstYear) + 1];
			
			for (int i = firstYear; i <= actualYear; i++) {
				yearsDropdown[actualYear - i] = i;
			}
			
			// Get not projects
			List<ProjectDTO> notProjectDTOs = projectService.getNotProjectDTOsByUserId(id);

			// Get Projects
			List<ProjectListDTO> projects = null;

			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				projects = projectService.getAllProjectsDTOs();
			} else {
				projects = projectService.getProjectsByUser(user);
			}

			final List<ManualSigningType> manualSigningTypes = this.manualSigningTypeService.findAll();

			// Add roles and subroles to model
			model.addAttribute("roles", roles);
			model.addAttribute("subRoles", subRoles);
			
			// Add to model
			model.addAttribute("countries", countries);
			model.addAttribute("activityCenters", activityCenters);
			model.addAttribute("months", months);
			model.addAttribute("years", yearsDropdown);
			model.addAttribute("projects", projects);
			model.addAttribute("notProjects", notProjectDTOs);
			model.addAttribute("manualSigningTypes", manualSigningTypes);
			
			// Add user to model
			model.addAttribute("userDetail", user);
			
			// Load Action Buttons for DataTable
			model.addAttribute("absenceTypes", absenceTypes);
			model.addAttribute("tableActionButtons", ModelUtil.getTableActionButtonsOnlyTrash());
			model.addAttribute("tableValidateActionButtons", ModelUtil.getTableValidateActionButtons());
			model.addAttribute("tableValidateExpenseActionButtons", ModelUtil.getExpensesTableActionButtons());
			
			// Loading view
			return "user-detail";
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/update")
	public ResponseEntity<String> updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO, Locale locale) {

		try {
						
			// Recover user
			User me = Utiles.getUsuario();
						
			ActivityCenter activityCenter = activityCenterServiceOld.getById(userDTO.getActivityCenterId());
			Role role = roleService.getRoleById(userDTO.getRoleId());
			SubRole subRole = subRoleService.getSubRoleById(userDTO.getSubRoleId());
			User user = userService.getUserById(id);
			String actualPassword = user.getPassword();
			String actualForumPassword = user.getForumPassword();
			
			user = UserMapper.mapUpdateUserDTOToUser(user, userDTO, activityCenter, role, subRole);
			
			// If it's the same password, we don't encrypt twice
			String criptedPassword = userDTO.getPassword();
			if (criptedPassword.equals(actualPassword)) {
				user.setPassword(actualPassword);
				user.setForumPassword(actualForumPassword);
			} else {
				
				sessionUtil.setPassword(userDTO.getPassword());
				
				if (StringUtils.isNotEmpty(user.getUsername())) {
					userForumService.updateUserPassword(user.getEmail(), userDTO.getPassword());
				}
			}
			
			userService.save(user);
			
			log.info("Usuario " + id + " actualizado con éxito por parte del usuario " + me.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.update.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("user.update.error", new Object[] { Utiles.getExceptionDump(e) }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping("/{id}/holidays/dt")
	public DataTableResults<UserHolidayDTO> userHolidaysDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {
		
		try {
						
			DataTableRequest<UserHoliday> dataTableInRQ = new DataTableRequest<>(request);
		    PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		    
		    List<UserHolidayDTO> userHolidays = userHolidayService.getUserHolidaysDTOsByUserId(id, pagination);
		    
		    Long totalRecords = userHolidayService.getUserHolidaysCountByUser(id);

		    DataTableResults<UserHolidayDTO> dataTableResult = new DataTableResults<>();
		    dataTableResult.setDraw(dataTableInRQ.getDraw());
		    dataTableResult.setData(userHolidays);
		    dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		    dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (userHolidays != null && !userHolidays.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(userHolidays.size()));
			}

		    return dataTableResult;
		    
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@ResponseBody
	@GetMapping("/{id}/absences/dt")
	public DataTableResults<UserAbsenceDTO> userAbsencesDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {
		
		try {
						
			DataTableRequest<UserAbsence> dataTableInRQ = new DataTableRequest<>(request);
		    PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		    
		    List<UserAbsenceDTO> userAbsences = userAbsenceService.getUserAbsencesDTOsByUserId(id, pagination);
		    
		    Long totalRecords = userAbsenceService.getUserAbsencesCountByUser(id);

		    DataTableResults<UserAbsenceDTO> dataTableResult = new DataTableResults<>();
		    dataTableResult.setDraw(dataTableInRQ.getDraw());
		    dataTableResult.setData(userAbsences);
		    dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		    dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (userAbsences != null && !userAbsences.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(userAbsences.size()));
			}

		    return dataTableResult;
		    
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/absences/create")
	public ResponseEntity<String> createUserAbsence(@RequestParam Long absenceType, @RequestParam @DateTimeFormat(iso = ISO.DATE) Date date, @PathVariable Long id, Locale locale) {
		
		try {
			
			// Recover user
			User me = Utiles.getUsuario();
						
			// Get user
			User user = userService.getUserById(id);
			
			UserAbsence userAbsence = AbsencesMapper.mapFormToUserAbsence(user, absenceType, date, singletonUtil);
			
			// Save user absence
			userAbsenceService.save(userAbsence);
			
			log.info("Ausencia " + absenceType + " añadido al usuario " + id + " por parte del usuario " + me.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.detail.absences.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("user.detail.absences.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/absences/delete/{absenceId}")
	public ResponseEntity<String> deleteUserAbsence(@PathVariable Long absenceId, @PathVariable Long id, Locale locale) {
		
		try {

			// Recover user
			User me = Utiles.getUsuario();
						
			userAbsenceService.deleteById(absenceId);
			
			log.info("Ausencia " + absenceId + " eliminado del usuario " + id + " por parte del usuario " + me.getId());	
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.detail.absences.delete", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("user.detail.absences.derror", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping("/{id}/projects/dt")
	public DataTableResults<ProjectTableDTO> userProjectsDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {
		
		try {
						
			DataTableRequest<UserHoliday> dataTableInRQ = new DataTableRequest<>(request);
		    PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		    
		    List<ProjectTableDTO> userProjects = projectService.getProjectsByUserMemberDataTables(id, pagination, null);
		    
		    Long totalRecords = projectService.getProjectsCountByUserMember(id, null);

		    DataTableResults<ProjectTableDTO> dataTableResult = new DataTableResults<>();
		    dataTableResult.setDraw(dataTableInRQ.getDraw());
		    dataTableResult.setData(userProjects);
		    dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		    dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (userProjects != null && !userProjects.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(userProjects.size()));
			}

		    return dataTableResult;
		    
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId, Locale locale) {
		
		try {
			
			// Recover user
			User me = Utiles.getUsuario();
						
			userService.deleteUserById(userId);
			
			log.info("Usuario " + userId + " eliminado con éxito por parte del usuario " + me.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("users.delete.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("users.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/{id}/projects/create")
	public ResponseEntity<String> createProjectMember(@RequestParam Long[] projectId, @PathVariable Long id, Locale locale) {
		
		try {

			for (Long project : projectId) {
				
				projectService.createMember(project, id);
				
				log.info("Usuario " + id + " añadido al proyecto " + project);
			}
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.detail.projects.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("user.detail.projects.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/projects/delete/{projectId}")
	public ResponseEntity<String> deleteProjectMember(@PathVariable Long projectId, @PathVariable Long id, Locale locale) {
		
		try {

			projectService.deleteMember(projectId, id);
			
			log.info("Usuario " + id + " eliminado del proyecto " + projectId);	
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.projects.delete", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("project.detail.projects.derror", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
}
