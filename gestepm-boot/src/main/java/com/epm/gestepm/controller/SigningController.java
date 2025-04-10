package com.epm.gestepm.controller;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigning;
import com.epm.gestepm.modelapi.modifiedsigning.dto.ModifiedSigningTableDTO;
import com.epm.gestepm.modelapi.modifiedsigning.service.ModifiedSigningService;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigning;
import com.epm.gestepm.modelapi.personalsigning.dto.PersonalSigningDTO;
import com.epm.gestepm.modelapi.personalsigning.service.PersonalSigningService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.shares.ShareDecorator;
import com.epm.gestepm.modelapi.timecontrolold.dto.TimeControlTableDTO;
import com.epm.gestepm.modelapi.timecontrolold.service.TimeControlOldService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.dto.UserTableDTO;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigning;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningDTO;
import com.epm.gestepm.modelapi.usermanualsigning.dto.UserManualSigningTableDTO;
import com.epm.gestepm.modelapi.usermanualsigning.service.UserManualSigningService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigningShareDTO;
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
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/signing")
public class SigningController {

    private static final Log log = LogFactory.getLog(SigningController.class);

    @Value("${gestepm.first-year}")
    private int firstYear;

    @Value("#{'${gestepm.mails.rrhh}'.split(',')}")
    private List<String> rrhhMails;

    @Autowired
    private ShareDecorator shareDecorator;

    @Autowired
    private ManualSigningTypeService manualSigningTypeService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ModifiedSigningService modifiedSigningService;

    @Autowired
    private PersonalSigningService personalSigningService;

    @Autowired
    private SMTPService smtpService;

    @Autowired
    private TimeControlOldService timeControlOldService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserManualSigningService userManualSigningService;

