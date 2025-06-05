package com.epm.gestepm.controller;

import com.epm.gestepm.modelapi.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.deprecated.activitycenter.dto.ActivityCenter;
import com.epm.gestepm.modelapi.deprecated.country.dto.Country;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
import com.epm.gestepm.modelapi.userholiday.dto.UserHoliday;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.service.RoleService;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.userholiday.dto.UserHolidayDTO;
import com.epm.gestepm.modelapi.userholiday.service.UserHolidaysService;
import com.epm.gestepm.model.activitycenter.service.ActivityCenterServiceImpl;
import com.epm.gestepm.model.country.service.CountryServiceOldImpl;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
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
import java.util.*;

@Controller
@RequestMapping("/users")
public class UserControllerOld {

	private static final Log log = LogFactory.getLog(UserControllerOld.class);
	
	@Value("${gestepm.first-year}")
	private int firstYear;
	
	@Autowired
	private UserServiceOld userServiceOld;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserHolidaysService userHolidayService;
	
	@Autowired
	private SubRoleService subRoleService;
	
	@Autowired
	private CountryServiceOldImpl countryService;
	
	@Autowired
	private ActivityCenterServiceImpl activityCenterServiceOld;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private ManualSigningTypeService manualSigningTypeService;

	@GetMapping("/old/{id}")
	public String userDetail(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		try {
			
			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
	
			// Recover user
			User me = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + me.getId() + " ha accedido a la vista de Detalles de Usuario " + id);
						
			// Load user
			User user = userServiceOld.getUserById(id);
			
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


}
