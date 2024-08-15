package com.epm.gestepm.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.epm.gestepm.modelapi.expense.dto.ExpenseDTO;
import com.epm.gestepm.modelapi.expense.dto.ExpenseTableDTO;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveDTO;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrectiveTableDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetDTO;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheetTableDTO;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.epm.gestepm.model.expensecorrective.service.mapper.ExpenseCorrectiveMapper;
import com.epm.gestepm.model.expense.service.mapper.ExpenseMapper;
import com.epm.gestepm.modelapi.expense.dto.Expense;
import com.epm.gestepm.modelapi.expensecorrective.dto.ExpenseCorrective;
import com.epm.gestepm.modelapi.expensefile.dto.ExpenseFile;
import com.epm.gestepm.modelapi.expensesheet.dto.ExpenseSheet;
import com.epm.gestepm.modelapi.paymenttype.dto.PaymentType;
import com.epm.gestepm.modelapi.pricetype.dto.PriceType;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.model.expensecorrective.service.ExpenseCorrectiveServiceImpl;
import com.epm.gestepm.model.expensefile.service.ExpenseFileServiceImpl;
import com.epm.gestepm.model.expense.service.ExpenseServiceImpl;
import com.epm.gestepm.model.expensesheet.service.ExpenseSheetServiceImpl;
import com.epm.gestepm.model.project.service.ProjectServiceImpl;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.model.common.utils.classes.SingletonUtil;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;

@Controller
@RequestMapping("/expenses")
public class ExpensesController {

	private static final Log log = LogFactory.getLog(ExpensesController.class);

	@Autowired
	private ExpenseSheetServiceImpl expenseSheetService;

	@Autowired
	private ExpenseServiceImpl expenseService;
	
	@Autowired
	private ExpenseCorrectiveServiceImpl expenseCorrectiveService;

	@Autowired
	private ExpenseFileServiceImpl expenseFileService;

	@Autowired
	private ProjectServiceImpl projectService;

	@Autowired
	private SingletonUtil singletonUtil;
	
	@Autowired
	private SMTPService smtpService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/personal")
	public String personalExpenses(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();

			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Gastos");

			// Get expenses sheet with status Pending
			List<ExpenseSheet> pendingExpensesSheet = expenseSheetService.getExpenseSheetsByUserAndStatus(user.getId(), Constants.STATUS_PENDING);
			int totalPendingExpensesSheet = pendingExpensesSheet.size();
			Date lastPendingExpenseSheetDate = expenseSheetService.getLastExpenseSheetDate(pendingExpensesSheet);

			// Get expense sheets with status Approved
			List<ExpenseSheet> approvedExpenseSheets = expenseSheetService.getExpenseSheetsByUserAndStatus(user.getId(), Constants.STATUS_APPROVED);
			int totalApprovedExpenseSheets = approvedExpenseSheets.size();
			
			double totalPendingAmount = expenseSheetService.getTotalPendingAmount(approvedExpenseSheets);

			// Recover user projects
			List<ProjectListDTO> projects = null;
			
			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				projects = projectService.getAllProjectsDTOs();
			} else {
				projects = projectService.getProjectsDTOByUserId(user.getId());
			}
			
			// Adding attributes to view
			model.addAttribute("totalPendingExpensesSheet", totalPendingExpensesSheet);
			model.addAttribute("totalApprovedExpenseSheets", totalApprovedExpenseSheets);
			model.addAttribute("totalPendingAmount", totalPendingAmount);
			model.addAttribute("lastPendingExpenseSheetDate", lastPendingExpenseSheetDate);
			model.addAttribute("projects", projects);
			model.addAttribute("tableActionButtons", ModelUtil.getTableActionButtons());

			// Loading view
			return "personal-expenses";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
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
	@GetMapping("/personal/dt")
	public String userExpensesDatatable(HttpServletRequest request, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<Expense> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ExpenseSheetTableDTO> expenseSheets = expenseSheetService
					.getExpenseSheetsByUserDataTables(user.getId(), pagination);

			Long totalRecords = expenseSheetService.getExpenseSheetsCountByUser(user.getId());

			DataTableResults<ExpenseSheetTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(expenseSheets);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (expenseSheets != null && !expenseSheets.isEmpty()
					&& !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(expenseSheets.size()));
			}

