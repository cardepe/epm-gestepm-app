package com.epm.gestepm.controller;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.inspection.service.mapper.MapIToInspectionUpdateDto;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.model.user.service.mapper.SigningMapper;
import com.epm.gestepm.modelapi.common.utils.CalendarDTO;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareService;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.service.DisplacementShareService;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.finder.InspectionByIdFinderDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.service.InterventionSubShareService;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;
import com.epm.gestepm.modelapi.modifiedsigning.service.ModifiedSigningService;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningDTO;
import com.epm.gestepm.modelapi.personalsigning.service.PersonalSigningService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.shares.ShareDecorator;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlDetailTableDTO;
import com.epm.gestepm.modelapi.timecontrol.dto.TimeControlTableDTO;
import com.epm.gestepm.modelapi.timecontrol.service.TimeControlService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningDTO;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningTableDTO;
import com.epm.gestepm.modelapi.usermanualsigning.service.UserManualSigningService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningShareDTO;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningTableDTO;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.mapstruct.factory.Mappers.getMapper;

@Controller
@RequestMapping("/signing")
public class SigningController {

	private static final Log log = LogFactory.getLog(SigningController.class);
	
	@Value("${first.year}")
	private int firstYear;

	@Value("#{'${rrhh.mails}'.split(',')}")
	private List<String> rrhhMails;
	
	@Autowired
	private ConstructionShareService constructionShareService;
	
	@Autowired
	private DisplacementShareService displacementShareService;

	@Autowired
	private ShareDecorator shareDecorator;

	@Autowired
	private InterventionPrShareService interventionPrShareService;
	
	@Autowired
	private InterventionSubShareService interventionSubShareService;

	@Autowired
	private InspectionService inspectionService;

	@Autowired
	private NoProgrammedShareService noProgrammedShareService;

	@Autowired
	private ManualSigningTypeService manualSigningTypeService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ModifiedSigningService modifiedSigningService;
	
	@Autowired
	private PersonalSigningService personalSigningService;
	
	@Autowired
	private ProjectService projectService;

	@Autowired
	private SMTPService smtpService;
	
	@Autowired
	private TimeControlService timeControlService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserSigningService userSigningService;

	@Autowired
	private UserManualSigningService userManualSigningService;
	
	@Autowired
	private WorkShareService workShareService;
	
	@GetMapping
	public String signingPage(@RequestParam(value = "user", required = false) Long userId, Locale locale, Model model, HttpServletRequest request) {
		
		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			if (userId != null && !user.getId().equals(userId)) {
				
				if (user.getRole().getId() < Constants.ROLE_RRHH_ID) {
					return "unauthorized";
				}
				
				UserTableDTO userDTO = userService.getUserDTOByUserId(userId, null);
				
				if (userDTO != null) {
					model.addAttribute("userName", userDTO.getName() + " " + userDTO.getSurnames());
					model.addAttribute("trashVisible", true);
				}
				
			} else {
				
				model.addAttribute("userName", user.getName() + " " + user.getSurnames());
				model.addAttribute("trashVisible", false);
				userId = user.getId();
			}
			
			// Get Current UserSigning
			UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(userId, null);
			
			// Recover user projects
			List<ProjectListDTO> projects;
			
			if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID) {
				projects = projectService.getAllProjectsDTOs();
			} else {
				projects = projectService.getProjectsDTOByUserId(userId);
			}

			final List<Project> displacementProjects = this.projectService.findDisplacementProjects();
			model.addAttribute("displacementProjects", displacementProjects);
			
			// Adding attributes to view
			model.addAttribute("projects", projects);
			model.addAttribute("currentUserSigning", currentUserSigning);
			model.addAttribute("userSigningStarted", currentUserSigning != null);
			
			model.addAttribute("currentUser", userId.equals(user.getId()));
			model.addAttribute("userId", userId);
			
			// Load Action Buttons for DataTable
			model.addAttribute("tableActionButtons", ModelUtil.getViewTrashActionButton());
						
