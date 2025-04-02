package com.epm.gestepm.rest.inspection;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
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
public class InspectionViewController {

    private final NoProgrammedShareService noProgrammedShareService;

    private final SubFamilyService subFamilyService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/shares/no-programmed/{shareId}/inspections/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewInspection(@PathVariable final Integer shareId, @PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        final NoProgrammedShareDto noProgrammedShare = this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(shareId));
        final List<RoleDTO> subRoles = this.subFamilyService.getSubRolsById(noProgrammedShare.getSubFamilyId().longValue());

        final String userLevel = user.getSubRole().getRol();
        final boolean hasRole = subRoles.isEmpty()
                || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));

        model.addAttribute("hasRole", hasRole);

        return "inspection-detail";
    }
}
