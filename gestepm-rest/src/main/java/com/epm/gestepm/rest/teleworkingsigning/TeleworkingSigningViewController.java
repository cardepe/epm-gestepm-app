package com.epm.gestepm.rest.teleworkingsigning;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.project.dto.ProjectListDTO;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.TeleworkingSigningDto;
import com.epm.gestepm.modelapi.teleworkingsigning.dto.filter.TeleworkingSigningFilterDto;
import com.epm.gestepm.modelapi.teleworkingsigning.service.TeleworkingSigningService;
import com.epm.gestepm.modelapi.user.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@EnableExecutionLog(layerMarker = VIEW)
public class TeleworkingSigningViewController {

    private final TeleworkingSigningService teleworkingSigningService;

    private final ProjectService projectService;

    public TeleworkingSigningViewController(TeleworkingSigningService teleworkingSigningService, ProjectService projectService) {
        this.teleworkingSigningService = teleworkingSigningService;
        this.projectService = projectService;
    }

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/signings/teleworking")
    @LogExecution(operation = OP_VIEW)
    public String viewPersonalExpensesSheets(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);
        this.loadProjects(model);

        return "teleworking-signings";
    }

    @GetMapping("/signings/teleworking/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewPersonalExpensesSheet(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        this.loadProjects(model);

        return "teleworking-signing-detail";
    }

    private void loadProjects(final Model model) {
        final List<ProjectListDTO> projects = this.projectService.getTeleworkingProjects(true);
        model.addAttribute("projects", projects);
    }
}
