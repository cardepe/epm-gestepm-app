package com.epm.gestepm.controller;

import com.epm.gestepm.model.family.service.mapper.FamilyMapper;
import com.epm.gestepm.model.deprecated.project.service.mapper.ProjectMapper;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.deprecated.customer.dto.Customer;
import com.epm.gestepm.modelapi.deprecated.customer.service.CustomerService;
import com.epm.gestepm.modelapi.deprecated.project.dto.*;
import com.epm.gestepm.modelapi.family.dto.Family;
import com.epm.gestepm.modelapi.family.dto.FamilyTableDTO;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectOldService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.dto.UserDTO;
import com.epm.gestepm.modelapi.deprecated.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/projects")
public class ProjectOldController {

	private static final Log log = LogFactory.getLog(ProjectOldController.class);
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private ProjectOldService projectOldService;

	@Autowired
	private UserServiceOld userServiceOld;

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
			List<ProjectListDTO> projectListDTO = projectOldService.getAllProjectsDTOs();

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

			projects = projectOldService.getAllProjectsDataTables(pagination, params);
			totalRecords = projectOldService.getAllProjectsCount(params);

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
	@PostMapping("/copy")
	public ResponseEntity<String> copyProject(@ModelAttribute ProjectCopyDTO projectCopyDTO, Locale locale) {
		
		try {
		
			log.info("Preparando la funcion para duplicar el proyecto " + projectCopyDTO.getProjectId());
			
			// Get selected project
			Project projectCopy = projectOldService.getProjectById(projectCopyDTO.getProjectId());
			
			if (projectCopy == null) {
				log.error("Proyecto no encontrado");
				return new ResponseEntity<>(messageSource.getMessage("project.copy.error", new Object[] {}, locale),
						HttpStatus.NOT_FOUND);
			}
			
			// Map selected project to new entity project
			Project project = ProjectMapper.copyProject(projectCopy);
			
			// Save project in DB
			project = projectOldService.save(project);
			
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
					projectOldService.createMember(project.getId(), member.getId());
				}
			}
			
			// Copy teamLeaders
			if (!projectCopy.getBossUsers().isEmpty()) {
				
				for (User teamLeader : projectCopy.getBossUsers()) {

					if (teamLeader.getState() == 0) {
						projectOldService.createUserBoss(project.getId(), teamLeader.getId());
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
			Project project = projectOldService.getProjectById(id);

			ByteArrayOutputStream archivo = new ByteArrayOutputStream();

			workbook = projectOldService.generateProjectExcel(id, user.getId(), project, year, locale);
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
			
			Project project = projectOldService.getProjectById(id);
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
			
			Project project = projectOldService.getProjectById(id);
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
	@GetMapping("/{id}/responsables/html")
	public String getProjectResponsablesHtml(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		Project project = projectOldService.getProjectById(id);
		
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
