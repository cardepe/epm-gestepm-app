package com.epm.gestepm.controller;

import com.epm.gestepm.lib.file.FileUtils;
import com.epm.gestepm.model.interventionshare.service.mapper.ShareMapper;
import com.epm.gestepm.modelapi.common.scope.ShareListFilterParams;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableRequest;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.common.utils.datatables.PaginationCriteria;
import com.epm.gestepm.modelapi.common.utils.smtp.SMTPService;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionDTO;
import com.epm.gestepm.modelapi.constructionshare.dto.ConstructionShare;
import com.epm.gestepm.modelapi.constructionshare.service.ConstructionShareService;
import com.epm.gestepm.modelapi.displacement.dto.Displacement;
import com.epm.gestepm.modelapi.displacement.dto.DisplacementDTO;
import com.epm.gestepm.modelapi.displacement.service.DisplacementService;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShare;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareDTO;
import com.epm.gestepm.modelapi.displacementshare.dto.DisplacementShareTableDTO;
import com.epm.gestepm.modelapi.displacementshare.service.DisplacementShareService;
import com.epm.gestepm.modelapi.expense.dto.FileDTO;
import com.epm.gestepm.modelapi.family.dto.FamilyDTO;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrDTO;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionPrShare;
import com.epm.gestepm.modelapi.interventionprshare.dto.InterventionShareMaterial;
import com.epm.gestepm.modelapi.interventionprshare.service.InterventionPrShareService;
import com.epm.gestepm.modelapi.interventionshare.dto.*;
import com.epm.gestepm.modelapi.interventionshare.mapper.MapISMToInterventionMaterialDto;
import com.epm.gestepm.modelapi.interventionshare.mapper.MapISSToInterventionFinalDto;
import com.epm.gestepm.modelapi.interventionshare.service.InterventionShareService;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShare;
import com.epm.gestepm.modelapi.interventionsubshare.dto.InterventionSubShareTableDTO;
import com.epm.gestepm.modelapi.interventionsubshare.service.InterventionSubShareService;
import com.epm.gestepm.modelapi.interventionsubsharefile.service.InterventionSubShareFileService;
import com.epm.gestepm.modelapi.materialrequired.dto.MaterialRequiredDTO;
import com.epm.gestepm.modelapi.materialrequired.service.MaterialRequiredService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.exception.InvalidUserSessionException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import com.epm.gestepm.modelapi.workshare.dto.WorkShare;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareDTO;
import com.epm.gestepm.modelapi.workshare.dto.WorkShareTableDTO;
import com.epm.gestepm.modelapi.workshare.service.WorkShareService;
import com.epm.gestepm.modelapi.worksharefile.dto.WorkShareFile;
import com.epm.gestepm.modelapi.worksharefile.service.WorkShareFileService;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import static org.mapstruct.factory.Mappers.getMapper;

@Controller
@RequestMapping("/shares")
public class ShareController {

    private static final Log log = LogFactory.getLog(ShareController.class);

    @Value("${forum.url}")
    private String forumUrl;

    @Autowired
    private DisplacementShareService displacementShareService;

    @Autowired
    private FamilyService familyService;

    @Autowired
    private InterventionShareService interventionShareService;

    @Autowired
    private InterventionSubShareService interventionSubShareService;

    @Autowired
    private InterventionSubShareFileService interventionSubShareFileService;

    @Autowired
    private ConstructionShareService constructionShareService;

    @Autowired
    private InterventionPrShareService interventionPrShareService;

    @Autowired
    private MaterialRequiredService materialRequiredService;

    @Autowired
    private WorkShareService workShareService;

    @Autowired
    private WorkShareFileService workShareFileService;

    @Autowired
    private DisplacementService displacementService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SubFamilyService subFamilyService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSigningService userSigningService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SMTPService smtpService;

    @Resource(name = "rsShareListFilterParams")
    private ShareListFilterParams shareListFilterParams;

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
            List<ProjectListDTO> projects = projectService.getAllProjectsDTOs();

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            // Have Privileges
            boolean havePrivileges = Utiles.havePrivileges(user.getSubRole().getRol());

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
            model.addAttribute("userSigning", currentUserSigning);
            model.addAttribute("havePrivileges", havePrivileges);
            model.addAttribute("sharesType", sharesType);
            model.addAttribute("actualDate", Utiles.transformDateToString(actualDate));
            model.addAttribute("language", locale.getLanguage());
            model.addAttribute("userRole", user.getRole().getRoleName());
            model.addAttribute("usersDTO", usersDTO);
            model.addAttribute("filters", shareListFilterParams);

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

            final List<ShareTableDTO> constructionShares = constructionShareService.getShareTableByActivityCenterId(id, activityCenterId, projectId, progress);
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