    @GetMapping(value = "/project/{projectId}/weekly", produces = {"application/zip"})
    public void exportWeeklyPdf(@PathVariable Integer projectId, HttpServletResponse response, Locale locale) {

        try {

            log.info("Exportando partes de proyecto " + projectId + " de forma semanal");

            final ZoneOffset zoneOffset = ZoneOffset.UTC;
            final LocalDateTime startOfWeek = LocalDateTime.now(zoneOffset)
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    .withHour(0).withMinute(0).withSecond(0);
            final LocalDateTime endOfWeek = LocalDateTime.now(zoneOffset)
                    .with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                    .withHour(23).withMinute(59).withSecond(59);

            final List<PdfFileDTO> pdfs = this.shareDecorator.exportShares(projectId, startOfWeek, endOfWeek);

            final String fileName = messageSource.getMessage("shares.zip.name", new Object[]{Utiles.getDateFormatted(new Date())}, locale) + ".zip";

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
    public String manualSigning(@RequestParam(value = "user", required = false) Long userId, Locale locale, Model model, HttpServletRequest request) {

        try {

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

            if (user.getId() != userManualSigningDTO.getUserId()) {
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

            return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.error", new Object[]{}, locale), HttpStatus.NOT_MODIFIED);
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
            return new ResponseEntity<>(messageSource.getMessage("signing.manual.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("signing.manual.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
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

            if (user.getId() != personalSigningDTO.getUserId()) {
                signingUser = this.userService.getUserById(personalSigningDTO.getUserId());
            }

            log.info("El usuario " + user.getId() + " ha creado un fichaje personal al usuario " + signingUser.getId());

            final PersonalSigning personalSigning = new PersonalSigning();
            personalSigning.setUser(signingUser);
            personalSigning.setStartDate(personalSigningDTO.getStartDate());
            personalSigning.setEndDate(personalSigningDTO.getEndDate());

            this.personalSigningService.save(personalSigning);

            return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("signing.manual.create.error", new Object[]{}, locale), HttpStatus.NOT_MODIFIED);
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

            List<TimeControlTableDTO> timeControlTable = timeControlOldService.getTimeControlTableDTOByDateAndUser(selectedMonth, selectedYear, userDetail.getId(), userDetail.getActivityCenter().getId(), locale);

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
    public String timeControlView(@RequestParam String date,
                                  @PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

        try {
            final Date dateFormatted = Utiles.transformSimpleStringToDate(date);
            final LocalDateTime startDate = dateFormatted.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // Loading constants
            ModelUtil.loadConstants(locale, model, request);

            // Recover user
            User user = Utiles.getUsuario();

            // Log info
            log.info("El usuario " + user.getId() + " ha accedido a la vista detalle de Control Horario " + id);

            // Get TimeControl info
            TimeControlTableDTO timeControlDTO = timeControlOldService.getTimeControlDetail(startDate, id);

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
            model.addAttribute("actualDate", startDate);
            model.addAttribute("timeControlDTO", timeControlDTO);

            // Loading view
            return "time-control-detail";

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    @ResponseBody
    @GetMapping("/personal/{id}")
    public PersonalSigningDTO getPersonalSigning(@PathVariable Long id) {

        PersonalSigning personalSigning = personalSigningService.getById(id);

        return ShareMapper.mapPersonalSigningToDTO(personalSigning);
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

        try {
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
            final LocalDateTime startDate = modifiedSigning.getStartDate();
            final LocalDateTime endDate = modifiedSigning.getEndDate();

            boolean isValidType = true;

            switch (signingType) {

                case "MANUAL_SIGNINGS":

                    final UserManualSigning userManualSigning = userManualSigningService.getById(signingId);
                    userManualSigning.setStartDate(startDate);
                    userManualSigning.setEndDate(endDate);

                    userManualSigningService.save(userManualSigning);

                    break;

                case "PERSONAL_SIGNINGS":

                    final PersonalSigning personalSigning = personalSigningService.getById(signingId);
                    personalSigning.setStartDate(startDate);
                    personalSigning.setEndDate(endDate);

                    personalSigningService.save(personalSigning);

                    break;

                default:
                    isValidType = false;
                    break;
            }

            if (isValidType) {
                this.modifiedSigningService.deleteById(id);
            }

            return new ResponseEntity<>(messageSource.getMessage("modified.signing.approve.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("modified.signing.approve.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/modified-list/decline/{id}")
    public ResponseEntity<String> declineModifiedSigning(@PathVariable Long id, Locale locale) {

        try {

            this.modifiedSigningService.deleteById(id);

            return new ResponseEntity<>(messageSource.getMessage("modified.signing.decline.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("modified.signing.decline.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/share/request-update")
    public ResponseEntity<String> requestUpdateUserShare(@ModelAttribute UserSigningShareDTO userSigningShareDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            final User user = Utiles.getUsuario();

            final ModifiedSigning modifiedSigning = new ModifiedSigning();
            modifiedSigning.setSigningId(userSigningShareDTO.getShareId());
            modifiedSigning.setTypeId(userSigningShareDTO.getShareType());
            modifiedSigning.setUser(user);
            modifiedSigning.setRequestDate(LocalDateTime.now());
            modifiedSigning.setStartDate(userSigningShareDTO.getStartDate());
            modifiedSigning.setEndDate(userSigningShareDTO.getEndDate());

            this.modifiedSigningService.save(modifiedSigning);

            log.info("Solicitud de actualización del registro " + userSigningShareDTO.getShareId() + " de tipo " + userSigningShareDTO.getShareType() + " por parte del usuario " + user.getId());

            return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.request.edit.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.request.edit.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/share/update")
    public ResponseEntity<String> updateUserShare(@ModelAttribute UserSigningShareDTO userSigningShareDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            final LocalDateTime startDate = userSigningShareDTO.getStartDate();
            final LocalDateTime endDate = userSigningShareDTO.getEndDate();

            switch (userSigningShareDTO.getShareType()) {

                case "PERSONAL_SIGNINGS":

                    PersonalSigning personalSigning = personalSigningService.getById(userSigningShareDTO.getShareId());
                    personalSigning.setStartDate(startDate);
                    personalSigning.setEndDate(endDate);

                    personalSigningService.save(personalSigning);

                    break;

                case "MANUAL_SIGNINGS":

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
            return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.edit.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("user.detail.signing.edit.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    private void getTodaySingingTimer(Long userId, Model model) {

        final LocalDateTime startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        final TimeControlTableDTO tcDTO = timeControlOldService.getTimeControlDetail(startDate, userId);

        model.addAttribute("todayTimer", tcDTO.getTotalHours());
    }

}
