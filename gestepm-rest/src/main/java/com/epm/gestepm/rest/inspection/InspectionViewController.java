package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@EnableExecutionLog(layerMarker = VIEW)
public class InspectionViewController {

    @ModelAttribute
    public void loadCommonModelView(final Locale locale, final Model model) {

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/no-programmed/{shareId}/inspections/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewInspection(@PathVariable final Integer shareId, @PathVariable final Integer id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        return "inspection-detail";
    }
}