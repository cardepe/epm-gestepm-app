package com.epm.gestepm.rest.shares.programmed;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.ProgrammedShareFileDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.filter.ProgrammedShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.programmed.dto.finder.ProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareFileService;
import com.epm.gestepm.modelapi.shares.programmed.service.ProgrammedShareService;
import com.epm.gestepm.modelapi.user.dto.User;
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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class ProgrammedShareViewController {

    private final ProgrammedShareService programmedShareService;

    private final ProgrammedShareFileService programmedShareFileService;

    private final ProjectService projectService;

    private final UserService userService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/programmed")
    @LogExecution(operation = OP_VIEW)
    public String viewProgrammedSharePage(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(false).stream()
                .sorted(Comparator.comparing(ProjectListDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("projects", projects);

        final List<User> users = this.userService.findByState(0).stream()
                .sorted(Comparator.comparing(User::getFullName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("users", users);

        return "programmed-share";
    }

    @GetMapping("/shares/programmed/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewProgrammedShareDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final ProgrammedShareDto programmedShare = this.programmedShareService.findOrNotFound(new ProgrammedShareByIdFinderDto(id));
        model.addAttribute("programmedShare", programmedShare);

        final List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(false).stream()
                .sorted(Comparator.comparing(ProjectListDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("projects", projects);

        final List<User> users = this.userService.findByState(0).stream()
                .sorted(Comparator.comparing(User::getFullName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("users", users);

        boolean canUpdate = Constants.ROLE_ADMIN.equals(user.getRole().getRoleName());
        model.addAttribute("canUpdate", canUpdate);

        final ProgrammedShareFileFilterDto filterDto = new ProgrammedShareFileFilterDto();
        filterDto.setShareId(id);

        final List<ProgrammedShareFileDto> files = this.programmedShareFileService.list(filterDto).stream()
                .sorted(Comparator.comparing(ProgrammedShareFileDto::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("files", files);

        return "programmed-share-detail";
    }
}