			return "signing-page";
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@PostMapping
	public ResponseEntity<String> signingAction(@ModelAttribute UserSigningDTO userSigningDTO, Locale locale) {
		
		try {
		
			// Recover user
			User user = Utiles.getUsuario();

			// Signing user
			User signingUser = user;
						
			// Get Current UserSigning
			UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(signingUser.getId(), null);
			
			if (userSigningDTO.getState() == 0) {
				
				// Check if have some signing
				if (currentUserSigning != null) {
					return new ResponseEntity<>(messageSource.getMessage("signing.page.action.error", new Object[] { }, locale), HttpStatus.NOT_MODIFIED);
				}
				
				Project project = projectService.getProjectById(userSigningDTO.getProject());
				
				if (project == null) {
					throw new Exception("No existe el proyecto " + userSigningDTO.getProject());
				}

				// Create new signing
				if (userSigningDTO.getStartDate() == null) {
					userSigningDTO.setStartDate(OffsetDateTime.now());
				}

				if (userSigningDTO.getUserId() != null) {
					signingUser = this.userService.getUserById(userSigningDTO.getUserId());
				}

				// Map
				UserSigning userSigning = SigningMapper.mapUserSigningDTOToEntity(userSigningDTO, signingUser, project);
				userSigning.setStartLocation(userSigningDTO.getGeolocation());
				
				// Save
				userSigning = userSigningService.save(userSigning);

				if (userSigningDTO.getUserId() == null) {
					log.info("El usuario " + signingUser.getId() + " ha empezado el fichaje " + userSigning.getId());
				} else {
					log.info("El usuario " + user.getId() + " ha creado el fichaje " + userSigning.getId());
				}
				
			} else if (userSigningDTO.getState() == 1) {
				
				// Set endDate to current signing
				currentUserSigning.setEndDate(OffsetDateTime.now());
				currentUserSigning.setEndLocation(userSigningDTO.getGeolocation());

				currentUserSigning = userSigningService.save(currentUserSigning);

				log.info("El usuario " + signingUser.getId() + " ha finalizado el fichaje " + currentUserSigning.getId());
			}

			return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("signing.page.action.error", new Object[] { }, locale), HttpStatus.NOT_MODIFIED);
		}
	}
	
	@ResponseBody
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserSigning(@PathVariable("id") String id, Locale locale) {
		
		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Delete from db
			userSigningService.delete(Long.parseLong(id));
			
			// Log info
			log.info("UserSigning " + id + " eliminado por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("signing.delete.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("signing.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@GetMapping("/dt")
	public DataTableResults<UserSigningTableDTO> signingPageDatatable(@RequestParam(value = "user", required = false) Long userId, HttpServletRequest request, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			DataTableRequest<Object> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
			
			if (userId == null) {
				userId = user.getId();
			}

			List<UserSigningTableDTO> userSigningTable = userSigningService.getUserSigningDTOsByUserId(userId, pagination);
			
			Long totalRecords = userSigningService.getUserSigningCountByUser(userId);
			
			DataTableResults<UserSigningTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(userSigningTable);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));
	
			if (!userSigningTable.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(userSigningTable.size()));
			}
	
			return dataTableResult;
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}
	
	@GetMapping("/{id}")
	public String signingDetail(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {
		
		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Get Current UserSigning
			UserSigning userSigning = userSigningService.getById(id);
			
			if (userSigning == null) {
				model.addAttribute("msgError", messageSource.getMessage("signing.detail.not.found", new Object[] { }, locale));
				throw new Exception("El fichaje " + id + " no existe.");
			}
			
			// Adding attributes to view
			model.addAttribute("userSigning", userSigning);
			model.addAttribute("tableActionButtons", ModelUtil.getViewActionButton());
			
			// log info
			log.info("El usuario " + user.getId() + " ha accedido al detalle del fichaje " + id);
						
			return "signing-detail";
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		} catch (Exception e) {
			log.error(e);
			return "simple-error";
		}
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@GetMapping("/{id}/dt")
	public DataTableResults<ShareTableDTO> signingDetailDataTable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

		DataTableRequest<UserSigning> dataTableInRQ = new DataTableRequest<>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		
		Object[] obj = filterByShare(id, (pagination.getPageNumber() / pagination.getPageSize()), pagination.getPageSize());

		Long totalRecords = (Long) obj[0];
		List<ShareTableDTO> signings = (List<ShareTableDTO>) obj[1];

		DataTableResults<ShareTableDTO> dataTableResult = new DataTableResults<>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setData(signings);
		dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
		dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

		if (signings != null && !signings.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
			dataTableResult.setRecordsFiltered(Integer.toString(signings.size()));
		}

		return dataTableResult;
	}

	@GetMapping(value = "/project/{projectId}/weekly", produces = { "application/zip" })
	public void exportWeeklyPdf(@PathVariable Integer projectId, HttpServletResponse response, Locale locale) {

		try {

			log.info("Exportando partes de proyecto " + projectId + " de forma semanal");

			final ZoneOffset zoneOffset = ZoneOffset.UTC;
			final OffsetDateTime startOfWeek = OffsetDateTime.now(zoneOffset)
					.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
					.withHour(0).withMinute(0).withSecond(0);
			final OffsetDateTime endOfWeek = OffsetDateTime.now(zoneOffset)
					.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
					.withHour(23).withMinute(59).withSecond(59);

			final List<PdfFileDTO> pdfs = this.shareDecorator.exportShares(projectId, startOfWeek, endOfWeek);

			final String fileName = messageSource.getMessage("shares.zip.name", new Object[] { Utiles.getDateFormatted(new Date()) }, locale) + ".zip";

			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.setContentType("application/zip");

			final ServletOutputStream out = response.getOutputStream();
			final ZipOutputStream zout = new ZipOutputStream(out);

			for (final PdfFileDTO pdf : pdfs) {

				final ZipEntry zip = new ZipEntry(pdf.getFileName());

				zout.putNextEntry(zip);
				zout.write(pdf.getDocumentBytes());
				zout.closeEntry();
			}

			zout.close();

		} catch (IOException e) {
			log.error(e);
		}
	}

	@GetMapping("/manual")
	public String manualSigning(@RequestParam(value = "user", required = false) Long userId,Locale locale, Model model, HttpServletRequest request) {

		try{

			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			final User user = Utiles.getUsuario();

			if (userId != null && !user.getId().equals(userId)) {

				if (user.getRole().getId() < Constants.ROLE_RRHH_ID) {
					return "unauthorized";
				}

				UserTableDTO userDTO = userService.getUserDTOByUserId(userId, null);

				if (userDTO != null) {
					model.addAttribute("userId", userDTO.getId());
					model.addAttribute("userName", userDTO.getName() + " " + userDTO.getSurnames());
					model.addAttribute("trashVisible", true);
				}

			} else {

				userId = user.getId();

				model.addAttribute("userId", userId);
				model.addAttribute("userName", user.getName() + " " + user.getSurnames());
				model.addAttribute("trashVisible", false);
			}

			final List<ManualSigningType> manualSigningTypes = this.manualSigningTypeService.findAll();
			manualSigningTypes.sort(Comparator.comparing(ManualSigningType::getName));

			model.addAttribute("manualSigningTypes", manualSigningTypes);
			model.addAttribute("tableActionButtons", ModelUtil.getTableFileAndTrashActionButtons());

			log.info("El usuario " + user.getId() + " ha accedido a la vista de Fichajes Manuales");

			return "manual-signing";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/manual/dt")
	public DataTableResults<UserManualSigningTableDTO> manualSigningDataTable(@RequestParam(value = "user", required = false) Long userId, HttpServletRequest request, Locale locale) {

		try {

			// Recover user
			User user = Utiles.getUsuario();

			DataTableRequest<Object> dataTableInRQ = new DataTableRequest<>(request);
			PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			if (userId == null) {
				userId = user.getId();
			}

			List<UserManualSigningTableDTO> userSigningTable = userManualSigningService.getUserManualSigningDTOsByUserId(userId, pagination);

			Long totalRecords = userManualSigningService.getUserManualSigningCountByUser(userId);

			DataTableResults<UserManualSigningTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(userSigningTable);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (!userSigningTable.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(userSigningTable.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping("/manual")
	public ResponseEntity<String> manualSigningAction(@ModelAttribute UserManualSigningDTO userManualSigningDTO, Locale locale, Model model, HttpServletRequest request) {

		try {

			final User user = Utiles.getUsuario();
			User signingUser = user;

			if(user.getId() != userManualSigningDTO.getUserId()) {
				signingUser = this.userService.getUserById(userManualSigningDTO.getUserId());
			}

			final ManualSigningType manualType = this.manualSigningTypeService.findById(userManualSigningDTO.getManualTypeId());

			final UserManualSigning signing = new UserManualSigning();
			signing.setUser(signingUser);
			signing.setManualSigningType(manualType);
			signing.setStartDate(userManualSigningDTO.getStartDate());
			signing.setEndDate(userManualSigningDTO.getEndDate());
			signing.setDescription(userManualSigningDTO.getDescription());

			if (userManualSigningDTO.getJustification() != null && !userManualSigningDTO.getJustification().isEmpty()) {

				final String fileName = userManualSigningDTO.getJustification().getOriginalFilename();
				final String ext = FilenameUtils.getExtension(fileName);
				final byte[] materialsFileData = FileUtils.compressBytes(userManualSigningDTO.getJustification().getBytes());

				signing.setJustification(materialsFileData);
				signing.setJustificationExt(ext);
			}

			signing.setLocation(userManualSigningDTO.getGeolocation());

			this.userManualSigningService.save(signing);

			if (!rrhhMails.isEmpty()) {
				rrhhMails.forEach(mail -> this.smtpService.sendSigningManualMail(mail, signing, locale));
			}

			return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.error", new Object[] { }, locale), HttpStatus.NOT_MODIFIED);
		}
	}

	@ResponseBody
	@DeleteMapping("/manual/{id}")
	public ResponseEntity<String> deleteUserManualSigning(@PathVariable("id") Long id, Locale locale) {

		try {

			User user = Utiles.getUsuario();

			userManualSigningService.delete(id);

			// Log info
			log.info("UserManualSigning " + id + " eliminado por parte del usuario " + user.getId());

			// Return data
			return new ResponseEntity<>(messageSource.getMessage("signing.manual.delete.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(messageSource.getMessage("signing.manual.delete.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/manual/{id}/file")
	public HttpEntity<ByteArrayResource> getManualSigningFile(@PathVariable Long id, Locale locale) {

		log.info("Exportando el justificante de fichaje manual del fichaje " + id);

		UserManualSigning userManualSigning = userManualSigningService.getById(id);

		if (userManualSigning == null) {
			log.error("No existe el fichaje con id " + id);
			return null;
		}

		if (userManualSigning.getJustification() == null) {
			log.error("El fichaje " + id + " no contiene ningun justificante adjunto");
			return null;
		}

		byte[] fileBytes = FileUtils.decompressBytes(userManualSigning.getJustification());

		if (fileBytes == null) {
			log.error("Error al generar el fichero de justificante para el fichaje manual " + id);
			return null;
		}

		String fileName = "manual_signing_" + id + "." + userManualSigning.getJustificationExt();

		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "force-download"));
		header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

		return new HttpEntity<>(new ByteArrayResource(fileBytes), header);
	}
	
	@GetMapping("/personal")
	public String horarioPersonal(Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);

			// Recover user
			User user = Utiles.getUsuario();

			// Redirect with login if im a customer
			if (Constants.ROLE_CUSTOMER_ID == user.getRole().getId()) {
				return "redirect:/forum/login";
			}
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Fichajes Personales");

			// Adding to model the total time spended today
			getTodaySingingTimer(user.getId(), model);
			
			// Adding attributes to view
			model.addAttribute("dateTime", Utiles.getActualDateTime());
			
			// Loading view
			return "personal-signing";
			
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@PostMapping("/personal")
	public ResponseEntity<String> ficharHorarioPersonal(@ModelAttribute PersonalSigningDTO personalSigningDTO, Locale locale, Model model, HttpServletRequest request) {

		try {

			final User user = Utiles.getUsuario();
			User signingUser = user;

			if(user.getId() != personalSigningDTO.getUserId()) {
				signingUser = this.userService.getUserById(personalSigningDTO.getUserId());
			}

			log.info("El usuario " + user.getId() + " ha creado un fichaje personal al usuario " + signingUser.getId());

			final PersonalSigning personalSigning = new PersonalSigning();
			personalSigning.setUser(signingUser);
			personalSigning.setStartDate(personalSigningDTO.getStartDate());
			personalSigning.setEndDate(personalSigningDTO.getEndDate());

			this.personalSigningService.save(personalSigning);

			return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.error", new Object[] { }, locale), HttpStatus.NOT_MODIFIED);
		}
	}
	
	@ResponseBody
	@GetMapping(value = "/personal/calendar")
	public ResponseEntity<List<CalendarDTO>> getPersonalCalendar(@RequestParam String start, @RequestParam String end, HttpServletRequest request, Locale locale) {
		
		try {
			
			// Format the String ISO 8601 to Date
			Date startDate = Utiles.transformStringToDate(start);
			Date endDate = Utiles.transformStringToDate(end);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Generate CalendarDTO List
			List<CalendarDTO> calendarDTOs = generateSigninCalendar(startDate, endDate, user.getId(), locale);
			
			// Return data
			return new ResponseEntity<>(calendarDTOs, HttpStatus.OK);
			
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}
	
	@ResponseBody
	@GetMapping(value = "/personal/calendar/{id}")
	public ResponseEntity<List<CalendarDTO>> getPersonalCalendar(@PathVariable Long id, @RequestParam String start, @RequestParam String end, HttpServletRequest request, Locale locale) {
		
		try {
			
			// Format the String ISO 8601 to Date
			Date startDate = Utiles.transformStringToDate(start);
			Date endDate = Utiles.transformStringToDate(end);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Get user role
			Role role = user.getRole();
						
			if (role.getId() < Constants.ROLE_PL_ID) {
				return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
			}
			
			// Generate CalendarDTO List
			List<CalendarDTO> calendarDTOs = generateSigninCalendar(startDate, endDate, id, locale);
			
			// Return data
			return new ResponseEntity<>(calendarDTOs, HttpStatus.OK);
			
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}
	
	@GetMapping("/personal/{id}/excel")
	public HttpEntity<ByteArrayResource> generateExcel(@PathVariable Long id, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, Locale locale) throws IOException {

		XSSFWorkbook workbook = null;

		try {

			final User user = Utiles.getUsuario();

			if (month == null) {
				month = Calendar.getInstance().get(Calendar.MONTH) + 1;
			}

			if (year == null) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}

			log.info("El usuario " + user.getId() + " está generando el excel de horas imputadas del usuario " + id);

			final User userSigning = userService.getUserById(id);
			
			final ByteArrayOutputStream file = new ByteArrayOutputStream();

			workbook = personalSigningService.generateSigningSheetExcel(month, year, userSigning, locale);
			workbook.write(file);

			final byte[] excelContent = file.toByteArray();

			String fileName = "Signing_" + userSigning.getName() + "_" + userSigning.getSurnames() + "_" + year + ".xlsx";
			fileName = fileName.replace(" ", "_");
			
			final HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

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

	@GetMapping("/personal/{id}/woffu/excel")
	public HttpEntity<ByteArrayResource> generateWoffuExcel(@PathVariable Long id, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, Locale locale) throws IOException {

		XSSFWorkbook workbook = null;

		try {

			final User user = Utiles.getUsuario();

			if (month == null) {
				month = Calendar.getInstance().get(Calendar.MONTH);
			}

			if (year == null) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}

			log.info("El usuario " + user.getId() + " está generando el woffu excel de horas imputadas del usuario " + id);

			final User userSigning = userService.getUserById(id);

			final ByteArrayOutputStream file = new ByteArrayOutputStream();

			workbook = personalSigningService.generateSigningSheetWoffuExcel(month, year, userSigning, locale);
			workbook.write(file);

			final byte[] excelContent = file.toByteArray();

			String fileName = "Signing_Woffu_" + userSigning.getName() + "_" + userSigning.getSurnames() + "_" + month + "_" + year + ".xlsx";
			fileName = fileName.replace(" ", "_");

			final HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "force-download"));
			header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

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

	@GetMapping("/personal/time-control")
	public String timeControlList(@RequestParam(value = "user", required = false) Long userId, Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			if (userId != null && !user.getId().equals(userId) && user.getRole().getId() < Constants.ROLE_RRHH_ID) {
				return "unauthorized";
			}
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista de Control Horario" + (userId == null ? "" : " del usuario " + userId));
						
			// Load months
			Map<Integer, String> months = ModelUtil.loadMonths(messageSource, locale);
			
			int actualYear = Calendar.getInstance().get(Calendar.YEAR);
			int[] yearsDropdown = new int[(actualYear - firstYear) + 1];
			
			for (int i = firstYear; i <= actualYear; i++) {
				yearsDropdown[actualYear - i] = i;
			}
				
			// Carga del modelo
			model.addAttribute("userDetailId", userId);
			model.addAttribute("tableActionButtons", ModelUtil.getViewActionButton());
			model.addAttribute("months", months);
			model.addAttribute("years", yearsDropdown);
			
			// Loading view
			return "time-control-view";
			
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@GetMapping("/personal/time-control/dt")
	public DataTableResults<TimeControlTableDTO> timeControlDatatable(@RequestParam(value = "user", required = false) Long userId, @RequestParam(required = false) Integer month, @RequestParam(required = false) Integer year, HttpServletRequest request, Locale locale) {

		try {
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Load user
			User userDetail = null;
			
			if (userId != null) {
				userDetail = userService.getUserById(userId);
			} else {
				userDetail = user;
			}
			
			DataTableRequest<Object> dataTableInRQ = new DataTableRequest<>(request);
			
			Calendar c = Calendar.getInstance();
			int selectedMonth = c.get(Calendar.MONTH) + 1;
			int selectedYear = c.get(Calendar.YEAR);
			
			if (month != null) {
				selectedMonth = month;
			}
			
			if (year != null) {
				selectedYear = year;
			}
	
			List<TimeControlTableDTO> timeControlTable = timeControlService.getTimeControlTableDTOByDateAndUser(selectedMonth, selectedYear, userDetail.getId(), userDetail.getActivityCenter().getId(), locale);
			
			DataTableResults<TimeControlTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(timeControlTable);
			dataTableResult.setRecordsTotal(String.valueOf(timeControlTable.size()));
			dataTableResult.setRecordsFiltered(Long.toString(timeControlTable.size()));
	
			if (!timeControlTable.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(timeControlTable.size()));
			}
	
			return dataTableResult;
		
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@GetMapping("/personal/time-control/{id}")
	public String timeControlView(@RequestParam String date, @PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

		try {

			// Loading constants
			ModelUtil.loadConstants(locale, model, request);
			
			// Recover user
			User user = Utiles.getUsuario();
			
			// Log info
			log.info("El usuario " + user.getId() + " ha accedido a la vista detalle de Control Horario " + id);
						
			// Transform String to Date
			Date dateFormatted = Utiles.transformSimpleStringToDate(date);
			
			// Get TimeControl info
			TimeControlTableDTO timeControlDTO = timeControlService.getTimeControlDetail(dateFormatted, id);
		
			if (timeControlDTO.getDifference().startsWith("-")) {
				timeControlDTO.setDifference("<span style=\"color: red\">" + timeControlDTO.getDifference().replace("-", "") + "</span>");
			}
			
			String urlPattern = "";
			
			if (!user.getId().equals(id)) {
				urlPattern = "?user=" + id;
			}
			
			// Carga del modelo
			model.addAttribute("id", id);
			model.addAttribute("urlPattern", urlPattern);
			model.addAttribute("actualDate", date);
			model.addAttribute("timeControlDTO", timeControlDTO);
			
			// Loading view
			return "time-control-detail";
			
		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}
	
	@ResponseBody
	@GetMapping("/personal/time-control/{id}/dt")
	public DataTableResults<TimeControlDetailTableDTO> timeControlDetailDatatable(@RequestParam String date, @PathVariable Long id, HttpServletRequest request, Locale locale) {

		try {
			
			// Recover user
			// User user = Utiles.getUsuario();
			
			DataTableRequest<Object> dataTableInRQ = new DataTableRequest<>(request);
			
			// Transform String to Date
			Date dateFormatted = Utiles.transformSimpleStringToDate(date);
	
			List<TimeControlDetailTableDTO> timeControlTable = timeControlService.getTimeControlDetailTableDTOByDateAndUser(dateFormatted, id, locale);
			
			DataTableResults<TimeControlDetailTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(timeControlTable);
			dataTableResult.setRecordsTotal(String.valueOf(timeControlTable.size()));
			dataTableResult.setRecordsFiltered(Long.toString(timeControlTable.size()));
	
			if (!timeControlTable.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(timeControlTable.size()));
			}
	
			return dataTableResult;
		
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	
	@ResponseBody
	@GetMapping("/personal/{id}")
	public PersonalSigningDTO getPersonalSigning(@PathVariable Long id) {

		PersonalSigning personalSigning = personalSigningService.getById(id);

		return ShareMapper.mapPersonalSigningToDTO(personalSigning);
	}
	
	@ResponseBody
	@GetMapping("/user/{id}")
	public PersonalSigningDTO getUserSigning(@PathVariable Long id) {

		UserSigning userSigning = userSigningService.getById(id);

		return ShareMapper.mapUserSigningToDTO(userSigning);
	}

	@ResponseBody
	@GetMapping("/manual/{id}")
	public UserManualSigningDTO getUserManualSigning(@PathVariable Long id) {

		UserManualSigning userManualSigning = userManualSigningService.getById(id);

		return ShareMapper.mapUserManualSigningToDTO(userManualSigning);
	}

	@GetMapping("/modified-list")
	public String modifiedList(Locale locale, Model model, HttpServletRequest request) {

		try {

			ModelUtil.loadConstants(locale, model, request);

			final User user = Utiles.getUsuario();
			final List<UserDTO> usersDTO = userService.getAllUserDTOs();

			model.addAttribute("usersDTO", usersDTO);
			model.addAttribute("tableActionButtons", ModelUtil.getThumbsTableActionButtons());

			log.info("El usuario " + user.getId() + " ha accedido a la vista de fichajes modificados");

			return "modified-list";

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return "redirect:/login";
		}
	}

	@ResponseBody
	@GetMapping("/modified-list/dt")
	public DataTableResults<ModifiedSigningTableDTO> modifiedListDataTable(@RequestParam(required = false) Long userId, HttpServletRequest request) {

		try{
			final DataTableRequest<ModifiedSigning> dataTableInRQ = new DataTableRequest<>(request);
			final PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

			final User user = Utiles.getUsuario();

			List<Long> projectIds = null;

			if (user.getRole().getId() == Constants.ROLE_PL_ID) {

				projectIds = user.getBossProjects().stream().map(Project::getId).collect(Collectors.toList());

				if (projectIds.isEmpty()) {
					projectIds.add(-1L); // like empty but need in IN clause
				}
			}

			final List<ModifiedSigningTableDTO> modifiedSignings = modifiedSigningService.getModifiedSigningsDataTable(projectIds, userId, pagination);
			final Long totalRecords = modifiedSigningService.getModifiedSigningsCount(projectIds, userId);

			final DataTableResults<ModifiedSigningTableDTO> dataTableResult = new DataTableResults<>();
			dataTableResult.setDraw(dataTableInRQ.getDraw());
			dataTableResult.setData(modifiedSignings);
			dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
			dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

			if (!modifiedSignings.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
				dataTableResult.setRecordsFiltered(Integer.toString(modifiedSignings.size()));
			}

			return dataTableResult;

		} catch (InvalidUserSessionException e) {
			log.error(e);
			return null;
		}
	}

	@ResponseBody
	@PostMapping("/modified-list/approve/{id}")
	public ResponseEntity<String> approveModifiedSigning(@PathVariable Long id, Locale locale) {

		try {

			final ModifiedSigning modifiedSigning = this.modifiedSigningService.getById(id);

			final Long signingId = modifiedSigning.getSigningId();
			final String signingType = modifiedSigning.getTypeId();
			final OffsetDateTime startDate = modifiedSigning.getStartDate();
			final OffsetDateTime endDate = modifiedSigning.getEndDate();

			boolean isValidType = true;

			switch (signingType) {

				case "ds":

					long diffInMinutes = Duration.between(startDate, endDate).toMinutes();

					final DisplacementShare displacementShare = this.displacementShareService.getDisplacementShareById(signingId);
					displacementShare.setDisplacementDate(startDate.toInstant().atOffset(ZoneOffset.UTC));
					displacementShare.setManualHours(Math.toIntExact(diffInMinutes));

					displacementShareService.save(displacementShare);

					break;

				case "ps":

					final PersonalSigning personalSigning = personalSigningService.getById(signingId);
					personalSigning.setStartDate(startDate);
					personalSigning.setEndDate(endDate);

					personalSigningService.save(personalSigning);

					break;

				case "us":

					final UserSigning userSigning = userSigningService.getById(signingId);
					userSigning.setStartDate(startDate);
					userSigning.setEndDate(endDate);

					userSigningService.save(userSigning);

					break;

				case "ums":

					final UserManualSigning userManualSigning = userManualSigningService.getById(signingId);
					userManualSigning.setStartDate(startDate);
					userManualSigning.setEndDate(endDate);

					userManualSigningService.save(userManualSigning);

					break;

				default:
					isValidType = false;
					break;
			}

			if (isValidType) {
				this.modifiedSigningService.deleteById(id);
			}

			return new ResponseEntity<>(messageSource.getMessage("modified.signing.approve.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("modified.signing.approve.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/modified-list/decline/{id}")
	public ResponseEntity<String> declineModifiedSigning(@PathVariable Long id, Locale locale) {

		try {

			this.modifiedSigningService.deleteById(id);

			return new ResponseEntity<>(messageSource.getMessage("modified.signing.decline.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("modified.signing.decline.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/share/request-update")
	public ResponseEntity<String> requestUpdateUserShare(@ModelAttribute UserSigningShareDTO userSigningShareDTO, Locale locale, Model model, HttpServletRequest request) {

		try {

			final User user = Utiles.getUsuario();

			final List<User> projectManagers = new ArrayList<>();
			final OffsetDateTime ts = OffsetDateTime.now();
			final OffsetDateTime startDate = userSigningShareDTO.getStartDate();
			final OffsetDateTime endDate = userSigningShareDTO.getEndDate();

			switch (userSigningShareDTO.getShareType()) {

				case "cs":

					final ConstructionShare constructionShare = constructionShareService.getConstructionShareById(userSigningShareDTO.getShareId());
					projectManagers.addAll(constructionShare.getProject().getBossUsers());

					break;

				case "ds":

					final DisplacementShare displacementShare = displacementShareService.getDisplacementShareById(userSigningShareDTO.getShareId());
					projectManagers.addAll(displacementShare.getProject().getBossUsers());

					break;

				case "ips":

					final InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(userSigningShareDTO.getShareId());
					projectManagers.addAll(interventionPrShare.getProject().getBossUsers());

					break;

				case "is":

					final Integer inspectionId = userSigningShareDTO.getShareId().intValue();
					final InspectionDto inspection = this.inspectionService.findOrNotFound(new InspectionByIdFinderDto(inspectionId));
					final NoProgrammedShareDto noProgrammedShare = this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(inspection.getShareId()));
					final Project project = this.projectService.getProjectById(noProgrammedShare.getProjectId().longValue());

					projectManagers.addAll(project.getBossUsers());

					break;

				case "ps":

					// Need someone to notify?

					break;

				case "ws":

					final WorkShare workShare = workShareService.getWorkShareById(userSigningShareDTO.getShareId());
					projectManagers.addAll(workShare.getProject().getBossUsers());

					break;

				case "us":

					final UserSigning userSigning = userSigningService.getById(userSigningShareDTO.getShareId());
					projectManagers.addAll(userSigning.getProject().getBossUsers());

					break;

				case "ums":

					// Need someone to notify?

					break;

				default:
					break;
			}

			final ModifiedSigning modifiedSigning = new ModifiedSigning();
			modifiedSigning.setSigningId(userSigningShareDTO.getShareId());
			modifiedSigning.setTypeId(userSigningShareDTO.getShareType());
			modifiedSigning.setUser(user);
			modifiedSigning.setRequestDate(ts);
			modifiedSigning.setStartDate(startDate);
			modifiedSigning.setEndDate(endDate);

			this.modifiedSigningService.save(modifiedSigning);

			projectManagers.forEach(pm -> this.smtpService.sendSigningModifyMail(pm.getEmail(), modifiedSigning, locale));

			log.info("Solicitud de actualización del registro " + userSigningShareDTO.getShareId() + " de tipo " + userSigningShareDTO.getShareType() + " por parte del usuario " + user.getId());

			return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.request.edit.success", new Object[] { }, locale), HttpStatus.OK);

		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.request.edit.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}

	@ResponseBody
	@PostMapping("/share/update")
	public ResponseEntity<String> updateUserShare(@ModelAttribute UserSigningShareDTO userSigningShareDTO, Locale locale, Model model, HttpServletRequest request) {
		
		try {
			
			// Recover user
			User user = Utiles.getUsuario();
						
			final OffsetDateTime startDate = OffsetDateTime.from(userSigningShareDTO.getStartDate());
			final OffsetDateTime endDate = OffsetDateTime.from(userSigningShareDTO.getEndDate());
			
			switch (userSigningShareDTO.getShareType()) {

				case "cs": 
	
					ConstructionShare constructionShare = constructionShareService.getConstructionShareById(userSigningShareDTO.getShareId());
					constructionShare.setStartDate(startDate);
					constructionShare.setEndDate(endDate);
					
					constructionShareService.save(constructionShare);
					
					break;
				
				case "ds": 

					long diffInMinutes = Duration.between(startDate, endDate).toMinutes();
					
					DisplacementShare displacementShare = displacementShareService.getDisplacementShareById(userSigningShareDTO.getShareId());
					displacementShare.setDisplacementDate(startDate);
					displacementShare.setManualHours(Math.toIntExact(diffInMinutes));
					
					displacementShareService.save(displacementShare);
					
					break;
				
				case "ips": 
	
					InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(userSigningShareDTO.getShareId());
					interventionPrShare.setStartDate(startDate);
					interventionPrShare.setEndDate(endDate);
					
					interventionPrShareService.save(interventionPrShare);
					
					break;
				
				case "is": 

					final Integer inspectionId = userSigningShareDTO.getShareId().intValue();
					final InspectionDto inspection = this.inspectionService.findOrNotFound(new InspectionByIdFinderDto(inspectionId));

					final InspectionUpdateDto update = getMapper(MapIToInspectionUpdateDto.class).from(inspection);
					update.setStartDate(startDate);
					update.setEndDate(endDate);

					this.inspectionService.update(update);
					
					break;
				
				case "ps": 
	
					PersonalSigning personalSigning = personalSigningService.getById(userSigningShareDTO.getShareId());
					personalSigning.setStartDate(startDate);
					personalSigning.setEndDate(endDate);
					
					personalSigningService.save(personalSigning);
					
					break;
				
				case "ws": 
	
					WorkShare workShare = workShareService.getWorkShareById(userSigningShareDTO.getShareId());
					workShare.setStartDate(startDate);
					workShare.setEndDate(endDate);
					
					workShareService.save(workShare);
					
					break;
					
				case "us": 
					
					UserSigning userSigning = userSigningService.getById(userSigningShareDTO.getShareId());
					userSigning.setStartDate(startDate);
					userSigning.setEndDate(endDate);
					
					userSigningService.save(userSigning);
					
					break;

				case "ums":

					UserManualSigning userManualSigning = userManualSigningService.getById(userSigningShareDTO.getShareId());
					userManualSigning.setStartDate(startDate);
					userManualSigning.setEndDate(endDate);

					userManualSigningService.save(userManualSigning);

					break;
				
				default: 
					break;
				
			}
			
			// Log info
			log.info("Actualizado el registro " + userSigningShareDTO.getShareId() + " de tipo " + userSigningShareDTO.getShareType() + " por parte del usuario " + user.getId());
			
			// Return data
			return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.edit.success", new Object[] { }, locale), HttpStatus.OK);
		
		} catch (Exception e) {
			log.error(e);
			return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.edit.error", new Object[] { }, locale), HttpStatus.NOT_FOUND);
		}
	}
	
	private void getTodaySingingTimer(Long userId, Model model) {
		
		// Loading personal signing of today
		// List<PersonalSigning> todayPersonalSignings = personalSigningService.getWeekSigningsByUserId(Utiles.atStartOfDay(new Date()), Utiles.atEndOfDay(new Date()), userId);
		
		// Get the today signing minutes
		// int todayMinutes = getTotalMinutesInDay(todayPersonalSignings);
		
		TimeControlTableDTO tcDTO = timeControlService.getTimeControlDetail(Utiles.atStartOfDay(new Date()), userId);

		// Get today hours and minutes
		// String todayHoursAndMinutes = String.format("%02d:%02d", todayMinutes / 60, todayMinutes % 60);
		
		model.addAttribute("todayTimer", tcDTO.getTotalHours());
	}
	
	private List<CalendarDTO> generateSigninCalendar(Date startDate, Date endDate, Long userId, Locale locale) {
		
		List<CalendarDTO> calendarDTOs = new ArrayList<>();
		
		// Loading Displacement Share Week Signing (Manual Displacements)
		List<DisplacementShare> displacementShares = displacementShareService.getWeekSigningsByUserId(startDate, endDate, userId, 1);
		List<CalendarDTO> displacementShareCalendarDTOs = SigningMapper.mapDisplacementSharesToCalendarDTOs(displacementShares, messageSource, locale);

		// Loading Personal Week Signing
		List<PersonalSigning> personalSignings = personalSigningService.getWeekSigningsByUserId(startDate, endDate, userId);
		List<CalendarDTO> personalSigningCalendarDTOs = SigningMapper.mapPersonalSigningsToCalendarDTOs(personalSignings, messageSource, locale);

		// Loading User Week Signing
		List<UserSigning> userSignings = userSigningService.getWeekSigningsByUserId(startDate, endDate, userId);
		List<CalendarDTO> userSigningCalendarDTOs = SigningMapper.mapUserSigningsToCalendarDTOs(userSignings, messageSource, locale);

		// Loading Manual Signing
		List<UserManualSigning> userManualSignings = userManualSigningService.getWeekManualSigningsByUserId(startDate, endDate, userId);
		List<CalendarDTO> userManualSigningCalendarDTOs = SigningMapper.mapUserManualSigningsToCalendarDTOs(userManualSignings, messageSource, locale);
				
		// Add result to a simplificated list
		calendarDTOs.addAll(displacementShareCalendarDTOs);
		calendarDTOs.addAll(personalSigningCalendarDTOs);
		calendarDTOs.addAll(userSigningCalendarDTOs);
		calendarDTOs.addAll(userManualSigningCalendarDTOs);
		
		return calendarDTOs;
	}
	
	private Object[] filterByShare(long userSigningId, Integer pageNumber, Integer pageSize) {
		
		List<ShareTableDTO> shareTableDTOs = new ArrayList<>();

		List<ShareTableDTO> csShareTableDTOs = constructionShareService.getShareTableByUserSigningId(userSigningId);
		List<ShareTableDTO> dsShareTableDTOs = displacementShareService.getShareTableByUserSigningId(userSigningId);
		List<ShareTableDTO> ipsShareTableDTOs = interventionPrShareService.getShareTableByUserSigningId(userSigningId);
		List<ShareTableDTO> isShareTableDTOs = interventionSubShareService.getShareTableByUserSigningId(userSigningId);
		List<ShareTableDTO> wsShareTableDTOs = workShareService.getShareTableByUserSigningId(userSigningId);

		shareTableDTOs.addAll(csShareTableDTOs);
		shareTableDTOs.addAll(dsShareTableDTOs);
		shareTableDTOs.addAll(ipsShareTableDTOs);
		shareTableDTOs.addAll(isShareTableDTOs);
		shareTableDTOs.addAll(wsShareTableDTOs);

		Collections.sort(shareTableDTOs, 
                (o1, o2) -> o1.getStartDate().compareTo(o2.getStartDate()));
		
		int fromIndex = pageNumber * pageSize;
		
		Object[] obj = new Object[2];
		
		obj[0] = new Long(shareTableDTOs.size());
		obj[1] = shareTableDTOs.subList(fromIndex, Math.min(fromIndex + pageSize, shareTableDTOs.size()));
		
		return obj;
	}
}
