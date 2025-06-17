package com.epm.gestepm.controller;

import com.epm.gestepm.forum.model.api.dto.ForumDTO;
import com.epm.gestepm.forum.model.api.service.UserForumService;
import com.epm.gestepm.model.family.service.mapper.FamilyMapper;
import com.epm.gestepm.model.materialrequired.service.mapper.MaterialRequiredMapper;
import com.epm.gestepm.model.projectold.service.mapper.ProjectMapper;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.customer.dto.Customer;
import com.epm.gestepm.modelapi.customer.dto.CustomerDTO;
import com.epm.gestepm.modelapi.customer.service.CustomerService;
import com.epm.gestepm.modelapi.deprecated.project.dto.*;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.dto.FamilyDTO;
import com.epm.gestepm.modelapi.family.dto.FamilyTableDTO;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequired;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequiredTableDTO;
import com.epm.gestepm.modelapi.materialrequired.service.MaterialRequiredService;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;
import com.epm.gestepm.modelapi.deprecated.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/projects")
public class ProjectController {

	private static final Log log = LogFactory.getLog(ProjectController.class);

	@Value("${gestepm.first-year}")
	private int firstYear;

	@Autowired
	private ActivityCenterService activityCenterServiceOld;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private MaterialRequiredService materialRequiredService;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private UserServiceOld userServiceOld;
	
	@Autowired
	private UserForumService userForumService;

	@GetMapping
	public String getMyProjects(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Proyectos");

			// Recover Team Leaders
			List<UserDTO> teamLeaders = userServiceOld.getUserDTOsByRank(Constants.ROLE_PL_ID);
			
			// Recover All Responsables
			List<UserDTO> responsables = userServiceOld.getAllProjectResponsables();
			
			// Recover Activity Centers
			List<ActivityCenter> activityCenters = activityCenterServiceOld.findAll();
			
			// Order by name
			activityCenters.sort(Comparator.comparing(ActivityCenter::getName));
			
			// PHPBB Forums
			List<ForumDTO> forumDTOs = userForumService.getAllForumsToDTO();
			
			// Recover Project Stations
			List<ProjectListDTO> projectListDTO = null;
			
			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				projectListDTO = projectService.getAllProjectsDTOs();
			} else {
				projectListDTO = projectService.getBossProjectsDTOByUserId(user.getId());
			}

			int actualYear = Calendar.getInstance().get(Calendar.YEAR);
			int[] yearsDropdown = new int[(actualYear - firstYear) + 1];

			for (int i = firstYear; i <= actualYear; i++) {
				yearsDropdown[actualYear - i] = i;
			}

			// Load Action Buttons for DataTable
			model.addAttribute("teamLeaders", teamLeaders);
			model.addAttribute("activityCenters", activityCenters);
			model.addAttribute("forumDTOs", forumDTOs);
			model.addAttribute("projectListDTO", projectListDTO);
			model.addAttribute("responsables", responsables);
			model.addAttribute("years", yearsDropdown);
			model.addAttribute("tableActionButtons", ModelUtil.getTableActionButtons());

			// Loading view
			return "projects";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/dt")
	public DataTableResults<ProjectTableDTO> projectsDataTable(@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long responsibleId, @RequestParam(required = false) Integer station, HttpServletRequest request) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ProjectTableDTO> projects = null;
			Long totalRecords = null;
			
			Object[] params = new Object[3];
			params[0] = projectId;
			params[1] = responsibleId;
			params[2] = station;

			if (user.getRole().getRoleName().equals(Constants.ROLE_ADMIN) || user.getRole().getRoleName().equals(Constants.ROLE_TECHNICAL_SUPERVISOR)) {
				projects = projectService.getAllProjectsDataTables(pagination, params);
				totalRecords = projectService.getAllProjectsCount(params);
			} else {
				projects = projectService.getProjectsByUserBossDataTables(user.getId(), pagination, params);
				totalRecords = projectService.getProjectsCountByUserBoss(user.getId(), params);
			}

