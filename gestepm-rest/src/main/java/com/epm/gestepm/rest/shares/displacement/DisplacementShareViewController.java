package com.epm.gestepm.rest.shares.displacement;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.deprecated.project.dto.Project;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectOldService;
import com.epm.gestepm.modelapi.shares.displacement.dto.DisplacementShareDto;
import com.epm.gestepm.modelapi.shares.displacement.dto.finder.DisplacementShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.displacement.service.DisplacementShareService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import lombok.AllArgsConstructor;
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

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class DisplacementShareViewController {

    private final DisplacementShareService displacementShareService;

    private final ProjectOldService projectOldService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/displacement")
    @LogExecution(operation = OP_VIEW)
    public String viewDisplacementSharePage(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        final List<Project> projects = this.projectOldService.findDisplacementProjects();
        model.addAttribute("projects", projects);

        return "displacement-share";
    }

    @GetMapping("/shares/displacement/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewDisplacementShareDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final DisplacementShareDto displacementShare = this.displacementShareService.findOrNotFound(new DisplacementShareByIdFinderDto(id));
        model.addAttribute("displacementShare", displacementShare);

        final List<Project> projects = this.projectOldService.findDisplacementProjects();
        model.addAttribute("projects", projects);

        boolean canUpdate = Constants.ROLE_ADMIN.equals(user.getRole().getRoleName());
        model.addAttribute("canUpdate", canUpdate);

        return "displacement-share-detail";
    }
}
