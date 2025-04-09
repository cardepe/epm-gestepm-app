package com.epm.gestepm.controller;

import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionDTO;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareOldService;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.service.DisplacementService;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareDTO;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.displacementshare.service.DisplacementShareService;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionExportService;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.IdMsgDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.InterventionShare;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.PdfFileDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.deprecated.interventionshare.service.InterventionShareService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import com.epm.gestepm.modelapi.worksharefile.service.WorkShareFileService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@Controller
@RequestMapping("/shares")
public class ShareController {

    private static final Log log = LogFactory.getLog(ShareController.class);

    @Autowired
    private DisplacementShareService displacementShareService;

    @Autowired
    private InspectionService inspectionService;

    @Autowired
    private InspectionExportService inspectionExportService;

    @Autowired
    private InterventionShareService interventionShareService;

    @Autowired
    private ConstructionShareOldService constructionShareOldService;

    @Autowired
    private InterventionPrShareService interventionPrShareService;

    @Autowired
    private WorkShareService workShareService;

    @Autowired
    private WorkShareFileService workShareFileService;

    @Autowired
    private DisplacementService displacementService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SMTPService smtpService;

    @GetMapping("/intervention")
    public String interventionsView(Locale locale, Model model, HttpServletRequest request) {

        try {

            // Loading constants
            ModelUtil.loadConstants(locale, model, request);

            // Recover user
            User user = Utiles.getUsuario();

            // Log info
            log.info("El usuario " + user.getId() + " ha accedido a la vista de Partes de Intervención");

            // Recover user projects
            List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(false);

            // Recover users
            List<UserDTO> usersDTO = null;

            if (user.getRole().getId() == Constants.ROLE_ADMIN_ID || user.getRole().getId() == Constants.ROLE_RRHH_ID) {

                usersDTO = userService.getAllUserDTOs();

            } else if (user.getRole().getId() == Constants.ROLE_PL_ID) {

                usersDTO = new ArrayList<>();

                for (ProjectListDTO projectListDTO : projects) {
                    List<UserDTO> userDTOsByProjectId = userService.getUserDTOsByProjectId(projectListDTO.getId());
                    usersDTO.addAll(userDTOsByProjectId);
                }

                usersDTO = usersDTO.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingLong(UserDTO::getUserId))), ArrayList::new));
            }

            Date actualDate = Calendar.getInstance().getTime();

            String[] sharesType = new String[4];
            sharesType[0] = "cs";
            sharesType[1] = "is";
            sharesType[2] = "ips";
            sharesType[3] = "ws";

            model.addAttribute("projects", projects);
            model.addAttribute("sharesType", sharesType);
            model.addAttribute("actualDate", Utiles.transformDateToString(actualDate));
            model.addAttribute("language", locale.getLanguage());
            model.addAttribute("userRole", user.getRole().getRoleName());
            model.addAttribute("usersDTO", usersDTO);

            // Load Action Buttons for DataTable
            model.addAttribute("tableActionButtons", ModelUtil.getInterventionActionButtons());

            // Loading view
            return "intervention-share";

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    private Object[] filterByShare(Long activityCenterId, Integer pageNumber, Integer pageSize, Long id, String type, Long projectId, Integer progress) {

        final List<ShareTableDTO> shareTableDTOs = new ArrayList<>();
        final boolean isTypeFiltered = !StringUtils.isNullOrEmpty(type);

        if (!isTypeFiltered || "cs".equals(type)) {

            final List<ShareTableDTO> constructionShares = constructionShareOldService.getShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
            shareTableDTOs.addAll(constructionShares);
        }

        if (!isTypeFiltered || "is".equals(type)) {

            final List<ShareTableDTO> noProgrammedInterventionShares = interventionShareService.getShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
            shareTableDTOs.addAll(noProgrammedInterventionShares);
        }

        if (!isTypeFiltered || "ips".equals(type)) {

            final List<ShareTableDTO> programmedInterventionShares = interventionPrShareService.getShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
            shareTableDTOs.addAll(programmedInterventionShares);
        }

        if (!isTypeFiltered || "ws".equals(type)) {
            final List<ShareTableDTO> workShares = workShareService.getShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
            shareTableDTOs.addAll(workShares);
        }

        shareTableDTOs.sort((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate())); // DESC Order

        // FIXME: it should return a class, not an object.

        final int fromIndex = pageNumber * pageSize;
        final Object[] obj = new Object[2];

        obj[0] = (long) shareTableDTOs.size();
        obj[1] = shareTableDTOs.subList(fromIndex, Math.min(fromIndex + pageSize, shareTableDTOs.size()));

        return obj;
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @GetMapping("/intervention/dt")
    public DataTableResults<ShareTableDTO> userInterventionSharesDatatable(@RequestParam(required = false) Long id, @RequestParam(required = false) String type, @RequestParam(required = false) Long project, @RequestParam(required = false) Integer progress, @RequestParam(required = false, name = "user") Long userId, HttpServletRequest request, Locale locale) {

        final DataTableRequest<InterventionShare> dataTableInRQ = new DataTableRequest<>(request);
        final PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

        final Long totalRecords = interventionShareService.getAllShareTableCount(id, type, project, progress, userId);
        final List<ShareTableDTO> shareTableDTOs = interventionShareService.getAllShareTable((pagination.getPageNumber() / pagination.getPageSize()), pagination.getPageSize(), id, type, project, progress, userId);

        final DataTableResults<ShareTableDTO> dataTableResult = new DataTableResults<>();
        dataTableResult.setDraw(dataTableInRQ.getDraw());
        dataTableResult.setData(shareTableDTOs);
        dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
        dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

        if (shareTableDTOs != null && !shareTableDTOs.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
            dataTableResult.setRecordsFiltered(Integer.toString(shareTableDTOs.size()));
        }

        return dataTableResult;
    }

    @GetMapping(value = "/intervention/files", produces = { "application/zip" })
    public void exportPdf(@RequestParam(required = false, defaultValue = "0") Integer offset, @RequestParam(required = false, defaultValue = "10") Integer limit,
                          @RequestParam(required = false) Long id, @RequestParam(required = false) String type, @RequestParam(required = false) Long project,
                          @RequestParam(required = false) Integer progress, @RequestParam(required = false, name = "user") Long userId,
                          HttpServletResponse response, Locale locale) {

        try {

            final User user = Utiles.getUsuario();

            log.info("Exportando conjunto de partes por parte del usuario " + user.getId());

            List<ShareTableDTO> shareTableDTOs = null;

            final Long userIdFilter = userId != null ? userId : user.getId();

            if (user.getRole().getId() == Constants.ROLE_TECHNICAL_SUPERVISOR_ID || user.getRole().getId() == Constants.ROLE_ADMIN_ID) {

                shareTableDTOs = interventionShareService.getAllShareTable((offset / limit), limit, id, type, project, progress, userId);

            } else {

                final Object[] obj = filterByShare(userIdFilter, (offset / limit), limit, id, type, project, progress);

                shareTableDTOs = (List<ShareTableDTO>) obj[1];
            }

            final List<PdfFileDTO> pdfs = new ArrayList<>();

            final List<PdfFileDTO> constructionSharesPdf = this.getConstructionSharesPdf(shareTableDTOs, locale);
            final List<PdfFileDTO> programmedSharesPdf = this.getProgrammedSharesPdf(shareTableDTOs, locale);
            final List<PdfFileDTO> noProgrammedSharesZip = this.getNoProgrammedSharesPdf(shareTableDTOs, locale);
            final List<PdfFileDTO> workSharesPdf = this.getWorkSharesPdf(shareTableDTOs, locale);

            pdfs.addAll(constructionSharesPdf);
            pdfs.addAll(programmedSharesPdf);
            pdfs.addAll(noProgrammedSharesZip);
            pdfs.addAll(workSharesPdf);

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

        } catch (final InvalidUserSessionException | FileNotFoundException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        }
    }

    @ResponseBody
    @PostMapping("/intervention/programmed/create")
    public ResponseEntity<String> createProgrammedIntervention(@ModelAttribute InterventionPrDTO interventionPrDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get share project
            Project project = projectService.getProjectById(interventionPrDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + interventionPrDTO.getProjectId() + " no existe");
            }

            // Map Intervention share
            InterventionPrShare interventionPrShare = ShareMapper.mapDTOToInterventionPrShare(interventionPrDTO, user, project, interventionPrDTO.getDispShareId());
            interventionPrShare.setStartDate(LocalDateTime.now());

            // Save intervention
            interventionPrShare = interventionPrShareService.save(interventionPrShare);

            // Log info
            log.info("Creado nuevo parte de intervención " + interventionPrShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.create.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.create.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/intervention/programmed/update")
    public ResponseEntity<String> updateProgrammedIntervention(@ModelAttribute InterventionPrDTO interventionPrDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Load Programmed Intervention
            InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(interventionPrDTO.getId());
            interventionPrShare.setObservations(interventionPrDTO.getObservations());
            interventionPrShare.setSignature(interventionPrDTO.getSignature());
            interventionPrShare.setSignatureOp(interventionPrDTO.getSignatureOp());

            // Save intervention
            interventionPrShare = interventionPrShareService.create(interventionPrShare, interventionPrDTO.getFiles());

            // Log info
            log.info("Actualizada la informacion del parte de intervención " + interventionPrShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/intervention/programmed/finish")
    public ResponseEntity<String> finishProgrammedIntervention(@ModelAttribute InterventionPrDTO interventionPrDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Map Intervention share stepper
            InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(interventionPrDTO.getId());
            interventionPrShare.setEndDate(interventionPrDTO.getEndDate());

            if (!StringUtils.isNullOrEmpty(interventionPrDTO.getObservations())) {
                interventionPrShare.setObservations(interventionPrDTO.getObservations());
            }

            interventionPrShare.setSignature(interventionPrDTO.getSignature());
            interventionPrShare.setSignatureOp(interventionPrDTO.getSignatureOp());

            if (interventionPrDTO.getSecondTechnical() != null) {
                User secondTechnical = this.userService.getUserById(interventionPrDTO.getSecondTechnical());
                interventionPrShare.setSecondTechnical(secondTechnical);
            }

            // Save intervention
            interventionPrShare = interventionPrShareService.create(interventionPrShare, interventionPrDTO.getFiles());

            // Log info
            log.info("Creado nuevo parte de intervención " + interventionPrShare.getId() + " por parte del usuario " + user.getId());

            byte[] pdfGenerated = interventionPrShareService.generateInterventionSharePdf(interventionPrShare, locale);

            // Send Emails
            smtpService.sendCloseProgrammedShareMail(user.getEmail(), interventionPrShare, pdfGenerated, locale);

            if (interventionPrShare.getProject().getResponsables() != null && !interventionPrShare.getProject().getResponsables().isEmpty()) {

                for (User responsable : interventionPrShare.getProject().getResponsables()) {
                    smtpService.sendCloseProgrammedShareMail(responsable.getEmail(), interventionPrShare, pdfGenerated, locale);
                }
            }

            if (Boolean.TRUE.equals(interventionPrDTO.getClientNotif()) && interventionPrShare.getProject().getCustomer() != null) {
                smtpService.sendCloseProgrammedShareMail(interventionPrShare.getProject().getCustomer().getMainEmail(), interventionPrShare, pdfGenerated, locale);
            }

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.finish.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.finish.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PutMapping("/programmed/{id}")
    public ResponseEntity<String> updateProgrammedShare(@PathVariable Long id, @ModelAttribute InterventionPrDTO interventionPrDTO,
                                                        Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get
            InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(interventionPrDTO.getId());

            interventionPrShare.setStartDate(interventionPrDTO.getStartDate());
            interventionPrShare.setEndDate(interventionPrDTO.getEndDate());
            interventionPrShare.setObservations(interventionPrDTO.getObservations());

            // Save intervention
            interventionPrShare = interventionPrShareService.save(interventionPrShare);

            // Log info
            log.info("Actualizado parte de intervencion programado " + interventionPrShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/intervention/programmed/{id}")
    public InterventionPrDTO getProgrammedShare(@PathVariable Long id) {

        InterventionPrShare interventionPrShare = interventionPrShareService.getInterventionPrShareById(id);

        return ShareMapper.mapInterventionPrShareToDTO(interventionPrShare);
    }

    @ResponseBody
    @DeleteMapping("/intervention/programmed/delete/{id}")
    public ResponseEntity<String> deleteProgrammedShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            interventionPrShareService.deleteById(id);

            log.info("Parte de mantenimiento programdo " + id + " eliminado con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("shares.programmed.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/intervention/programmed/{id}/pdf", produces = {"application/pdf"})
    public HttpEntity<byte[]> exportProgrammedPdf(@PathVariable Long id, Locale locale) {

        log.info("Exportando el pdf del parte de intervención programado " + id);

        InterventionPrShare share = interventionPrShareService.getInterventionPrShareById(id);

        if (share == null) {
            log.error("No existe el parte programado con id " + id);
            return null;
        }

        byte[] pdf = interventionPrShareService.generateInterventionSharePdf(share, locale);

        if (pdf == null) {
            log.error("Error al generar el fichero pdf del parte programado " + id);
            return null;
        }

        String fileName = messageSource.getMessage("shares.programmed.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName + ".pdf");

        return new HttpEntity<>(pdf, headers);
    }

    @ResponseBody
    @PostMapping("/intervention/construction/create")
    public ResponseEntity<IdMsgDTO> createConstructionIntervention(@ModelAttribute ConstructionDTO constructionDTO, Locale locale, Model model, HttpServletRequest request) {

        IdMsgDTO response = new IdMsgDTO();

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get share project
            Project project = projectService.getProjectById(constructionDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + constructionDTO.getProjectId() + " no existe");
            }

            // Map Intervention share stepper
            ConstructionShare constructionShare = ShareMapper.mapDTOToConstructionShare(constructionDTO, user, project);
            constructionShare.setStartDate(LocalDateTime.now());

            // Save intervention
            constructionShare = constructionShareOldService.save(constructionShare);

            // Log info
            log.info("Creado nuevo parte de intervención " + constructionShare.getId() + " por parte del usuario " + user.getId());

            response.setId(constructionShare.getId());
            response.setMsg(messageSource.getMessage("shares.construction.create.success", new Object[]{}, locale));

            // Return data
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            response.setMsg(messageSource.getMessage("shares.construction.create.error", new Object[]{}, locale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/intervention/construction/finish")
    public ResponseEntity<String> finishConstructionIntervention(@ModelAttribute ConstructionDTO constructionDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Map Intervention share stepper
            ConstructionShare constructionShare = constructionShareOldService.getConstructionShareById(constructionDTO.getId());
            constructionShare.setEndDate(LocalDateTime.now());
            constructionShare.setObservations(constructionDTO.getObservations());
            constructionShare.setSignatureOp(constructionDTO.getSignatureOp());

            // Save intervention
            constructionShare = constructionShareOldService.create(constructionShare, constructionDTO.getFiles());

            // Log info
            log.info("Parte de intervención " + constructionShare.getId() + " finalizado por parte del usuario " + user.getId());

            byte[] pdfGenerated = constructionShareOldService.generateConstructionSharePdf(constructionShare, locale);

            // Send Emails
            smtpService.sendCloseConstructionShareMail(user.getEmail(), constructionShare, pdfGenerated, locale);

            if (constructionShare.getProject().getResponsables() != null && !constructionShare.getProject().getResponsables().isEmpty()) {

                for (User responsable : constructionShare.getProject().getResponsables()) {
                    smtpService.sendCloseConstructionShareMail(responsable.getEmail(), constructionShare, pdfGenerated, locale);
                }
            }

            if (Boolean.TRUE.equals(constructionDTO.getClientNotif()) && constructionShare.getProject().getCustomer() != null) {
                smtpService.sendCloseConstructionShareMail(constructionShare.getProject().getCustomer().getMainEmail(), constructionShare, pdfGenerated, locale);
            }

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.finish.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.finish.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PutMapping("/construction/{id}")
    public ResponseEntity<String> updateConstructionShare(@PathVariable Long id, @ModelAttribute ConstructionDTO constructionDTO,
                                                          Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get
            ConstructionShare constructionShare = constructionShareOldService.getConstructionShareById(constructionDTO.getId());

            constructionShare.setStartDate(constructionDTO.getStartDate());
            constructionShare.setEndDate(constructionDTO.getEndDate());
            constructionShare.setObservations(constructionDTO.getObservations());

            // Save intervention
            constructionShare = constructionShareOldService.save(constructionShare);

            // Log info
            log.info("Actualizado parte de construccion " + constructionShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/intervention/construction/{id}")
    public ConstructionDTO getConstructionShare(@PathVariable Long id) {

        ConstructionShare constructionShare = constructionShareOldService.getConstructionShareById(id);

        return ShareMapper.mapConstructionShareToDTO(constructionShare);
    }


    @ResponseBody
    @DeleteMapping("/intervention/construction/delete/{id}")
    public ResponseEntity<String> deleteConstructionShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            constructionShareOldService.deleteById(id);

            log.info("Parte de construcción " + id + " eliminado con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("shares.construction.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/intervention/construction/{id}/pdf", produces = {"application/pdf"})
    public HttpEntity<byte[]> exportConstructionPdf(@PathVariable Long id, Locale locale) {

        log.info("Exportando el pdf del parte de construccion " + id);

        ConstructionShare share = constructionShareOldService.getConstructionShareById(id);

        if (share == null) {
            log.error("No existe el parte de construccion con id " + id);
            return null;
        }

        byte[] pdf = constructionShareOldService.generateConstructionSharePdf(share, locale);

        if (pdf == null) {
            log.error("Error al generar el fichero pdf del parte de construccion " + id);
            return null;
        }

        String fileName = messageSource.getMessage("shares.construction.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName + ".pdf");

        return new HttpEntity<>(pdf, headers);
    }

    @GetMapping("/displacement")
    public String displacementsView(Locale locale, Model model, HttpServletRequest request) {

        try {

            // Loading constants
            ModelUtil.loadConstants(locale, model, request);

            // Recover user
            User user = Utiles.getUsuario();

            // Log info
            log.info("El usuario " + user.getId() + " ha accedido a la vista de Partes de Desplazamiento");

            // Recover user projects
            final List<Project> projects = this.projectService.findDisplacementProjects();
            model.addAttribute("projects", projects);

            // Load Action Buttons for DataTable
            model.addAttribute("tableActionButtons", ModelUtil.getViewActionButton());

            // Loading view
            return "displacement-share";

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    @ResponseBody
    @PostMapping("/displacement")
    public ResponseEntity<IdMsgDTO> createDisplacement(@ModelAttribute DisplacementShareDTO displacementShareDTO,
                                                       @RequestParam("project") String projectId, Locale locale, Model model, HttpServletRequest request) {

        IdMsgDTO response = new IdMsgDTO();

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get displacement
            Displacement displacement = displacementService.getDisplacementById(displacementShareDTO.getActivityCenter());

            Project project = projectService.getProjectById(Long.valueOf(projectId));

            // Map Intervention share stepper
            DisplacementShare displacementShare = ShareMapper.mapDTOToDisplacementShare(displacementShareDTO, user, project, displacement);
            displacementShare.setOriginalDate(LocalDateTime.now());

            // Save intervention
            displacementShare = displacementShareService.save(displacementShare);

            // Log info
            log.info("Creado nuevo parte de desplazamiento " + displacementShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            response.setId(displacementShare.getId());
            response.setMsg(messageSource.getMessage("shares.displacement.create.success", new Object[]{}, locale));
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error al crear un parte de desplazamiento: %s", e);
            response.setMsg(messageSource.getMessage("shares.displacement.create.error", new Object[]{}, locale));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/displacement/{id}")
    public DisplacementShareDTO getDisplacementShare(@PathVariable Long id) {

        DisplacementShare displacementShare = displacementShareService.getDisplacementShareById(id);

        return ShareMapper.mapDisplacementShareToDTO(displacementShare);
    }

    @ResponseBody
    @PutMapping("/displacement/{id}")
    public ResponseEntity<String> updateDisplacement(@PathVariable Long id, @ModelAttribute DisplacementShareDTO displacementShareDTO,
                                                     Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get share project
            Project project = projectService.getProjectById(Long.valueOf(displacementShareDTO.getProjectId()));

            // Get displacement
            Displacement displacement = displacementService.getDisplacementById(displacementShareDTO.getActivityCenter());

            // Map Intervention share stepper
            DisplacementShare displacementShare = ShareMapper.mapDTOToDisplacementShare(displacementShareDTO, user, project, displacement);
            displacementShare.setId(id);

            // Save intervention
            displacementShare = displacementShareService.save(displacementShare);

            // Log info
            log.info("Actualizado parte de desplazamiento " + displacementShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @DeleteMapping("/displacement/{id}")
    public ResponseEntity<String> deleteDisplacementShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            displacementShareService.deleteById(id);

            log.info("Parte de desplazamiento " + id + " eliminado con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/displacement/dt")
    public DataTableResults<DisplacementShareTableDTO> userDisplacementSharesDatatable(HttpServletRequest request, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            DataTableRequest<DisplacementShare> dataTableInRQ = new DataTableRequest<>(request);
            PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

            List<DisplacementShareTableDTO> displacementShares = displacementShareService
                    .getDisplacementSharesByUserDataTables(user.getId(), pagination);

            Long totalRecords = displacementShareService.getDisplacementSharesCountByUser(user.getId());

            DataTableResults<DisplacementShareTableDTO> dataTableResult = new DataTableResults<>();
            dataTableResult.setDraw(dataTableInRQ.getDraw());
            dataTableResult.setData(displacementShares);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (displacementShares != null && !displacementShares.isEmpty()
                    && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(displacementShares.size()));
            }

            return dataTableResult;

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return null;
        }
    }

    @GetMapping("/work")
    public String worksView(Locale locale, Model model, HttpServletRequest request) {

        try {

            // Loading constants
            ModelUtil.loadConstants(locale, model, request);

            // Recover user
            User user = Utiles.getUsuario();

            // Log info
            log.info("El usuario " + user.getId() + " ha accedido a la vista de Partes de Trabajo");

            // Recover user projects
            List<ProjectListDTO> projects = projectService.getProjectsByUser(user);

            model.addAttribute("projects", projects);

            // Load Action Buttons for DataTable
            model.addAttribute("tableActionButtons", ModelUtil.getViewActionButton());

            // Loading view
            return "work-share";

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    @ResponseBody
    @PostMapping("/work/create")
    public ResponseEntity<String> createWork(@ModelAttribute WorkShareDTO workShareDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get share project
            Project project = projectService.getProjectById(workShareDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + workShareDTO.getProjectId() + " no existe");
            }

            // Map Intervention share stepper
            WorkShare workShare = ShareMapper.mapDTOToWorkShare(workShareDTO, user, project);
            workShare.setStartDate(LocalDateTime.now());

            // Save intervention
            workShare = workShareService.save(workShare);

            // Log info
            log.info("Creado nuevo parte de trabajo " + workShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.work.create.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.work.create.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @PostMapping("/work/finish")
    public ResponseEntity<String> finishWork(@ModelAttribute WorkShareDTO workShareDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Map Intervention share stepper
            WorkShare workShare = workShareService.getWorkShareById(workShareDTO.getId());
            workShare.setEndDate(workShareDTO.getEndDate());
            workShare.setObservations(workShareDTO.getObservations());
            workShare.setSignatureOp(workShareDTO.getSignatureOp());

            // Save intervention
            workShare = workShareService.create(workShare, workShareDTO.getFiles());

            // Log info
            log.info("Creado nuevo parte de trabajo " + workShare.getId() + " por parte del usuario " + user.getId());

            byte[] pdfGenerated = workShareService.generateWorkSharePdf(workShare, locale);

            // Send Emails
            smtpService.sendCloseWorkShareMail(user.getEmail(), workShare, pdfGenerated, locale);

            if (workShare.getProject().getResponsables() != null && !workShare.getProject().getResponsables().isEmpty()) {

                for (User responsable : workShare.getProject().getResponsables()) {
                    smtpService.sendCloseWorkShareMail(responsable.getEmail(), workShare, pdfGenerated, locale);
                }
            }

            if (Boolean.TRUE.equals(workShareDTO.getClientNotif()) && workShare.getProject().getCustomer() != null) {
                smtpService.sendCloseWorkShareMail(workShare.getProject().getCustomer().getMainEmail(), workShare, pdfGenerated, locale);
            }

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.work.finish.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.work.finish.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/work/{id}")
    public WorkShareDTO getWorkShare(@PathVariable Long id) {

        WorkShare workShare = workShareService.getWorkShareById(id);

        return ShareMapper.mapWorkShareToDTO(workShare);
    }

    @ResponseBody
    @GetMapping("/work/files/{id}")
    public List<FileDTO> getWorkFilesShare(@PathVariable Long id) {

        List<WorkShareFile> workShareFiles = workShareFileService.getWorkShareFileByWorkShareId(id);

        return ShareMapper.mapWorkShareFileToFileDTO(workShareFiles);
    }

    @ResponseBody
    @PutMapping("/work/{id}")
    public ResponseEntity<String> updateWork(@PathVariable Long id, @ModelAttribute WorkShareDTO workShareDTO,
                                             Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Map Intervention share stepper
            WorkShare workShare = workShareService.getWorkShareById(workShareDTO.getId());

            workShare.setStartDate(workShareDTO.getStartDate());
            workShare.setEndDate(workShareDTO.getEndDate());
            workShare.setObservations(workShareDTO.getObservations());

            // Save intervention
            workShare = workShareService.create(workShare, workShareDTO.getFiles());

            // Log info
            log.info("Actualizado parte de trabajo " + workShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.work.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.work.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @DeleteMapping("/work/{id}")
    public ResponseEntity<String> deleteWorkShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            workShareService.deleteById(id);

            log.info("Parte de trabajo " + id + " eliminado con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.work.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("shares.work.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/work/{id}/pdf", produces = {"application/pdf"})
    public HttpEntity<byte[]> exportWorkPdf(@PathVariable Long id, Locale locale) {

        log.info("Exportando el pdf del parte de trabajo " + id);

        WorkShare share = workShareService.getWorkShareById(id);

        if (share == null) {
            log.error("No existe el parte de construccion con id " + id);
            return null;
        }

        byte[] pdf = workShareService.generateWorkSharePdf(share, locale);

        if (pdf == null) {
            log.error("Error al generar el fichero pdf del parte de construccion " + id);
            return null;
        }

        String fileName = messageSource.getMessage("shares.work.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName + ".pdf");

        return new HttpEntity<>(pdf, headers);
    }

    @ResponseBody
    @GetMapping("/work/dt")
    public DataTableResults<WorkShareTableDTO> userWorkSharesDatatable(HttpServletRequest request, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            DataTableRequest<WorkShare> dataTableInRQ = new DataTableRequest<>(request);
            PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

            List<WorkShareTableDTO> workShares = workShareService
                    .getWorkSharesByUserDataTables(user.getId(), pagination);

            Long totalRecords = workShareService.getWorkSharesCountByUser(user.getId());

            DataTableResults<WorkShareTableDTO> dataTableResult = new DataTableResults<>();
            dataTableResult.setDraw(dataTableInRQ.getDraw());
            dataTableResult.setData(workShares);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (workShares != null && !workShares.isEmpty()
                    && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(workShares.size()));
            }

            return dataTableResult;

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return null;
        }
    }

    private List<PdfFileDTO> getConstructionSharesPdf(final List<ShareTableDTO> shareTableDTOs, final Locale locale) {

        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final List<ShareTableDTO> constructionShares = shareTableDTOs.stream().filter(f -> "cs".equals(f.getShareType()) && f.getEndDate() != null).collect(Collectors.toList());

        for (ShareTableDTO constructionShare : constructionShares) {

            final Long id = Long.parseLong(constructionShare.getId().split("_")[0]);

            final ConstructionShare share = constructionShareOldService.getConstructionShareById(id);

            if (share == null) {
                log.error("No existe el parte de construccion con id " + id);
                continue;
            }

            final byte[] pdf = constructionShareOldService.generateConstructionSharePdf(share, locale);

            if (pdf == null) {
                log.error("Error al generar el fichero pdf del parte de construccion " + id);
                continue;
            }

            final String fileName = messageSource.getMessage("shares.construction.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        }

        return pdfs;
    }

    private List<PdfFileDTO> getProgrammedSharesPdf(final List<ShareTableDTO> shareTableDTOs, final Locale locale) {

        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final List<ShareTableDTO> programmedShares = shareTableDTOs.stream().filter(f -> "ips".equals(f.getShareType()) && f.getEndDate() != null).collect(Collectors.toList());

        for (ShareTableDTO programmedShare : programmedShares) {

            final Long id = Long.parseLong(programmedShare.getId().split("_")[0]);

            final InterventionPrShare share = interventionPrShareService.getInterventionPrShareById(id);

            if (share == null) {
                log.error("No existe el parte programado con id " + id);
                continue;
            }

            final byte[] pdf = interventionPrShareService.generateInterventionSharePdf(share, locale);

            if (pdf == null) {
                log.error("Error al generar el fichero pdf del parte programado " + id);
                continue;
            }

            final String fileName = messageSource.getMessage("shares.programmed.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        }

        return pdfs;
    }

    private List<PdfFileDTO> getNoProgrammedSharesPdf(final List<ShareTableDTO> shareTableDTOs, final Locale locale) {

        final List<PdfFileDTO> zips = new ArrayList<>();

        final List<ShareTableDTO> noProgrammedShares = shareTableDTOs.stream().filter(f -> "is".equals(f.getShareType())).collect(Collectors.toList());

        for (ShareTableDTO noProgrammedShare : noProgrammedShares) {

            final Integer noProgrammedShareId = Integer.parseInt(noProgrammedShare.getId().split("_")[0]);

            final InspectionFilterDto filter = new InspectionFilterDto();
            filter.setShareId(noProgrammedShareId);

            final List<InspectionDto> inspections = this.inspectionService.list(filter)
                    .stream().filter(inspection -> inspection.getEndDate() != null)
                    .collect(Collectors.toList());

            byte[] zipContent = null;

            if (CollectionUtils.isNotEmpty(inspections)) {

                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                try (ZipOutputStream zos = new ZipOutputStream(baos)) {

                    for (InspectionDto inspection : inspections) {

                        final byte[] pdf = this.inspectionExportService.generate(inspection);

                        final String fileName = messageSource.getMessage("shares.no.programmed.pdf.name", new Object[] {
                                inspection.getShareId().toString(),
                                inspection.getId().toString(),
                                Utiles.transform(inspection.getStartDate(), "yyyyMMdd")
                        }, locale) + ".pdf";

                        final ZipEntry zipEntr = new ZipEntry(fileName);

                        zos.putNextEntry(zipEntr);
                        zos.write(pdf);
                        zos.closeEntry();

                        final byte[] pdfMaterials = this.inspectionExportService.generateMaterials(inspection);

                        final String fileNameMaterials = messageSource.getMessage("shares.no.programmed.materials.pdf.name", new Object[] {
                                inspection.getShareId().toString(),
                                inspection.getId().toString(),
                                Utiles.transform(inspection.getStartDate(), "yyyyMMdd")
                        }, locale) + ".pdf";

                        final ZipEntry zipEntryMat = new ZipEntry(fileNameMaterials);

                        zos.putNextEntry(zipEntryMat);
                        zos.write(pdfMaterials);
                        zos.closeEntry();
                    }

                    zos.close();
                    zipContent = baos.toByteArray();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String fileName = messageSource.getMessage("shares.no.programmed.zip.name", new Object[] {
                        noProgrammedShareId.toString(),
                        Utiles.transform(noProgrammedShare.getStartDate(), "yyyyMMdd")
                }, locale) + ".zip";

                final PdfFileDTO pdfFileDTO = new PdfFileDTO();
                pdfFileDTO.setFileName(fileName);
                pdfFileDTO.setDocumentBytes(zipContent);

                zips.add(pdfFileDTO);
            }
        }

        return zips;
    }

    private List<PdfFileDTO> getWorkSharesPdf(final List<ShareTableDTO> shareTableDTOs, final Locale locale) {

        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final List<ShareTableDTO> workShares = shareTableDTOs.stream().filter(f -> "ws".equals(f.getShareType()) && f.getEndDate() != null).collect(Collectors.toList());

        for (ShareTableDTO workShare : workShares) {

            final Long id = Long.parseLong(workShare.getId().split("_")[0]);

            final WorkShare share = workShareService.getWorkShareById(id);

            if (share == null) {
                log.error("No existe el parte de trabajo con id " + id);
                continue;
            }

            final byte[] pdf = workShareService.generateWorkSharePdf(share, locale);

            if (pdf == null) {
                log.error("Error al generar el fichero pdf del parte de trabajo " + id);
                continue;
            }

            final String fileName = messageSource.getMessage("shares.work.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getStartDate())}, locale) + ".pdf";

            final PdfFileDTO pdfFileDTO = new PdfFileDTO();
            pdfFileDTO.setDocumentBytes(pdf);
            pdfFileDTO.setFileName(fileName);

            pdfs.add(pdfFileDTO);
        }

        return pdfs;
    }

}