			DataTableResults<ProjectTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(projects);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (projects != null && !projects.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(projects.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}
	
	@GetMapping("/view")
	public String getViewProjects(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Consulta de Proyectos");
			
			// Recover All Responsables
			List<UserDTO> responsables = userServiceOld.getAllProjectResponsables();

			// Recover Project Stations
			List<ProjectListDTO> projectListDTO = projectService.getAllProjectsDTOs();

			// Load Action Buttons for DataTable
			model.addAttribute("tableActionButtons", ModelUtil.getViewActionButton());
			model.addAttribute("projectListDTO", projectListDTO);
			model.addAttribute("responsables", responsables);
			
			// Loading view
			return "project-view";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@GetMapping("/view/dt")
	public DataTableResults<ProjectTableDTO> viewProjectsDatatable(@RequestParam(required = false) Long projectId, @RequestParam(required = false) Long responsableId, @RequestParam(required = false) Integer station, HttpServletRequest request, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ProjectTableDTO> projects = null;
			Long totalRecords = null;
			
			Object[] params = new Object[3];
			params[0] = projectId;
			params[1] = responsableId;
			params[2] = station;

			projects = projectService.getAllProjectsDataTables(pagination, params);
			totalRecords = projectService.getAllProjectsCount(params);

			DataTableResults<ProjectTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(projects);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (projects != null && !projects.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(projects.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<String> createProject(@ModelAttribute ProjectDTO projectDTO, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			// Get responsables
			List<User> responsables = new ArrayList<>();
			
			for (Long responsableId : projectDTO.getResponsables()) {
				
				User responsable = userServiceOld.getUserById(responsableId);
				
				if (responsable != null) {
					responsables.add(responsable);
				}
			}
			
			if (responsables.isEmpty()) {
				log.error("No se ha añadido ningun responsable al proyecto " + projectDTO.getId());
				return new ResponseEntity<>(messageSource.getMessage("project.create.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
			}
			
			ActivityCenter activityCenter = activityCenterServiceOld.getById(projectDTO.getActivityCenter());
			if (activityCenter == null) {
				log.error("Centro de actividad " + projectDTO.getActivityCenter() + " no encontrado");
				return new ResponseEntity<>(messageSource.getMessage("project.create.error", new Object[] {}, locale),
						HttpStatus.NOT_FOUND);
			}

			Project project = ProjectMapper.mapDTOToProject(projectDTO, responsables, activityCenter);
			if (project == null) {
				log.error("Proyecto " + projectDTO.getProjectName() + " no mappeado");
				return new ResponseEntity<>(messageSource.getMessage("project.create.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
			}

			project = projectService.save(project);

			// Creamos las relaciones ManyToMany
			for (User responsable : responsables) {
				projectService.createUserBoss(project.getId(), responsable.getId());
			}

			log.info("Proyecto " + projectDTO.getProjectName() + " creado con éxito por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.create.success", new Object[] {}, locale),
					HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.create.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/copy")
	public ResponseEntity<String> copyProject(@ModelAttribute ProjectCopyDTO projectCopyDTO, Locale locale) {
		
		try {
		
			log.info("Preparando la funcion para duplicar el proyecto " + projectCopyDTO.getProjectId());
			
			// Get selected project
			Project projectCopy = projectService.getProjectById(projectCopyDTO.getProjectId());
			
			if (projectCopy == null) {
				log.error("Proyecto no encontrado");
				return new ResponseEntity<>(messageSource.getMessage("project.copy.error", new Object[] {}, locale),
						HttpStatus.NOT_FOUND);
			}
			
			// Map selected project to new entity project
			Project project = ProjectMapper.copyProject(projectCopy);
			
			// Save project in DB
			project = projectService.save(project);
			
			// Copy customer
			if (project.getCustomer() != null) {
				
				Customer customer =  new Customer();
				customer.setProject(project);
				customer.setName(project.getCustomer().getName());
				customer.setMainEmail(project.getCustomer().getMainEmail());
				customer.setSecondaryEmail(project.getCustomer().getSecondaryEmail());
				
				customerService.save(customer);
			}
			
			// Copy members
			if (!projectCopy.getUsers().isEmpty()) {
				
				for (User member : projectCopy.getUsers()) {
					projectService.createMember(project.getId(), member.getId());
				}
			}
			
			// Copy teamLeaders
			if (!projectCopy.getBossUsers().isEmpty()) {
				
				for (User teamLeader : projectCopy.getBossUsers()) {

					if (teamLeader.getState() == 0) {
						projectService.createUserBoss(project.getId(), teamLeader.getId());
					}
				}
			}
			
			// Copy families
			if (!projectCopy.getFamilies().isEmpty()) {
				
				for (Family family : projectCopy.getFamilies()) {
					
					Family newFamily = new Family();
					newFamily.setFamily(family.getFamily());
					newFamily.setProject(project);
					newFamily.setNameES(family.getNameES());
					newFamily.setNameFR(family.getNameFR());
					newFamily.setBrand(family.getBrand());
					newFamily.setModel(family.getModel());
					newFamily.setEnrollment(family.getEnrollment());
					newFamily.setCommon(family.getCommon());
					
					familyService.save(newFamily);
				}
			}
			
			// log
			log.info("Se duplica el proyecto con nuevo id: " + project.getId());
						
			// Return data
			return new ResponseEntity<>(project.getId().toString(), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.copy.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
						
			projectService.delete(id);

			log.info("Proyecto " + id + " eliminado con éxito por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.delete.success", new Object[] {}, locale),
					HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.delete.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}")
	public String projectDetail(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		try {
			
			// Init project
			Project project = null;

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();
			Role role = user.getRole();
			
			// Log info
			log.info("El usuario " + user.getId() + " está accediendo al detalle del proyecto " + id);

			// Load project
			if (role.getId() == Constants.ROLE_ADMIN_ID || role.getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				project = projectService.getProjectById(id);
			} else {
				project = projectService.getProjectByIdAndBossId(id, user.getId());
			}

			if (project == null) {
				log.info("El usuario " + user.getId() + " no tiene autorización para ver el projecto " + id);
				return "redirect:/unauthorized";
			}

			// Load all Users
			List<UserDTO> userDTOs = userServiceOld.getAllUserDTOs();
			
			// Load no project members
			List<UserDTO> notMembers = userServiceOld.getNotUserDTOsByProjectId(id);
						
			// Load no team leaders
			List<UserDTO> notBosses = userServiceOld.getNotBossDTOsByProjectId(id);

			// Recover Team Leaders
			List<UserDTO> teamLeaders = userServiceOld.getUserDTOsByRank(Constants.ROLE_PL_ID);
			
			// PHPBB Forums
			List<ForumDTO> forumDTOs = userForumService.getAllForumsToDTO();
			
			// Recover Customer info
			Customer customer = customerService.getByProjectId(id);
			
			// Recover Activity Centers
			List<ActivityCenter> activityCenters = activityCenterServiceOld.findAll();
			
			// Order by name
			activityCenters.sort(Comparator.comparing(ActivityCenter::getName));
			
			// Recover Project Stations
			List<ProjectListDTO> stations = projectService.getStationDTOs();
			
			// Load no families selected
			List<FamilyDTO> notFamilies = familyService.getClonableFamilyDTOs(locale);
			
			// Get Responsables
			List<Long> responsablesIds = project.getResponsables().stream().map(User::getId).collect(Collectors.toList());

			// Add project to model
			model.addAttribute("projectId", id);
			model.addAttribute("userDTOs", userDTOs);
			model.addAttribute("project", project);
			model.addAttribute("notMembers", notMembers);
			model.addAttribute("notBosses", notBosses);
			model.addAttribute("teamLeaders", teamLeaders);
			model.addAttribute("forumDTOs", forumDTOs);
			model.addAttribute("customer", customer);
			model.addAttribute("stations", stations);
			model.addAttribute("activityCenters", activityCenters);
			model.addAttribute("notFamilies", notFamilies);
			model.addAttribute("responsablesIds", responsablesIds);
			
			// Load Action Buttons for DataTable
			model.addAttribute("tableActionButtons", ModelUtil.getTableActionButtonsOnlyTrash());
			model.addAttribute("tableModifyButtons", ModelUtil.getTableModifyActionButtons());
			model.addAttribute("tableExpenseActionButtons", ModelUtil.getTableExpenseActionButtons());
			model.addAttribute("tableEquipmentActionButtons", ModelUtil.getTableModifyActionButtons());			

			// Loading view
			return "project-detail";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@PostMapping("/{id}/update")
	public ResponseEntity<String> updateProject(@PathVariable Long id, @ModelAttribute ProjectDTO projectDto,
			Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			// Get responsables
			List<User> responsables = new ArrayList<>();
			
			for (Long responsableId : projectDto.getResponsables()) {
				
				User responsable = userServiceOld.getUserById(responsableId);
				
				if (responsable != null) {
					responsables.add(responsable);
				}
			}

			ActivityCenter activityCenter = activityCenterServiceOld.getById(projectDto.getActivityCenter());

			Project project = ProjectMapper.mapDTOToProject(projectDto, responsables, activityCenter);
			project.setId(id);

			projectService.save(project);

			log.info("Proyecto " + id + " actualizado con éxito por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.update.success", new Object[] {}, locale),
					HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.update.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/customer/update")
	public ResponseEntity<String> updateProjectCustomer(@PathVariable Long id, @ModelAttribute CustomerDTO customerDto, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			// Recover customer
			Customer customer = customerService.getByProjectId(id);
						
			if (StringUtils.isBlank(customerDto.getCustomerName())) {
				
				if (customer != null) {
					customerService.delete(customer.getId());
				}
				
			} else {
				
				if (customer == null) {
	
					// Recover project
					Project project = projectService.getProjectById(id);
					
					customer = new Customer();
					customer.setProject(project);
				}
				
				customer.setName(customerDto.getCustomerName());
				customer.setMainEmail(customerDto.getMainEmail());
				customer.setSecondaryEmail(customerDto.getSecondaryEmail());
				
				customerService.save(customer);
			}
			
			log.info("Cliente actualizado con éxito para el proyecto " + id + " por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.customer.update.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.customer.update.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping("/{id}/bosses/dt")
	public DataTableResults<ProjectMemberDTO> projectBossesDataTable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

		DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		List<ProjectMemberDTO> bosses = userServiceOld.getProjectBossDTOsByProjectId(id, pagination);

		Long totalRecords = userServiceOld.getProjectBossesCountByProjectId(id);

		DataTableResults<ProjectMemberDTO> dataTableResult = new DataTableResults<>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(bosses);
		dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (bosses != null && !bosses.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(bosses.size()));
		}

		return dataTableResult;
	}

	@ResponseBody
	@GetMapping("/{id}/families/dt")
	public DataTableResults<FamilyTableDTO> projectFamiliesDataTable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

		DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		List<FamilyTableDTO> families = familyService.getFamiliesDataTablesByProjectId(id, pagination);

		Long totalRecords = familyService.getFamiliesCountByProjectId(id);

		DataTableResults<FamilyTableDTO> dataTableResult = new DataTableResults<>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(families);
		dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (families != null && !families.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(families.size()));
		}

		return dataTableResult;
	}
	
	@ResponseBody
	@GetMapping("/{id}/materials-required/dt")
	public DataTableResults<MaterialRequiredTableDTO> projectMaterialsRequiredDataTable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

		DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		List<MaterialRequiredTableDTO> materialsRequired = materialRequiredService.getMaterialsRequiredDataTablesByProjectId(id, pagination);

		Long totalRecords = materialRequiredService.getMaterialsRequiredCountByProjectId(id);

		DataTableResults<MaterialRequiredTableDTO> dataTableResult = new DataTableResults<>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(materialsRequired);
		dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (materialsRequired != null && !materialsRequired.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(materialsRequired.size()));
		}

		return dataTableResult;
	}

	@ResponseBody
	@GetMapping("/{id}/members/dt")
	public DataTableResults<ProjectMemberDTO> projectMembersDataTable(@PathVariable Long id, HttpServletRequest request, Locale locale) {
		
		DataTableRequest<Project> dataTableInRQ = new DataTableRequest<>(request);
	    PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

	    List<ProjectMemberDTO> members = userServiceOld.getProjectMemberDTOsByProjectId(id, pagination);
	    
	    Long totalRecords = userServiceOld.getProjectMembersCountByProjectId(id);

	    DataTableResults<ProjectMemberDTO> dataTableResult = new DataTableResults<>();
	    dataTableResult.setDraw(dataTableInRQ.getDraw());
	    dataTableResult.setData(members);
	    dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
	    dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (members != null && !members.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(members.size()));
		}

	    return dataTableResult;
	}
	
	@ResponseBody
	@PostMapping("/{id}/members/create")
	public ResponseEntity<String> createProjectMember(@RequestParam Long[] memberId, @PathVariable Long id, Locale locale) {
		
		try {

			for (Long member : memberId) {
				
				projectService.createMember(id, member);
				
				log.info("Usuario " + member + " añadido al proyecto " + id);
			}
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.members.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("project.detail.members.member.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/members/delete/{memberId}")
	public ResponseEntity<String> deleteProjectMember(@PathVariable Long memberId, @PathVariable Long id, Locale locale) {
		
		try {

			projectService.deleteMember(id, memberId);
			
			log.info("Usuario " + memberId + " eliminado del proyecto " + id);	
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.members.delete", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("project.detail.members.member.derror", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/bosses/create")
	public ResponseEntity<String> createProjectBoss(@RequestParam Long[] bossId, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			for (Long boss : bossId) {
				
				try {
					projectService.createMember(id, boss);
				} catch (DataIntegrityViolationException e) {
					log.info("El usuario " + boss + " ya es miembro del proyecto " + id);
				}
				
				projectService.createUserBoss(id, boss);
	
				log.info("Jefe de proyecto " + boss + " añadido al proyecto " + id + " por parte del usuario " + user.getId());

			}
			
			// Return data
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.bosses.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.bosses.member.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@DeleteMapping("/{id}/bosses/delete/{bossId}")
	public ResponseEntity<String> deleteProjectBoss(@PathVariable Long bossId, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			projectService.deleteMember(id, bossId);
			projectService.deleteUserBoss(id, bossId);

			log.info("Jefe de proyecto " + bossId + " eliminado del proyecto " + id + " por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.bosses.delete", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.bosses.boss.derror", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/{id}/excel")
	public HttpEntity<ByteArrayResource> generateExcel(@PathVariable Long id, @RequestParam(required = false) Integer year, Locale locale) throws IOException {

		XSSFWorkbook workbook = null;

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " está generando el excel del proyecto " + id);

			if (year == null) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}

			// Recover project
			Project project = projectService.getProjectById(id);

			ByteArrayOutputStream archivo = new ByteArrayOutputStream();

			workbook = projectService.generateProjectExcel(id, user.getId(), project, year, locale);
			workbook.write(archivo);

			byte[] excelContent = archivo.toByteArray();

			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Project_" + id + ".xlsx");

			return new HttpEntity<>(new ByteArrayResource(excelContent), header);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return null;
	}

	@ResponseBody
	@PostMapping("/{id}/families/create")
	public ResponseEntity<String> createProjectFamilies(@ModelAttribute ProjectFamilyDTO projectFamilyDTO, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			Project project = projectService.getProjectById(id);
			if (project == null) {
				throw new Exception("Proyecto " + id + " no encontrado");
			}
			
			Family family = FamilyMapper.mapProjectFamilyDTOToFamily(projectFamilyDTO, familyService);
			family.setProject(project);
			family = familyService.save(family);

			log.info("Familia " + family.getId() + " añadida al proyecto " + id + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/families/edit/{familyId}")
	public ResponseEntity<String> editProjectFamilies(@ModelAttribute ProjectFamilyDTO projectFamilyDTO, @PathVariable Long id, @PathVariable Long familyId, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			Family family = familyService.getById(familyId);
			family.setNameES(projectFamilyDTO.getNameES());
			family.setNameFR(projectFamilyDTO.getNameFR());
			family.setFamily(familyService.getById(projectFamilyDTO.getFamilyId()));
			family.setBrand(projectFamilyDTO.getBrand());
			family.setModel(projectFamilyDTO.getModel());
			family.setEnrollment(projectFamilyDTO.getEnrollment());

			family = familyService.save(family);

			log.info("Familia " + family.getId() + " añadida al proyecto " + id + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/families/delete/{familyId}")
	public ResponseEntity<String> deleteProjectFamily(@PathVariable Long familyId, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			familyService.delete(familyId);

			log.info("Familia " + familyId + " eliminado del proyecto " + id + " por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.families.delete", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.families.derror", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/families/import")
	public ResponseEntity<String> importFamilies(@PathVariable Long id, @RequestParam("file") MultipartFile file, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			Project project = projectService.getProjectById(id);
			if (project == null) {
				throw new Exception("Proyecto " + id + " no encontrado");
			}
			
			// Action
			familyService.importFamilyFile(file, project, locale);

			// Log
			log.info("Fichero Familias importado en el proyecto " + id + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.import.success", new Object[] {}, locale), HttpStatus.OK);
						
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("project.detail.families.import.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}/families/export")
	public HttpEntity<ByteArrayResource> exportFamilies(@PathVariable Long id, Locale locale) throws IOException {

		XSSFWorkbook workbook = null;

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " está exportando las familias del proyecto " + id);

			ByteArrayOutputStream archivo = new ByteArrayOutputStream();

			workbook = familyService.generateFamiliesExcel(id, locale);
			workbook.write(archivo);

			byte[] excelContent = archivo.toByteArray();

			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Families_Project_" + id + ".xlsx");

			return new HttpEntity<>(new ByteArrayResource(excelContent), header);

		} catch (Exception e) {
			log.error(e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}

		return null;
	}
	
	@ResponseBody
	@PostMapping("/{id}/materials-required/create")
	public ResponseEntity<String> createMaterialRequired(@ModelAttribute MaterialRequiredDTO materialRequiredDTO, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			Project project = projectService.getProjectById(id);
			if (project == null) {
				throw new Exception("Proyecto " + id + " no encontrado en la creacion de un Material Obligatorio");
			}
			
			MaterialRequired materialRequired = MaterialRequiredMapper.mapNewDTOToEntity(materialRequiredDTO, project);
			materialRequired = materialRequiredService.save(materialRequired);

			log.info("Material Obligatorio " + materialRequired.getId() + " añadido al proyecto " + id + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.materials.required.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.detail.materials.required.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/{id}/materials-required/edit/{materialRequiredId}")
	public ResponseEntity<String> editMaterialRequired(@ModelAttribute MaterialRequiredDTO materialRequiredDTO, @PathVariable Long id, @PathVariable Long materialRequiredId, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();
			
			MaterialRequired materialRequired = materialRequiredService.getById(materialRequiredId);
			materialRequired.setNameES(materialRequiredDTO.getNameES());
			materialRequired.setNameFR(materialRequiredDTO.getNameFR());
			materialRequired.setRequired(materialRequiredDTO.getRequired());

			materialRequired = materialRequiredService.save(materialRequired);

			log.info("Material Obligatorio " + materialRequired.getId() + " añadido al proyecto " + id + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("project.detail.materials.required.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("project.detail.materials.required.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}/materials-required/delete/{materialRequiredId}")
	public ResponseEntity<String> deleteMaterialRequired(@PathVariable Long materialRequiredId, @PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			materialRequiredService.delete(materialRequiredId);

			log.info("Material Obligatorio " + materialRequiredId + " eliminado del proyecto " + id + " por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.materials.required.delete", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					messageSource.getMessage("project.detail.materials.required.derror", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@GetMapping("/{id}/materials-required")
	public List<MaterialRequiredDTO> getMaterialRequiredByProject(@PathVariable Long id) {
		
		return materialRequiredService.getMaterialsRequiredByProjectId(id);
	}

	@ResponseBody
	@GetMapping("/{id}/members")
	public List<UserDTO> getProjectMembers(@PathVariable Long id) {
		return userServiceOld.getUserDTOsByProjectId(id);
	}
	
	@ResponseBody
	@GetMapping("/{id}/responsables/html")
	public String getProjectResponsablesHtml(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		Project project = projectService.getProjectById(id);
		
		if (project == null) {
			return "";
		}
		
		List<User> responsables = project.getResponsables();

		StringBuilder strText = new StringBuilder();
		boolean isFirst = true;
		
		for (User responsable : responsables) {
			
			if (!isFirst) {
				strText.append("<br>");
			} else {
				isFirst = false;
			}
			
			strText.append("- " + responsable.getName() + " " + responsable.getSurnames() + " (" + responsable.getEmail() + ")");
		}
		
		return strText.toString();
	}
}
