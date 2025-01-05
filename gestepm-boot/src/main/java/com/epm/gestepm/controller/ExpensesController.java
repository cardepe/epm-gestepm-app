package com.epm.gestepm.controller;

import com.epm.gestepm.model.expensecorrective.service.ExpenseCorrectiveServiceImpl;
import com.epm.gestepm.model.expensecorrective.service.mapper.ExpenseCorrectiveMapper;
import com.epm.gestepm.model.expensesheet.service.ExpenseSheetServiceImpl;
import com.epm.gestepm.model.project.service.ProjectServiceImpl;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveDTO;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {

	private static final Log log = LogFactory.getLog(ExpensesController.class);

	@Autowired
	private ExpenseSheetServiceImpl expenseSheetService;
	
	@Autowired
	private ExpenseCorrectiveServiceImpl expenseCorrectiveService;

	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private SMTPService smtpService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/corrective")
	public String correctiveExpenses(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();
			
			if (user.getRole().getId() != Constants.ROLE_ADMIN_ID && user.getRole().getId() != Constants.ROLE_ADMINISTRATION_ID) {
				model.addAttribute("msgError", messageSource.getMessage("error.role.invalid", new Object[] { }, locale));
				return "simple-error";
			}

			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Correctivos");

			// Recover user projects
			List<ProjectListDTO> projects = projectService.getAllProjectsDTOs();
			
			// Adding attributes to view
			model.addAttribute("projects", projects);
			model.addAttribute("tableActionButtons", ModelUtil.getTableModifyActionButtons());

			// Loading view
			return "corrective-expenses";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/corrective/dt")
	public DataTableResults<ExpenseCorrectiveTableDTO> userCorrectivesDatatable(HttpServletRequest request, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<ExpenseCorrective> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ExpenseCorrectiveTableDTO> expenseSheets = expenseCorrectiveService.getExpenseCorrectivesByUserDataTables(user.getId(), pagination);

			Long totalRecords = expenseCorrectiveService.getExpenseCorrectivesCountByUser(user.getId());

			DataTableResults<ExpenseCorrectiveTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(expenseSheets);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (expenseSheets != null && !expenseSheets.isEmpty()
					&& !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(expenseSheets.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping("/validate/{id}")
	public ResponseEntity<String> validateExpenseSheet(@PathVariable("id") String id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			// Get expense sheet
			ExpenseSheet expenseSheet = expenseSheetService.getExpenseSheetById(Long.parseLong(id));

			// Get user role
			Role role = user.getRole();

			if (role.getId() >= Constants.ROLE_PL_ID && Constants.STATUS_APPROVED.equals(expenseSheet.getStatus())) {
				
				// Move expense sheet to Paid
				expenseSheet.setStatus(Constants.STATUS_PAID);
				
			} else if (role.getId() >= Constants.ROLE_ADMINISTRATION_ID && Constants.STATUS_PENDING.equals(expenseSheet.getStatus())) {
				
				// Move expense sheet to Approved
				expenseSheet.setStatus(Constants.STATUS_APPROVED);
				
			} else {
				
				// Unauthorized validate
				return new ResponseEntity<>(messageSource.getMessage("expense.validate.unauthorized", new Object[] {}, locale),HttpStatus.UNAUTHORIZED);
			}

			// Update expense sheet
			expenseSheet = expenseSheetService.save(expenseSheet);

			// Log info
			log.info("Gasto " + expenseSheet.getId() + " validado por parte del usuario " + user.getId());
			
			// Send mail
			if (Constants.STATUS_APPROVED.equals(expenseSheet.getStatus())) {
				
				 // List<UserDTO> rrhhDTOs = userService.getUserDTOsByRank(Constants.ROLE_RRHH_ID);
				 
				 List<User> teamLeaders = expenseSheet.getProject().getBossUsers();
				 
				 if (!teamLeaders.isEmpty()) {
					 
					 for (User teamLeader : teamLeaders) {

						 if (teamLeader.getState() == 0) {
							 smtpService.sendExpenseTeamLeaderMail(teamLeader.getEmail(), user, expenseSheet, locale);
						 }
					 }
				 }
				 
			} else if (Constants.STATUS_PAID.equals(expenseSheet.getStatus())) {
				smtpService.sendExpenseRRHHMail(expenseSheet.getUser().getEmail(), user, expenseSheet, locale);
			}

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.validate.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.validate.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/decline/{id}")
	public ResponseEntity<String> declineExpenseSheet(@PathVariable("id") String id, @RequestParam(required = false) String observations, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			// Get expense sheet
			ExpenseSheet expenseSheet = expenseSheetService.getExpenseSheetById(Long.parseLong(id));

			// Get user role
			Role role = user.getRole();

			if (role.getId() >= Constants.ROLE_PL_ID) {
				
				// Move expense sheet to Decline
				expenseSheet.setStatus(Constants.STATUS_REJECTED);
				
			} else {
				
				// Unauthorized validate
				return new ResponseEntity<>(messageSource.getMessage("expense.decline.unauthorized", new Object[] {}, locale), HttpStatus.UNAUTHORIZED);
			}
			
			if (StringUtils.isNoneBlank(observations)) {
				expenseSheet.setObservations(observations);
			}

			// Update expense sheet
			expenseSheetService.save(expenseSheet);
			
			// Send decline email
			if (expenseSheet.getUser() != null && StringUtils.isNoneBlank(expenseSheet.getUser().getEmail())) {
				smtpService.sendExpenseDeclineMail(expenseSheet.getUser().getEmail(), user, expenseSheet, locale);
			}

			// Log info
			log.info("Gasto " + expenseSheet.getId() + " denegado por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.decline.success", new Object[] {}, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.decline.error", new Object[] {}, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/corrective/create")
	public ResponseEntity<String> createCorrective(@ModelAttribute ExpenseCorrectiveDTO correctiveDTO, Locale locale) {
		
		try {			
			
			// Recover user
			User user = Utiles.getUsuario();
						
			// Recover project
			Project project = projectService.getProjectById(correctiveDTO.getProject());
						
			ExpenseCorrective expenseCorrective = ExpenseCorrectiveMapper.mapDTOToExpenseCorrective(correctiveDTO, user, project);
			expenseCorrective.setCreationDate(new Date());
			
			// Save
			expenseCorrective = expenseCorrectiveService.save(expenseCorrective);
			
			log.info("Gasto correctivo " + expenseCorrective.getId() + " creado con éxito por parte del usuario " + user.getId());
			
			// Send Mails
			if (project.getResponsables() != null && !project.getResponsables().isEmpty()) {
				
				for (User responsable : project.getResponsables()) {
					smtpService.sendCorrectiveTeamLeaderMail(responsable.getEmail(), user, expenseCorrective, project, locale);
				}
			}
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.create.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.create.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@PostMapping("/corrective/update/{id}")
	public ResponseEntity<String> updateExpenseCorrective(@PathVariable Long id, @ModelAttribute ExpenseCorrectiveDTO expenseCorrectiveDTO, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			ExpenseCorrective expenseCorrective = expenseCorrectiveService.getById(id);
			expenseCorrective.setCost(expenseCorrectiveDTO.getCost());
			expenseCorrective.setDescription(expenseCorrectiveDTO.getDescription());
			
			expenseCorrectiveService.save(expenseCorrective);
			
			log.info("Gasto correctivo " + id + " actualizado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.update.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.update.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/corrective/delete/{id}")
	public ResponseEntity<String> deleteExpenseCorrective(@PathVariable Long id, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
						
			expenseCorrectiveService.delete(id);
			
			log.info("Gasto correctivo " + id + " eliminado con éxito por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.delete.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.corrective.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
}