    @ResponseBody
    @DeleteMapping("/intervention/no-programmed/delete/{id}")
    public ResponseEntity<String> deleteShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            if (user.getRole().getId() != Constants.ROLE_PL_ID && user.getRole().getId() != Constants.ROLE_ADMIN_ID) {
                log.error("El usuario " + user.getId() + " no tiene los permisos suficientes para eliminar el parte no programado " + id);
                return new ResponseEntity<>(messageSource.getMessage("share.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            interventionShareService.deleteById(id);

            log.info("Parte de intervención " + id + " eliminado con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("share.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("share.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @SuppressWarnings("unchecked")
    @ResponseBody
    @GetMapping("/intervention/dt")
    public String userInterventionSharesDatatable(@RequestParam(required = false) Long id, @RequestParam(required = false) String type, @RequestParam(required = false) Long project, @RequestParam(required = false) Integer progress, @RequestParam(required = false, name = "user") Long userId, HttpServletRequest request, Locale locale) {

        final DataTableRequest<InterventionShare> dataTableInRQ = new DataTableRequest<>(request);
        final PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

        shareListFilterParams.setId(id);
        shareListFilterParams.setType(type);
        shareListFilterParams.setProgress(progress);
        shareListFilterParams.setProjectId(project);
        shareListFilterParams.setUserId(userId);

        final Long totalRecords = interventionShareService.getAllShareTableCount(id, type, project, progress, userId);
        final List<ShareTableDTO> shareTableDTOs = interventionShareService.getAllShareTable((pagination.getPageNumber() / pagination.getPageSize()), pagination.getPageSize(), id, type, project, progress, userId);

        final DataTableResults<ShareTableDTO> dataTableResult = new DataTableResults<>();
        dataTableResult.setDraw(dataTableInRQ.getDraw());
        dataTableResult.setListOfDataObjects(shareTableDTOs);
        dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
        dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

        if (shareTableDTOs != null && !shareTableDTOs.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
            dataTableResult.setRecordsFiltered(Integer.toString(shareTableDTOs.size()));
        }

        return dataTableResult.getJson();
    }

    @GetMapping(value = "/intervention/files", produces = { "application/zip" })
    public void exportPdf(@RequestParam(required = false, defaultValue = "0") Integer offset, @RequestParam(required = false, defaultValue = "10") Integer limit,
                          @RequestParam(required = false) Long id, @RequestParam(required = false) String type, @RequestParam(required = false) Long project,
                          @RequestParam(required = false) Integer progress, @RequestParam(required = false, name = "user") Long userId,
                          HttpServletRequest request, HttpServletResponse response, Locale locale) {

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
    @PostMapping("/intervention/no-programmed/create")
    public ResponseEntity<?> createNoProgrammedIntervention(@ModelAttribute InterventionNoPrDTO interventionNoPrDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            if (currentUserSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
                return new ResponseEntity<>(messageSource.getMessage("signing.page.not.enable", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            // Get share project
            Project project = projectService.getProjectById(interventionNoPrDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + interventionNoPrDTO.getProjectId() + " no existe");
            }

            // Map Intervention share
            InterventionShare interventionShare = ShareMapper.mapDTOToInterventionShare(interventionNoPrDTO, user, project);
            interventionShare.setUserSigning(currentUserSigning);
            interventionShare.setState(1); // new share
            interventionShare.setLastDiagnosis(0); // not diagnosis actually
            interventionShare.setNoticeDate(new Timestamp(new Date().getTime()));

            // Fix JavaScript noticeDate by Navigator
            interventionShare.setNoticeDate(new Timestamp(new Date().getTime()));

            // Save intervention
            interventionShare = interventionShareService.save(interventionShare);

            // Log info
            log.info("Creado nuevo parte de intervención no programado " + interventionShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(interventionShare.getId(), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.no.programmed.create.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/intervention/no-programmed/detail/{id}")
    public String viewNoProgrammedShare(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Loading constants
            ModelUtil.loadConstants(locale, model, request);

            // Recover user
            User user = Utiles.getUsuario();

            // Log info
            log.info("El usuario " + user.getId() + " ha accedido a la vista de detalle del parte no programado " + id);

            InterventionShare share = interventionShareService.getInterventionShareById(id);

            if (share == null) {
                model.addAttribute("msgError", "error.share.no.programed.not.found");
                return "simple-error";
            }

            // Recover static data
            List<FamilyDTO> families = familyService.getCommonFamilyDTOsByProjectId(share.getProject().getId(), locale);

            // Recover materials required
            List<MaterialRequiredDTO> materialsRequired = materialRequiredService.getMaterialsRequiredByProjectId(share.getProject().getId());

            // Recover all users in a project
            List<UserDTO> usersTeam = userService.getUserDTOsByProjectId(share.getProject().getId());

            // Load Roles by SubFamily
            boolean hasRole = false;

            if (share.getSubFamily() != null) {

                List<RoleDTO> subRoles = subFamilyService.getSubRolsById(share.getSubFamily().getId());

                if (subRoles == null || subRoles.isEmpty()) {
                    hasRole = true;
                } else {

                    for (RoleDTO roleDTO : subRoles) {

                        if (user.getSubRole().getRol().equals(roleDTO.getName())) {
                            hasRole = true;
                            break;
                        }
                    }
                }
            }

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            // Post forum url
            String postForumUrl = forumUrl + "/viewtopic.php?f=" + share.getProject().getForumId() + "&t=" + share.getTopicId();
            String postForumTitle = share.getId() + " " + Utiles.getDateFormattedForForum(share.getNoticeDate());

            // Actual int
            if (share.getState() == 3) {
                InterventionSubShare actualIntervention = interventionSubShareService.getOpenIntervention(id);
                model.addAttribute("intervention", actualIntervention);
            }

            // Displacements
            List<DisplacementDTO> displacements = displacementService.getDisplacementDTOsByProjectId(share.getProject().getId());

            // Have Privileges
            boolean havePrivileges = Utiles.havePrivileges(user.getSubRole().getRol());

            // Add to model
            model.addAttribute("share", share);
            model.addAttribute("families", families);
            model.addAttribute("materialsRequired", materialsRequired);
            model.addAttribute("usersTeam", usersTeam);
            model.addAttribute("postForumUrl", postForumUrl);
            model.addAttribute("postForumTitle", postForumTitle);
            model.addAttribute("displacements", displacements);
            model.addAttribute("language", locale.getLanguage());
            model.addAttribute("hasRole", hasRole);
            model.addAttribute("userSigning", currentUserSigning);
            model.addAttribute("havePrivileges", havePrivileges);

            // Load Action Buttons for DataTable
            model.addAttribute("interventionTableActionButtons", ModelUtil.getTableDownloadInterventionButtons());
            model.addAttribute("tableActionButtons", ModelUtil.getTableModifyActionButtons());

            // Loading view
            return "intervention-share-detail";

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    @ResponseBody
    @GetMapping("/intervention/no-programmed/detail/{id}/dt")
    public DataTableResults<InterventionSubShareTableDTO> noProgrammedInterventionSharesDatatable(@PathVariable Long id, HttpServletRequest request, Locale locale) {

        DataTableRequest<InterventionShare> dataTableInRQ = new DataTableRequest<>(request);
        PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();

        List<InterventionSubShareTableDTO> subShareDTOs = interventionSubShareService.getInterventionSubSharesByShareDataTables(id, pagination);

        Long totalRecords = interventionSubShareService.getInterventionSubSharesCountByShareId(id);

        DataTableResults<InterventionSubShareTableDTO> dataTableResult = new DataTableResults<>();
        dataTableResult.setDraw(dataTableInRQ.getDraw());
        dataTableResult.setListOfDataObjects(subShareDTOs);
        dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
        dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

        if (subShareDTOs != null && !subShareDTOs.isEmpty() && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
            dataTableResult.setRecordsFiltered(Integer.toString(subShareDTOs.size()));
        }

        return dataTableResult;
    }

    @ResponseBody
    @PostMapping("/intervention/no-programmed/detail/{id}/displacement/create")
    public ResponseEntity<IdMsgDTO> createDisplacementFromNoProgrammedIntervention(@PathVariable Long id, @ModelAttribute DisplacementShareDTO displacementShareDTO, Locale locale, Model model, HttpServletRequest request) {

        IdMsgDTO response = new IdMsgDTO();

        try {

            // Recover user
            User user = Utiles.getUsuario();

            InterventionShare share = interventionShareService.getInterventionShareById(id);

            // Get displacement
            Displacement displacement = displacementService.getDisplacementById(displacementShareDTO.getActivityCenter());

            // AutDisp
            displacementShareDTO.setManualDisplacement(0);

            // Map Intervention share stepper
            DisplacementShare displacementShare = ShareMapper.mapDTOToDisplacementShare(displacementShareDTO, user, share.getProject(), displacement);
            displacementShare.setOriginalDate(new Timestamp(new Date().getTime()));

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

    @PostMapping("/intervention/no-programmed/detail/{id}/init")
    public String initNoProgrammedIntervention(@PathVariable Long id, @ModelAttribute InterventionNoPrDTO interventionNoPrDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            if (currentUserSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
                model.addAttribute("msgError", messageSource.getMessage("signing.page.not.enable", new Object[]{}, locale));
                return "simple-error";
            }

            User secondTechnicalUser = null;

            if (interventionNoPrDTO.getSecondTechnical() != null) {

                secondTechnicalUser = userService.getUserById(interventionNoPrDTO.getSecondTechnical());

                if (secondTechnicalUser == null) {
                    throw new Exception("El usuario" + interventionNoPrDTO.getSecondTechnical() + " no existe");
                }
            }

            InterventionShare share = interventionShareService.getInterventionShareById(id);
            share.setState(3); // inProgress

            if (interventionNoPrDTO.getAction() == 2) {
                share.setLastDiagnosis(1);
            } else if (interventionNoPrDTO.getAction() == 1) {
                share.setLastDiagnosis(2);
            } else {
                share.setLastDiagnosis(0);
            }

            interventionShareService.save(share);

            Long interventionsCount = interventionShareService.getInterventionsCount(id);

            InterventionSubShare subShare = new InterventionSubShare();
            subShare.setUserSigning(currentUserSigning);
            subShare.setOrderId(interventionsCount + 1);
            subShare.setAction(interventionNoPrDTO.getAction());
            subShare.setInterventionShare(share);
            subShare.setFirstTechnical(user);
            subShare.setSecondTechnical(secondTechnicalUser);
            subShare.setStartDate(OffsetDateTime.now());

            if (!StringUtils.isNullOrEmpty(interventionNoPrDTO.getMaterialsRequired())) {
                subShare.setMaterials(interventionNoPrDTO.getMaterialsRequired());
            }

            if (!StringUtils.isNullOrEmpty(interventionNoPrDTO.getMrSignature())) {
                subShare.setMrSignature(interventionNoPrDTO.getMrSignature());
            }

            subShare.setDisplacementShareId(interventionNoPrDTO.getDispShareId());
            interventionSubShareService.save(subShare);

//			// Send Emails
//			smtpService.sendOpenInterventionSubShareMail(user.getEmail(), subShare, locale);
//			
//			if (share.getProject().getResponsables() != null && !share.getProject().getResponsables().isEmpty()) {
//				
//				for (User responsable : share.getProject().getResponsables()) {
//					smtpService.sendOpenInterventionSubShareMail(responsable.getEmail(), subShare, locale);
//				}
//			}
//			
//			if ("on".equals(interventionNoPrDTO.getClientNotif()) && share.getProject().getCustomer() != null) {
//				smtpService.sendOpenInterventionSubShareMail(share.getProject().getCustomer().getMainEmail(), subShare, locale);
//			}

            return "redirect:/shares/intervention/no-programmed/detail/" + id;

        } catch (Exception e) {
            log.error(e);

            return "error";
        }
    }

    @PostMapping("/intervention/no-programmed/detail/{id}/update")
    public String updateNoProgrammedInterventionDescription(@PathVariable Long id, @ModelAttribute InterventionNoPrDTO interventionNoPrDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            InterventionShare share = interventionShareService.getInterventionShareById(id);

            if (share == null) {
                throw new Exception("El parte de intervencion no programado " + id + " no existe");
            }

            if (share.getFamily() != null) {
                throw new Exception("Ya se ha completado la descripcion de la averia del parte de intervencion no programado " + id);
            }

            share = interventionShareService.update(share, interventionNoPrDTO, user, request.getLocalAddr(), locale);

            // Send Emails
            smtpService.sendOpenInterventionShareMail(user.getEmail(), share, locale);

            if (share.getProject().getResponsables() != null && !share.getProject().getResponsables().isEmpty()) {

                for (User responsable : share.getProject().getResponsables()) {
                    smtpService.sendOpenInterventionShareMail(responsable.getEmail(), share, locale);
                }
            }

            if ("on".equals(interventionNoPrDTO.getClientNotif()) && share.getProject().getCustomer() != null) {
                smtpService.sendOpenInterventionShareMail(share.getProject().getCustomer().getMainEmail(), share, locale);
            }

            log.info("Parte de intervencion no programado " + id + " actualizado con exito por parte del usuario " + user.getId());

            return "redirect:/shares/intervention/no-programmed/detail/" + id;

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";

        } catch (Exception e) {
            log.error(e);
            model.addAttribute("msgError", e.toString());
            return "simple-error";
        }
    }

    @ResponseBody
    @PostMapping("/intervention/no-programmed/detail/{id}/end")
    public ResponseEntity<String> updateInterventionNoProgrammedDescription(@PathVariable Long id, @ModelAttribute InterventionNoPrDTO interventionNoPrDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            InterventionSubShare actualIntervention = interventionSubShareService.getOpenIntervention(id);

            if (actualIntervention == null) {
                log.error("No hay ninguna intervencion abierta para el parte " + id);
                return new ResponseEntity<>(messageSource.getMessage("shares.intervention.detail.close.intervention.empty.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            // Update Intervention
            interventionSubShareService.updateInterventionInfo(actualIntervention, interventionNoPrDTO);

            // Return data
            return new ResponseEntity<>(actualIntervention.getId().toString(), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.intervention.detail.close.intervention.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/intervention/no-programmed/detail/{id}/files")
    public String uploadFilesNoProgrammedInterventionDescription(@PathVariable Long id, @ModelAttribute InterventionNoPrDTO interventionNoPrDTO, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            InterventionSubShare actualIntervention = interventionSubShareService.getOpenIntervention(id);

            if (actualIntervention == null) {
                throw new Exception(messageSource.getMessage("shares.intervention.detail.close.intervention.empty.error", new Object[]{}, locale));
            }

            // Update MaterialsFile
            if (interventionNoPrDTO.getMaterialsFile() != null && !interventionNoPrDTO.getMaterialsFile().isEmpty()) {
                String fileName = interventionNoPrDTO.getMaterialsFile().getOriginalFilename();
                String ext = FilenameUtils.getExtension(fileName);
                byte[] materialsFileData = FileUtils.compressBytes(interventionNoPrDTO.getMaterialsFile().getBytes());

                actualIntervention.setMaterialsFile(materialsFileData);
                actualIntervention.setMaterialsFileExt(ext);
            }

            // Upload Files
            if (interventionNoPrDTO.getFiles() != null && !interventionNoPrDTO.getFiles().isEmpty()) {
                interventionSubShareFileService.uploadFiles(actualIntervention, interventionNoPrDTO.getFiles());
            }

            // Close intervention
            actualIntervention = interventionSubShareService.closeIntervention(actualIntervention, interventionNoPrDTO, user, request.getLocalAddr(), locale);

            byte[] pdfGenerated = interventionSubShareService.generateInterventionSharePdf(actualIntervention, locale);

            // Send Emails
            smtpService.sendCloseInterventionSubShareMail(user.getEmail(), actualIntervention, pdfGenerated, locale);

            final String customerMail = actualIntervention.getInterventionShare().getProject().getCustomer() == null
                    ? "null"
                    : actualIntervention.getInterventionShare().getProject().getCustomer().getMainEmail();

            log.debug("Opciones de mail de cliente al finalizar una intervención en un parte no programado - checkbox: "
                    + interventionNoPrDTO.getClientNotif() + ", customer: " + customerMail);

            if (actualIntervention.getInterventionShare().getProject().getResponsables() != null && !actualIntervention.getInterventionShare().getProject().getResponsables().isEmpty()) {

                for (User responsable : actualIntervention.getInterventionShare().getProject().getResponsables()) {
                    smtpService.sendCloseInterventionSubShareMail(responsable.getEmail(), actualIntervention, pdfGenerated, locale);
                }
            }

            if ("on".equals(interventionNoPrDTO.getClientNotif()) && actualIntervention.getInterventionShare().getProject().getCustomer() != null) {
                smtpService.sendCloseInterventionSubShareMail(actualIntervention.getInterventionShare().getProject().getCustomer().getMainEmail(), actualIntervention, pdfGenerated, locale);
            }

            // Return data
            return "redirect:/shares/intervention/no-programmed/detail/" + id;

        } catch (Exception e) {
            log.error("An error ocurred when upload intervention images: ", e);
            model.addAttribute("msgError", e.toString());
            return "simple-error";
        }
    }

    @GetMapping(value = "/intervention/no-programmed/detail/{shareId}/{interventionId}/materials/file")
    public HttpEntity<ByteArrayResource> getMaterialsFile(@PathVariable Long shareId, @PathVariable Long interventionId, Locale locale) {

        log.info("Exportando el fichero de materiales adjunto del parte de intervención " + shareId + "/" + interventionId);

        InterventionSubShare subShare = interventionSubShareService.getByShareAndOrder(shareId, interventionId);

        if (subShare == null) {
            log.error("No existe la intervención con id " + shareId + "/" + interventionId);
            return null;
        }

        if (subShare.getMaterialsFile() == null) {
            log.error("El parte " + shareId + "/" + interventionId + " no contiene un fichero adjunto de materiales");
            return null;
        }

        byte[] fileBytes = FileUtils.decompressBytes(subShare.getMaterialsFile());

        if (fileBytes == null) {
            log.error("Error al generar el fichero de materiales adjunto del parte de intervención " + shareId + "/" + interventionId);
            return null;
        }

        String fileName = "materials_file_" + interventionId + "_" + shareId + "." + subShare.getMaterialsFileExt();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        return new HttpEntity<>(new ByteArrayResource(fileBytes), header);
    }

    @ResponseBody
    @DeleteMapping("/intervention/no-programmed/detail/{id}/delete")
    public ResponseEntity<String> deleteInterventionNoProgrammed(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            if (user.getRole().getId() != Constants.ROLE_PL_ID && user.getRole().getId() != Constants.ROLE_ADMIN_ID) {
                log.error("El usuario " + user.getId() + " no tiene los permisos suficientes para eliminar el parte no programado " + id);
                return new ResponseEntity<>(messageSource.getMessage("share.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            interventionSubShareService.deleteById(id);

            log.info("Intervención " + id + " eliminada con éxito por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("share.delete.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(messageSource.getMessage("share.delete.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/intervention/no-programmed/detail/{id}/close")
    public String closeNoProgrammedInterventionDescription(@PathVariable Long id, Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Close InterventionShare
            InterventionShare share = interventionShareService.getInterventionShareById(id);
            share.setState(4); // end
            share.setEndDate(new Timestamp(new Date().getTime())); // end date
            interventionShareService.save(share);

            // Send Emails
            smtpService.sendCloseInterventionShareMail(user.getEmail(), share, locale);

            if (share.getProject().getResponsables() != null && !share.getProject().getResponsables().isEmpty()) {

                for (User responsable : share.getProject().getResponsables()) {
                    smtpService.sendCloseInterventionShareMail(responsable.getEmail(), share, locale);
                }
            }

            log.info("Parte de intervencion no programado " + id + " cerrado con exito por parte del usuario " + user.getId());

            return "redirect:/shares/intervention/no-programmed/detail/" + id;

        } catch (Exception e) {
            log.error(e);
            model.addAttribute("msgError", e.toString());
            return "simple-error";
        }
    }

    @ResponseBody
    @PutMapping("/intervention/no-programmed/{id}")
    public ResponseEntity<String> updateNoProgrammedShare(@PathVariable Long id, @ModelAttribute InterventionDTO interventionDTO,
                                                          Locale locale, Model model, HttpServletRequest request) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get
            InterventionSubShare interventionShare = interventionSubShareService.getById(interventionDTO.getId());

            interventionShare.setStartDate(interventionDTO.getStartDate().atOffset(ZoneOffset.UTC));
            interventionShare.setEndDate(interventionDTO.getEndDate().atOffset(ZoneOffset.UTC));

            // Save intervention
            interventionShare = interventionSubShareService.save(interventionShare);

            // Log info
            log.info("Actualizado parte de intervencion no programado " + interventionShare.getId() + " por parte del usuario " + user.getId());

            // Return data
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.success", new Object[]{}, locale), HttpStatus.OK);

        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(messageSource.getMessage("shares.displacement.update.error", new Object[]{}, locale), HttpStatus.NOT_FOUND);
        }
    }

    @ResponseBody
    @GetMapping("/intervention/no-programmed/{id}")
    public InterventionDTO getNoProgrammedShare(@PathVariable Long id) {

        InterventionSubShare interventionShare = interventionSubShareService.getById(id);

        return ShareMapper.mapInterventionShareToDTO(interventionShare);
    }

    @GetMapping(value = "/intervention/no-programmed/detail/{shareId}/{interventionId}/pdf", produces = {"application/pdf"})
    public HttpEntity<byte[]> exportPdf(@PathVariable Long shareId, @PathVariable Long interventionId, Locale locale) {

        log.info("Exportando el pdf del parte de intervención " + shareId + "/" + interventionId);

        InterventionSubShare subShare = interventionSubShareService.getByShareAndOrder(shareId, interventionId);

        if (subShare == null) {
            log.error("No existe la intervención con id " + shareId + "/" + interventionId);
            return null;
        }

        byte[] pdf = interventionSubShareService.generateInterventionSharePdf(subShare, locale);

        if (pdf == null) {
            log.error("Error al generar el fichero pdf de la interención " + shareId + "/" + interventionId);
            return null;
        }

        String shareIdStr = subShare.getInterventionShare().getId() + "/" + subShare.getOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{shareIdStr.replace("/", "-"), Utiles.getDateFormatted(subShare.getStartDate())}, locale) + ".pdf");

        return new HttpEntity<>(pdf, headers);
    }

    @GetMapping(value = "/intervention/no-programmed/detail/{shareId}/{interventionId}/materials/pdf", produces = {"application/pdf"})
    public HttpEntity<byte[]> exportMaterialsPdf(@PathVariable Long shareId, @PathVariable Long interventionId, Locale locale) {

        log.info("Exportando el pdf de materiales del parte de intervención " + shareId + "/" + interventionId);

        InterventionSubShare subShare = interventionSubShareService.getByShareAndOrder(shareId, interventionId);

        if (subShare == null) {
            log.error("No existe la intervención con id " + shareId + "/" + interventionId);
            return null;
        }

        byte[] pdf = interventionSubShareService.generateInterventionShareMaterialsPdf(subShare, locale);

        if (pdf == null) {
            log.error("Error al generar el fichero pdf de materiales de la interención " + shareId + "/" + interventionId);
            return null;
        }

        String shareIdStr = subShare.getInterventionShare().getId() + "/" + subShare.getOrderId();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", messageSource.getMessage("shares.no.programmed.materials.pdf.name", new Object[]{shareIdStr.replace("/", "-"), Utiles.getDateFormatted(subShare.getStartDate())}, locale) + ".pdf");

        return new HttpEntity<>(pdf, headers);
    }

    @GetMapping(value = "/intervention/no-programmed/detail/{shareId}/{interventionId}/materials")
    public HttpEntity<ByteArrayResource> exportMaterialsFile(@PathVariable Long shareId, @PathVariable Long interventionId, Locale locale) {

        log.info("Exportando el fichero de materiales del parte de intervención " + shareId + "/" + interventionId);

        InterventionSubShare subShare = interventionSubShareService.getByShareAndOrder(shareId, interventionId);

        if (subShare == null) {
            log.error("No existe la intervención con id " + shareId + "/" + interventionId);
            return null;
        }

        String fileName = "intervention_materials_" + shareId + "_" + interventionId + "." + subShare.getMaterialsFileExt();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + fileName + "\"");

        return new HttpEntity<>(new ByteArrayResource(FileUtils.decompressBytes(subShare.getMaterialsFile())), header);
    }

    @ResponseBody
    @PostMapping("/intervention/programmed/create")
    public ResponseEntity<String> createProgrammedIntervention(@ModelAttribute InterventionPrDTO interventionPrDTO, Locale locale, Model model, HttpServletRequest request) {
        try {

            // Recover user
            User user = Utiles.getUsuario();

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            if (currentUserSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
                return new ResponseEntity<>(messageSource.getMessage("signing.page.not.enable", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            // Get share project
            Project project = projectService.getProjectById(interventionPrDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + interventionPrDTO.getProjectId() + " no existe");
            }

            // Map Intervention share
            InterventionPrShare interventionPrShare = ShareMapper.mapDTOToInterventionPrShare(interventionPrDTO, user, project, interventionPrDTO.getDispShareId());
            interventionPrShare.setUserSigning(currentUserSigning);
            interventionPrShare.setStartDate(new Timestamp(new Date().getTime()));

            // Save intervention
            interventionPrShare = interventionPrShareService.save(interventionPrShare);

//			// Send Emails
//			smtpService.sendOpenProgrammedShareMail(user.getEmail(), interventionPrShare, locale);
//			
//			if (interventionPrShare.getProject().getResponsables() != null && !interventionPrShare.getProject().getResponsables().isEmpty()) {
//				
//				for (User responsable : interventionPrShare.getProject().getResponsables()) {
//					smtpService.sendOpenProgrammedShareMail(responsable.getEmail(), interventionPrShare, locale);
//				}
//			}
//			
//			if (Boolean.TRUE.equals(interventionPrDTO.getClientNotif()) && interventionPrShare.getProject().getCustomer() != null) {
//				smtpService.sendOpenProgrammedShareMail(interventionPrShare.getProject().getCustomer().getMainEmail(), interventionPrShare, locale);
//			}

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
            interventionPrShare.setEndDate(Timestamp.valueOf(interventionPrDTO.getEndDate()));

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

            interventionPrShare.setStartDate(Timestamp.valueOf(interventionPrDTO.getStartDate()));
            interventionPrShare.setEndDate(Timestamp.valueOf(interventionPrDTO.getEndDate()));
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

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            if (currentUserSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
                response.setMsg(messageSource.getMessage("signing.page.not.enable", new Object[]{}, locale));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Get share project
            Project project = projectService.getProjectById(constructionDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + constructionDTO.getProjectId() + " no existe");
            }

            // Map Intervention share stepper
            ConstructionShare constructionShare = ShareMapper.mapDTOToConstructionShare(constructionDTO, user, project, constructionDTO.getDispShareId());
            constructionShare.setUserSigning(currentUserSigning);
            constructionShare.setStartDate(new Timestamp(new Date().getTime()));

            // Save intervention
            constructionShare = constructionShareService.save(constructionShare);

//			// Send Emails
//			smtpService.sendOpenConstructionShareMail(user.getEmail(), constructionShare, locale);
//			
//			if (constructionShare.getProject().getResponsables() != null && !constructionShare.getProject().getResponsables().isEmpty()) {
//				
//				for (User responsable : constructionShare.getProject().getResponsables()) {
//					smtpService.sendOpenConstructionShareMail(responsable.getEmail(), constructionShare, locale);
//				}
//			}

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
            ConstructionShare constructionShare = constructionShareService.getConstructionShareById(constructionDTO.getId());
            constructionShare.setEndDate(Timestamp.valueOf(constructionDTO.getEndDate()));
            constructionShare.setObservations(constructionDTO.getObservations());
            constructionShare.setSignatureOp(constructionDTO.getSignatureOp());

            // Save intervention
            constructionShare = constructionShareService.create(constructionShare, constructionDTO.getFiles());

            // Log info
            log.info("Parte de intervención " + constructionShare.getId() + " finalizado por parte del usuario " + user.getId());

            byte[] pdfGenerated = constructionShareService.generateConstructionSharePdf(constructionShare, locale);

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
            ConstructionShare constructionShare = constructionShareService.getConstructionShareById(constructionDTO.getId());

            constructionShare.setStartDate(Timestamp.valueOf(constructionDTO.getStartDate()));
            constructionShare.setEndDate(Timestamp.valueOf(constructionDTO.getEndDate()));
            constructionShare.setObservations(constructionDTO.getObservations());

            // Save intervention
            constructionShare = constructionShareService.save(constructionShare);

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

        ConstructionShare constructionShare = constructionShareService.getConstructionShareById(id);

        return ShareMapper.mapConstructionShareToDTO(constructionShare);
    }


    @ResponseBody
    @DeleteMapping("/intervention/construction/delete/{id}")
    public ResponseEntity<String> deleteConstructionShare(@PathVariable Long id, Locale locale) {

        try {

            // Recover user
            User user = Utiles.getUsuario();

            constructionShareService.deleteById(id);

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

        ConstructionShare share = constructionShareService.getConstructionShareById(id);

        if (share == null) {
            log.error("No existe el parte de construccion con id " + id);
            return null;
        }

        byte[] pdf = constructionShareService.generateConstructionSharePdf(share, locale);

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
            displacementShare.setOriginalDate(new Timestamp(new Date().getTime()));

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
    public String userDisplacementSharesDatatable(HttpServletRequest request, Locale locale) {

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
            dataTableResult.setListOfDataObjects(displacementShares);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (displacementShares != null && !displacementShares.isEmpty()
                    && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(displacementShares.size()));
            }

            return dataTableResult.getJson();

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
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
            List<ProjectListDTO> projects = projectService.getProjectsDTOByUserId(user.getId());

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

            // Get Current UserSigning
            UserSigning currentUserSigning = userSigningService.getByUserIdAndEndDate(user.getId(), null);

            if (currentUserSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
                return new ResponseEntity<>(messageSource.getMessage("signing.page.not.enable", new Object[]{}, locale), HttpStatus.NOT_FOUND);
            }

            // Get share project
            Project project = projectService.getProjectById(workShareDTO.getProjectId());

            if (project == null) {
                throw new Exception("El proyecto " + workShareDTO.getProjectId() + " no existe");
            }

            // Map Intervention share stepper
            WorkShare workShare = ShareMapper.mapDTOToWorkShare(workShareDTO, user, project);
            workShare.setUserSigning(currentUserSigning);
            workShare.setStartDate(new Timestamp(new Date().getTime()));

            // Save intervention
            workShare = workShareService.save(workShare);

//			// Send Emails
//			smtpService.sendOpenWorkShareMail(user.getEmail(), workShare, locale);
//			
//			if (workShare.getProject().getResponsables() != null && !workShare.getProject().getResponsables().isEmpty()) {
//				
//				for (User responsable : workShare.getProject().getResponsables()) {
//					smtpService.sendOpenWorkShareMail(responsable.getEmail(), workShare, locale);
//				}
//			}

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
            workShare.setEndDate(Timestamp.valueOf(workShareDTO.getEndDate()));
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

            workShare.setStartDate(Timestamp.valueOf(workShareDTO.getStartDate()));
            workShare.setEndDate(Timestamp.valueOf(workShareDTO.getEndDate()));
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
    public String userWorkSharesDatatable(HttpServletRequest request, Locale locale) {

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
            dataTableResult.setListOfDataObjects(workShares);
            dataTableResult.setRecordsTotal(String.valueOf(totalRecords));
            dataTableResult.setRecordsFiltered(Long.toString(totalRecords));

            if (workShares != null && !workShares.isEmpty()
                    && !dataTableInRQ.getPaginationRequest().isFilterByEmpty()) {
                dataTableResult.setRecordsFiltered(Integer.toString(workShares.size()));
            }

            return dataTableResult.getJson();

        } catch (InvalidUserSessionException e) {
            log.error(e);
            return "redirect:/login";
        }
    }

    @ResponseBody
    @GetMapping("/no-programmed/{shareId}/intervention/{interventionId}")
    public InterventionFinalDto getIntervention(@PathVariable Long shareId, @PathVariable Long interventionId) {

        final InterventionSubShare interventionSubShare = interventionSubShareService.getById(interventionId);

        return getMapper(MapISSToInterventionFinalDto.class).from(interventionSubShare);
    }

    @ResponseBody
    @PutMapping("/no-programmed/{shareId}/intervention/{interventionId}")
    public InterventionFinalDto updateIntervention(@ModelAttribute InterventionUpdateFinalDto updateDto,
                                                   @PathVariable Long shareId, @PathVariable Long interventionId) throws Exception {
        updateDto.setId(interventionId);

        final InterventionSubShare interventionSubShare = interventionSubShareService.update(updateDto);
        return getMapper(MapISSToInterventionFinalDto.class).from(interventionSubShare);
    }

    @ResponseBody
    @GetMapping("/no-programmed/{shareId}/intervention/{interventionId}/materials")
    public List<InterventionMaterialDto> getInterventionMaterials(@PathVariable Long shareId, @PathVariable Long interventionId) {

        final List<InterventionShareMaterial> list = interventionSubShareService.listInterventionShareMaterial(interventionId);

        return getMapper(MapISMToInterventionMaterialDto.class).from(list);
    }

    private List<PdfFileDTO> getConstructionSharesPdf(final List<ShareTableDTO> shareTableDTOs, final Locale locale) {

        final List<PdfFileDTO> pdfs = new ArrayList<>();

        final List<ShareTableDTO> constructionShares = shareTableDTOs.stream().filter(f -> "cs".equals(f.getShareType()) && f.getEndDate() != null).collect(Collectors.toList());

        for (ShareTableDTO constructionShare : constructionShares) {

            final Long id = Long.parseLong(constructionShare.getId().split("_")[0]);

            final ConstructionShare share = constructionShareService.getConstructionShareById(id);

            if (share == null) {
                log.error("No existe el parte de construccion con id " + id);
                continue;
            }

            final byte[] pdf = constructionShareService.generateConstructionSharePdf(share, locale);

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

            final Long id = Long.parseLong(noProgrammedShare.getId().split("_")[0]);

            final InterventionShare share = interventionShareService.getInterventionShareById(id);

            List<InterventionSubShare> interventionSubShares = share.getInterventionSubShares();

            interventionSubShares = interventionSubShares.stream().filter(f -> f.getEndDate() != null).collect(Collectors.toList());

            byte[] zipContent = null;

            if (!interventionSubShares.isEmpty()) {

                final ByteArrayOutputStream baos = new ByteArrayOutputStream();

                try (ZipOutputStream zos = new ZipOutputStream(baos)) {

                    for (InterventionSubShare subShare : interventionSubShares) {

                        final Long subShareId = subShare.getId();

                        final byte[] pdf = interventionSubShareService.generateInterventionSharePdf(subShare, locale);

                        if (pdf == null) {
                            log.error("Error al generar el fichero pdf de la intervención " + id + "/" + subShareId);
                            continue;
                        }

                        final String shareIdStr = subShare.getInterventionShare().getId() + "-" + subShare.getOrderId();

                        final String fileName = messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{shareIdStr, Utiles.getDateFormatted(subShare.getStartDate())}, locale) + ".pdf";

                        final ZipEntry zipEntr = new ZipEntry(fileName);

                        zos.putNextEntry(zipEntr);
                        zos.write(pdf);
                        zos.closeEntry();

                        final byte[] pdfMaterials = interventionSubShareService.generateInterventionShareMaterialsPdf(subShare, locale);

                        if (pdfMaterials == null) {
                            log.error("Error al generar el fichero pdf de materiales de la intervención " + id + "/" + subShareId);
                            continue;
                        }

                        final String fileNameMaterials = messageSource.getMessage("shares.no.programmed.materials.pdf.name", new Object[]{ shareIdStr, Utiles.getDateFormatted(subShare.getStartDate()) }, locale) + ".pdf";

                        final ZipEntry zipEntrMat = new ZipEntry(fileNameMaterials);

                        zos.putNextEntry(zipEntrMat);
                        zos.write(pdfMaterials);
                        zos.closeEntry();
                    }

                    zos.close();
                    zipContent = baos.toByteArray();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String fileName = messageSource.getMessage("shares.no.programmed.pdf.name", new Object[]{share.getId().toString(), Utiles.getDateFormatted(share.getNoticeDate())}, locale) + ".zip";

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
