package com.epm.gestepm.rest.holiday;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.filter.ActivityCenterFilterDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.masterdata.api.country.dto.CountryDto;
import com.epm.gestepm.masterdata.api.country.dto.filter.CountryFilterDto;
import com.epm.gestepm.masterdata.api.country.service.CountryService;
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
public class HolidayViewController {

    private final ActivityCenterService activityCenterService;

    private final CountryService countryService;

    @ModelAttribute
    public void loadCommonModelView(final Locale locale, final Model model) {

        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);

        model.addAttribute("tableActionButtons", ModelUtil.getTableModifyActionButtons());
    }

    @GetMapping("/holidays")
    // @LogExecution(operation = OP_VIEW) : fixme model prints infinite loop
    public String viewProductPage(final Model model) {

        final CountryFilterDto countryFilterDto = new CountryFilterDto();
        countryFilterDto.setOrder("ASC");
        countryFilterDto.setOrderBy("name");

        final ActivityCenterFilterDto activityCenterFilterDto = new ActivityCenterFilterDto();
        activityCenterFilterDto.setOrder("ASC");
        activityCenterFilterDto.setOrderBy("name");

        final List<CountryDto> countryDtos = this.countryService.list(countryFilterDto);
        final List<ActivityCenterDto> activityCenterDtos = this.activityCenterService.list(activityCenterFilterDto);

        model.addAttribute("countries", countryDtos);
        model.addAttribute("activityCenters", activityCenterDtos);

        return "admin-countries";
    }
}