			return dataTableResult.getJson();

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@GetMapping("/corrective/dt")
	public String userCorrectivesDatatable(HttpServletRequest request, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<ExpenseCorrective> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ExpenseCorrectiveTableDTO> expenseSheets = expenseCorrectiveService.getExpenseCorrectivesByUserDataTables(user.getId(), pagination);

			Long totalRecords = expenseCorrectiveService.getExpenseCorrectivesCountByUser(user.getId());

			DataTableResults<ExpenseCorrectiveTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(expenseSheets);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (expenseSheets != null && !expenseSheets.isEmpty()
					&& !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(expenseSheets.size()));
			}

			return dataTableResult.getJson();

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@GetMapping("/personal/create")
	public String personalExpensesCreate(@RequestParam("project") Long projectId,
			@RequestParam("sheetName") String sheetName, Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();

			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de creación de un gasto");

			// Recover project
			Project project = projectService.getProjectById(projectId);

			// Recover static data from db
			List<PaymentType> paymentTypes = singletonUtil.getPaymentTypes();
			List<PriceType> priceTypes = singletonUtil.getPriceTypes();

			// Adding attributes to view
			model.addAttribute("dateTime", Utiles.getActualDateTime());
			model.addAttribute("project", project);
			model.addAttribute("sheetName", sheetName);
			model.addAttribute("paymentTypes", paymentTypes);
			model.addAttribute("priceTypes", priceTypes);
			model.addAttribute("priceTypesJson", singletonUtil.getPriceTypesJson());
			model.addAttribute("tableActionButtons", ModelUtil.getTableDownloadActionButtons());

			// Loading view
			return "personal-expenses-create";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@GetMapping("/personal/view/{sheetId}")
	public String personalExpensesCreate(@PathVariable("sheetId") Long sheetId, Locale locale, Model model,
			HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista detalle de una hoja de gastos");

			// Recover expense sheet
			ExpenseSheet expenseSheet = expenseSheetService.getExpenseSheetById(sheetId);

			// Adding attributes to view
			model.addAttribute("userLoaded", user);
			model.addAttribute("backUrl", "/expenses/personal");
			model.addAttribute("expenseSheet", expenseSheet);
			model.addAttribute("tableActionButtons", ModelUtil.getTableDownloadButtons());

			// Loading view
			return "personal-expenses-view";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/personal/{sheetId}/dt")
	public String userExpensesDatatable(@PathVariable("sheetId") Long sheetId, HttpServletRequest request,
			Locale locale) {

		try {

			DataTableRequest<Expense> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			List<ExpenseTableDTO> expenses = expenseService.getExpensesBySheetDataTables(sheetId, pagination);

			Long totalRecords = expenseService.getExpensesCountBySheet(sheetId);

			DataTableResults<ExpenseTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setListOfDataObjects(expenses);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (expenses != null && !expenses.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(expenses.size()));
			}

			return dataTableResult.getJson();

		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping(value = "/create")
	public ResponseEntity<?> addExpenseSheet(@RequestBody ExpenseSheetDTO expenseSheetDTO, Locale locale) {

		try {

			if (expenseSheetDTO.getExpenses() == null || expenseSheetDTO.getExpenses().isEmpty()) {
				return new ResponseEntity<>(messageSource.getMessage("expense.create.empty", new Object[] {}, locale),
						HttpStatus.NOT_FOUND);
			}

			// Recover user
			User user = Utiles.getUsuario();

			// Create expense
			Project project = projectService.getProjectById(expenseSheetDTO.getProject());
			ExpenseSheet expenseSheet = ExpenseMapper.mapDTOToExpenseSheet(expenseSheetDTO, user, project,
					singletonUtil);

			expenseSheet = expenseSheetService.create(expenseSheet);

			ExpenseSheetDTO finalExpenseSheetDTO = ExpenseMapper.expenseSheetToMapDTO(expenseSheet, project.getId(),
					user.getId());

			log.info("Gasto " + expenseSheet.getId() + " creado con éxito por parte del usuario " + user.getId());

			if (!project.getBossUsers().isEmpty()) {

				for (User teamLeader : project.getBossUsers()) {

					if (teamLeader.getState() == 0) {
						smtpService.sendExpenseUserMail(teamLeader.getEmail(), expenseSheet, locale);
					}
				}
			}

			return new ResponseEntity<>(finalExpenseSheetDTO, HttpStatus.OK);

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("expense.create.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteExpense(@PathVariable Long id, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			expenseSheetService.deleteById(id);

			log.info("Hoja de gastos " + id + " eliminado con éxito por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.delete.success", new Object[] {}, locale),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.delete.error", new Object[] {}, locale),
					HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping(value = "/get/{id}")
	public ExpenseDTO getExpense(@PathVariable("id") String id) {
		Expense expense = expenseService.getExpenseById(Long.parseLong(id));
		return ExpenseMapper.mapExpenseToDTO(expense);
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
	@PostMapping("/{expenseId}/upload")
	public ResponseEntity<String> uploadImage(@PathVariable("expenseId") Long expenseId, @RequestParam("file") MultipartFile file, Locale locale) {

		try {
			
			// Get expense
			Expense expense = expenseService.getExpenseById(expenseId);

			ExpenseFile expenseFile = ExpenseMapper.mapMultipartFileToExpenseFile(file, expense);

			expenseFileService.save(expenseFile);

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("expense.upload.success", new Object[] {}, locale), HttpStatus.OK);
			
		} catch (IOException e) {
			return new ResponseEntity<>(messageSource.getMessage("expense.upload.error", new Object[] {}, locale),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseBody
	@GetMapping("/file/{expenseId}")
	public FileDTO getFile(@PathVariable("expenseId") Long expenseId) {

		ExpenseFile expenseFile = expenseFileService.getExpenseFileByExpenseId(expenseId);

		return ExpenseMapper.mapExpenseFileToFileDTO(expenseFile);
	}

	@GetMapping("/{id}/excel")
	public HttpEntity<ByteArrayResource> generateExcel(@PathVariable Long id, Locale locale) throws IOException {

		XSSFWorkbook workbook = null;

		try {

			// Recover user
			User user = Utiles.getUsuario();

			// Log info
			log.info("El usuario " + user.getId() + " está generando el excel de la hoja de gastos " + id);

			// Get expense sheet
			ExpenseSheet expenseSheet = expenseSheetService.getExpenseSheetById(id);

			ByteArrayOutputStream file = new ByteArrayOutputStream();

			workbook = expenseSheetService.generateExpenseSheetExcel(id, user.getId(), expenseSheet, locale);
			workbook.write(file);

			byte[] excelContent = file.toByteArray();

			String fileName = "Nota_" + id + "_" + expenseSheet.getProject().getName() + "_" + user.getName() + "_"
					+ user.getSurnames() + "_" + Utiles.getDateFormatted(expenseSheet.getCreationDate()) + ".xlsx";
			fileName = fileName.replace(" ", "_");
			
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + fileName + "\"");

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
	
	@GetMapping(value = "/{id}/pdf", produces = { "application/pdf" })
	public HttpEntity<byte[]> exportExpensePdf(@PathVariable Long id, Locale locale) {
		
		log.info("Exportando el pdf de la hoja de gastos " + id);
		
		// Get expense sheet
		ExpenseSheet expenseSheet = expenseSheetService.getExpenseSheetById(id);
		
		if (expenseSheet == null) {
			log.error("No existe la hoja de gastos con id " + id);
			return null;
		}
		
		byte[] pdf = expenseSheetService.generateExpensePdf(expenseSheet, locale);
		
		if (pdf == null) {
			log.error("Error al generar el fichero pdf de la hoja de gastos " + id);
			return null;
		}

		String fileName = messageSource.getMessage("expense.pdf.name", new Object[] { expenseSheet.getId().toString(), Utiles.getDateFormatted(expenseSheet.getCreationDate()) }, locale);
		
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName + ".pdf");
        
        return new HttpEntity<>(pdf, headers);
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
