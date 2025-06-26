package com.epm.gestepm.controller;

import com.epm.gestepm.model.expensecorrective.service.ExpenseCorrectiveServiceImpl;
import com.epm.gestepm.model.expensecorrective.service.mapper.ExpenseCorrectiveMapper;
import com.epm.gestepm.model.deprecated.project.service.ProjectOldServiceImpl;
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
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.exception.InvalidUserSessionException;
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
	private ExpenseCorrectiveServiceImpl expenseCorrectiveService;

	@Autowired
	private ProjectOldServiceImpl projectService;
	
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
