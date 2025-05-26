package com.epm.gestepm.rest.shares.construction;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.breaks.dto.ShareBreakDto;
import com.epm.gestepm.modelapi.shares.breaks.dto.filter.ShareBreakFilterDto;
import com.epm.gestepm.modelapi.shares.breaks.service.ShareBreakService;
import com.epm.gestepm.modelapi.shares.common.dto.ShareStatusDto;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareDto;
import com.epm.gestepm.modelapi.shares.construction.dto.ConstructionShareFileDto;
import com.epm.gestepm.modelapi.shares.construction.dto.filter.ConstructionShareFileFilterDto;
import com.epm.gestepm.modelapi.shares.construction.dto.finder.ConstructionShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareFileService;
import com.epm.gestepm.modelapi.shares.construction.service.ConstructionShareService;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
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
public class ConstructionShareViewController {

    private final ConstructionShareService constructionShareService;

    private final ConstructionShareFileService constructionShareFileService;

    private final ProjectService projectService;

    private final ShareBreakService shareBreakService;

    private final UserServiceOld userServiceOld;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/construction")
    @LogExecution(operation = OP_VIEW)
    public String viewConstructionSharePage(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(false).stream()
                .sorted(Comparator.comparing(ProjectListDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("projects", projects);

        final List<User> users = this.userServiceOld.findByState(0).stream()
                .sorted(Comparator.comparing(User::getFullName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("users", users);

        return "construction-share";
    }

    @GetMapping("/shares/construction/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewConstructionShareDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final ConstructionShareDto constructionShare = this.constructionShareService.findOrNotFound(new ConstructionShareByIdFinderDto(id));
        model.addAttribute("constructionShare", constructionShare);

        final List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(false).stream()
                .sorted(Comparator.comparing(ProjectListDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("projects", projects);

        boolean canUpdate = Constants.ROLE_ADMIN.equals(user.getRole().getRoleName());
        model.addAttribute("canUpdate", canUpdate);

        final ConstructionShareFileFilterDto filterDto = new ConstructionShareFileFilterDto();
        filterDto.setShareId(id);

        final List<ConstructionShareFileDto> files = this.constructionShareFileService.list(filterDto).stream()
                .sorted(Comparator.comparing(ConstructionShareFileDto::getName, String.CASE_INSENSITIVE_ORDER))
                .collect(Collectors.toList());
        model.addAttribute("files", files);

        final ShareBreakFilterDto shareBreakFilterDto = new ShareBreakFilterDto();
        shareBreakFilterDto.setConstructionShareIds(List.of(id));
        shareBreakFilterDto.setStatus(ShareStatusDto.NOT_FINISHED);

        final Page<ShareBreakDto> list = this.shareBreakService.list(shareBreakFilterDto, 0L, 1L);
        if (!list.isEmpty() && list.get(0).isPresent()) {
            model.addAttribute("currentShareBreak", list.get(0).get());
        }

        return "construction-share-detail";
    }
}
