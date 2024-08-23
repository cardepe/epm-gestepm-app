package com.epm.gestepm.rest.displacement;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class DisplacementViewController {

    private final ActivityCenterService activityCenterService;

    @ModelAttribute
    public void loadCommonModelView(final Locale locale, final Model model) {

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);

        model.addAttribute("tableActionButtons", ModelUtil.getTableModifyActionButtons());
    }

    @GetMapping("/displacements")
    // @LogExecution(operation = OP_VIEW) : fixme model prints infinite loop
    public String viewProductPage(final Model model) {

        final ActivityCenterFilterDto filterDto = new ActivityCenterFilterDto();
        filterDto.setOrder("ASC");
        filterDto.setOrderBy("name");

        final List<ActivityCenterDto> activityCenterDtos = this.activityCenterService.list(filterDto);

        model.addAttribute("activityCenters", activityCenterDtos);

        return "admin-displacements";
    }
}
