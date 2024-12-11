package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;

@Controller
@EnableExecutionLog(layerMarker = VIEW)
public class InspectionViewController {

    @ModelAttribute
    public void loadCommonModelView(final Locale locale, final Model model) {

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    /*@GetMapping("/shares/no-programmed/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewNoProgrammedShareDetailPage(@PathVariable final Long id, final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);

        return "Inspection-share-detail";
    }*/
}
