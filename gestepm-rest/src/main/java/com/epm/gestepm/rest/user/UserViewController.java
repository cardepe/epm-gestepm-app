package com.epm.gestepm.rest.user;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.manualsigningtype.dto.ManualSigningType;
import com.epm.gestepm.modelapi.manualsigningtype.service.ManualSigningTypeService;
import com.epm.gestepm.modelapi.deprecated.project.dto.ProjectDTO;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.Role;
import com.epm.gestepm.modelapi.role.service.RoleService;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class UserViewController {

    private static final Integer FIRST_YEAR = 2018;

    private final ActivityCenterService activityCenterService;

    private final ManualSigningTypeService manualSigningTypeService;

    private final MessageSource messageSource;

    private final ProjectService projectService;

    private final RoleService roleService;

    private final SubRoleService levelService;

    private final UserService userService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/users")
    @LogExecution(operation = OP_VIEW)
    public String viewUserPage(final Locale locale, final Model model) {

        // TODO: Al filtrar la DataTable, si es Jefe de proyecto que filtre por boss users
        /*
        if (user.getRole().getId() == Constants.ROLE_PL_ID) {
            projectIds = user.getBossProjects().stream().map(Project::getId).collect(Collectors.toList());
        }
        */

        this.loadCommonModelView(locale, model);
        this.loadCommonSelects(model);

        model.addAttribute("loadingPath", "users");
        model.addAttribute("type", "view");

        return "users";
    }

    @GetMapping("/users/{id}")
    @LogExecution(operation = OP_VIEW,
            msgIn = "Loading user detail view",
            msgOut = "Loading user detail view OK",
            errorMsg = "Failed to load user detail view")
    public String viewUserDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);
        this.loadCommonSelects(model);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(id));
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("tab", "info");

        return "user-detail";
    }

    @GetMapping("/users/{id}/signings")
    @LogExecution(operation = OP_VIEW)
    public String viewUserSigningsPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(id));
        model.addAttribute("currentUser", currentUser);

        final List<ManualSigningType> manualSigningTypes = this.manualSigningTypeService.findAll();
        model.addAttribute("manualSigningTypes", manualSigningTypes);

        final Map<Integer, String> months = ModelUtil.loadMonths(messageSource, locale);

        int actualYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] yearsDropdown = new int[(actualYear - FIRST_YEAR) + 1];

        for (int i = FIRST_YEAR; i <= actualYear; i++) {
            yearsDropdown[actualYear - i] = i;
        }

        model.addAttribute("months", months);
        model.addAttribute("years", yearsDropdown);

        model.addAttribute("tab", "signings");

        return "user-detail";
    }

    @GetMapping("/users/{id}/expenses")
    @LogExecution(operation = OP_VIEW)
    public String viewUserExpensesPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(id));
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("tab", "expenses");

        return "user-detail";
    }

    @GetMapping("/users/{id}/projects")
    @LogExecution(operation = OP_VIEW)
    public String viewUserProjectsPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(id));
        model.addAttribute("currentUser", currentUser);

        final List<ProjectDTO> notBelongProjects = this.projectService.getNotProjectDTOsByUserId(id.longValue());
        model.addAttribute("notBelongProjects", notBelongProjects);

        model.addAttribute("tab", "projects");

        return "user-detail";
    }

    @GetMapping("/users/{id}/holidays")
    @LogExecution(operation = OP_VIEW)
    public String viewUserHolidaysPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(id));
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("tab", "holidays");

        return "user-detail";
    }

    private void loadCommonSelects(final Model model) {

        final List<ActivityCenterDto> activityCenters = this.activityCenterService.list(new ActivityCenterFilterDto()).stream()
                .sorted(Comparator.comparing(ActivityCenterDto::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
            model.addAttribute("activityCenters", activityCenters);

        final List<Role> roles = this.roleService.getAll().stream()
                .sorted(Comparator.comparing(Role::getRoleName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("roles", roles);

        final List<SubRole> levels = this.levelService.getAll().stream()
                .sorted(Comparator.comparing(SubRole::getRol, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("levels", levels);
    }
}
