package com.epm.gestepm.rest.project;

import com.epm.gestepm.forum.model.api.dto.ForumDTO;
import com.epm.gestepm.forum.model.api.service.UserForumService;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.customer.dto.CustomerDto;
import com.epm.gestepm.modelapi.customer.dto.finder.CustomerByProjectIdFinderDto;
import com.epm.gestepm.modelapi.customer.service.CustomerService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class ProjectViewController {

    private final CustomerService customerService;

    private final ProjectService projectService;

    private final UserService userService;

    private final UserForumService userForumService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/projects")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectPage(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final List<ForumDTO> forums = this.userForumService.getAllForumsToDTO();
        model.addAttribute("forums", forums);

        model.addAttribute("importPath", "projects");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "view");

        return "projects";
    }

    @GetMapping("/projects/{id}")
    @LogExecution(operation = OP_VIEW,
            msgIn = "Loading project detail view",
            msgOut = "Loading project detail view OK",
            errorMsg = "Failed to load project detail view")
    public String viewProjectDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        final List<ForumDTO> forums = this.userForumService.getAllForumsToDTO();
        model.addAttribute("forums", forums);

        final CustomerDto customer = this.customerService.findOrNotFound(new CustomerByProjectIdFinderDto(id));
        model.addAttribute("customer", customer);

        model.addAttribute("importPath", "project-detail");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "detail");
        model.addAttribute("tab", "info");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/expenses")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectExpensesPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        final UserDto currentUser = this.userService.findOrNotFound(new UserByIdFinderDto(user.getId().intValue()));
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("importPath", "project-expenses");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "expenses");
        model.addAttribute("tab", "expenses");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/signings")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectSigningsPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        model.addAttribute("importPath", "project-signings");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "signings");
        model.addAttribute("tab", "signings");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/leaders")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectProjectLeadersPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        model.addAttribute("importPath", "project-leaders");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "leaders");
        model.addAttribute("tab", "leaders");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/members")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectMembersPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        model.addAttribute("importPath", "project-members");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "members");
        model.addAttribute("tab", "members");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/materials")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectMaterialsPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        model.addAttribute("importPath", "project-materials");
        model.addAttribute("loadingPath", "projects");
        model.addAttribute("type", "materials");
        model.addAttribute("tab", "materials");

        return "project-detail";
    }

    @GetMapping("/projects/{id}/families")
    @LogExecution(operation = OP_VIEW)
    public String viewProjectFamiliesPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final ProjectDto currentProject = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
        model.addAttribute("currentProject", currentProject);

        model.addAttribute("importPath", "project-families");
        model.addAttribute("loadingPath", "families");
        model.addAttribute("type", "detail");
        model.addAttribute("tab", "families");

        return "project-detail";
    }
}
