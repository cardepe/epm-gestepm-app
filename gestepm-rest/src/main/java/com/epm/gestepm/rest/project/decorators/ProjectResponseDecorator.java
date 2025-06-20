package com.epm.gestepm.rest.project.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.role.service.RoleService;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.rest.activitycenter.mappers.MapACToActivityCenterResponse;
import com.epm.gestepm.rest.project.request.ProjectFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.ActivityCenter;
import com.epm.gestepm.restapi.openapi.model.Level;
import com.epm.gestepm.restapi.openapi.model.Role;
import com.epm.gestepm.restapi.openapi.model.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("projectResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class ProjectResponseDecorator extends BaseResponseDataDecorator<Project> {

    public static final String PR_AC_EXPAND = "activityCenter";

    private final ActivityCenterService activityCenterService;

    public ProjectResponseDecorator(ApplicationContext applicationContext, ActivityCenterService activityCenterService) {
        super(applicationContext);
        this.activityCenterService = activityCenterService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating project response",
            msgOut = "Project decorated OK",
            errorMsg = "Error decorating project response")
    public void decorate(RestRequest request, Project data) {

        if (request.getLinks()) {

            final ProjectFindRestRequest selfReq = new ProjectFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(PR_AC_EXPAND) && data.getActivityCenter() != null) {

            final ActivityCenter activityCenter = data.getActivityCenter();
            final Integer id = activityCenter.getId();

            final com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto dto = this.activityCenterService.findOrNotFound(new ActivityCenterByIdFinderDto(id));
            final ActivityCenter response = getMapper(MapACToActivityCenterResponse.class).from(dto);

            data.setActivityCenter(response);
        }
    }
}
