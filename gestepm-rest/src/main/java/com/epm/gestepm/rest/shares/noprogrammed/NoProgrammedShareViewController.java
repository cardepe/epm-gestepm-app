package com.epm.gestepm.rest.shares.noprogrammed;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.family.dto.FamilyDTO;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionService;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.dto.UserDTO;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@EnableExecutionLog(layerMarker = VIEW)
public class NoProgrammedShareViewController {

    @Value("${forum.url}")
    private String forumUrl;

    private final FamilyService familyService;

    private final InspectionService inspectionService;

    private final NoProgrammedShareService noProgrammedShareService;

    private final ProjectService projectService;

    private final SubFamilyService subFamilyService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public NoProgrammedShareViewController(FamilyService familyService, InspectionService inspectionService, NoProgrammedShareService noProgrammedShareService, ProjectService projectService, SubFamilyService subFamilyService, UserService userService, UserSigningService userSigningService) {
        this.familyService = familyService;
        this.inspectionService = inspectionService;
        this.noProgrammedShareService = noProgrammedShareService;
        this.projectService = projectService;
        this.subFamilyService = subFamilyService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/no-programmed/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewNoProgrammedShareDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final NoProgrammedShareDto share = this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(id));

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(share.getProjectId());
        final Project project = Optional.ofNullable(this.projectService.getProjectById(share.getProjectId().longValue()))
                .orElseThrow(projectNotFound);

        final InspectionFilterDto filterDto = new InspectionFilterDto();
        filterDto.setShareId(id);
        filterDto.setOrder("DESC");
        filterDto.setOrderBy("id");

        final ActionEnumDto lastAction = this.inspectionService.list(filterDto, 0L, 1L).get(0)
                .map(InspectionDto::getAction)
                .orElse(ActionEnumDto.FOLLOWING);

        final List<FamilyDTO> families = familyService.getCommonFamilyDTOsByProjectId(share.getProjectId().longValue(), locale);
        final List<UserDTO> usersTeam = userService.getUserDTOsByProjectId(share.getProjectId().longValue());

        boolean hasRole = false;
        boolean hasSigning = false;

        if (share.getFamilyId() != null && share.getSubFamilyId() != null) {
            final List<RoleDTO> subRoles = this.subFamilyService.getSubRolsById(share.getSubFamilyId().longValue());
            final UserSigning userSigning = this.userSigningService.getByUserIdAndEndDate(user.getId(), null);
            final String userLevel = user.getSubRole().getRol();

            hasRole = subRoles.isEmpty()
                    || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));
            hasSigning = userSigning != null || Utiles.havePrivileges(userLevel);
        }

        model.addAttribute("families", families);
        model.addAttribute("usersTeam", usersTeam);
        model.addAttribute("nextAction", ActionEnumDto.getNextAction(lastAction));
        model.addAttribute("hasRole", hasRole);
        model.addAttribute("hasSigning", hasSigning);

        if (project.getForumId() != null && share.getTopicId() != null) {
            model.addAttribute("forumUrl", forumUrl + "/viewtopic.php?f=" + project.getForumId() + "&t=" + share.getTopicId());
        }

        return "no-programmed-share-detail";
    }
}
