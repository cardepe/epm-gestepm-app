package com.epm.gestepm.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.role.dto.RoleTableDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.epm.gestepm.model.role.service.mapper.RoleMapper;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;

@Controller
@RequestMapping("/roles")
public class RoleController {

	private static final Log log = LogFactory.getLog(RoleController.class);
	
	@Autowired
	private SubRoleService subRoleService;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public String getMyRoles(Locale locale, Model model, HttpServletRequest request) {

		try {
			
			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();

			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Roles");

			model.addAttribute("tableActionButtons", ModelUtil.getTableModifyActionButtons());

			// Loading view
			return "roles";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/dt")
	public DataTableResults<RoleTableDTO> roleBossRolesDatatable(HttpServletRequest request, Locale locale) {

		DataTableRequest<Role> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

		List<RoleTableDTO> roles = subRoleService.getRolesDataTables(pagination);

		Long totalRecords = subRoleService.getRolesCount();

		DataTableResults<RoleTableDTO> dataTableResult = new DataTableResults<>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(roles);
		dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (!roles.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(roles.size()));
		}

		return dataTableResult;
	}
	
	@ResponseBody
	@PostMapping("/create")
	public ResponseEntity<String> createProject(@ModelAttribute RoleDTO roleDTO, Locale locale) {
		
		try {			
			
			// Recover user
			User user = Utiles.getUsuario();
						
			SubRole subRole = RoleMapper.mapDTOToSubRole(roleDTO);
			
			subRoleService.save(subRole);
			
			log.info("Rol " + subRole.getRol() + " creado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("role.create.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("role.create.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/update/{id}")
	public ResponseEntity<String> updateRole(@PathVariable Long id, @ModelAttribute RoleDTO roleDTO, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
						
			SubRole subRole = RoleMapper.mapDTOToSubRole(roleDTO);
			subRole.setId(id);
			
			subRoleService.save(subRole);
			
			log.info("Rol " + id + " actualizado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("role.update.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("role.update.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable Long id, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
						
			subRoleService.delete(id);
			
			log.info("Rol " + id + " eliminado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("role.delete.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("role.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

}
