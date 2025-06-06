package com.epm.gestepm.rest.user.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.service.RoleService;
import com.epm.gestepm.modelapi.subrole.dto.SubRole;
import com.epm.gestepm.modelapi.subrole.service.SubRoleService;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
import com.epm.gestepm.rest.activitycenter.mappers.MapACToActivityCenterResponse;
import com.epm.gestepm.rest.country.mappers.MapCToCountryResponse;
import com.epm.gestepm.rest.user.request.UserFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.*;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("userResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class UserResponseDecorator extends BaseResponseDataDecorator<User> {

    public static final String U_AC_EXPAND = "activityCenter";

    public static final String U_R_EXPAND = "role";

    public static final String U_L_EXPAND = "level";

    private final ActivityCenterService activityCenterService;
    
    private final RoleService roleService;
    
    private final SubRoleService subRoleService;

    public UserResponseDecorator(ApplicationContext applicationContext, ActivityCenterService activityCenterService, RoleService roleService, SubRoleService subRoleService) {
        super(applicationContext);
        this.activityCenterService = activityCenterService;
        this.roleService = roleService;
        this.subRoleService = subRoleService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating user response",
            msgOut = "User decorated OK",
            errorMsg = "Error decorating user response")
    public void decorate(RestRequest request, User data) {

        if (request.getLinks()) {

            final UserFindRestRequest selfReq = new UserFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(U_AC_EXPAND) && data.getActivityCenter() != null) {

            final ActivityCenter activityCenter = data.getActivityCenter();
            final Integer id = activityCenter.getId();

            final com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto dto = this.activityCenterService.findOrNotFound(new ActivityCenterByIdFinderDto(id));
            final ActivityCenter response = getMapper(MapACToActivityCenterResponse.class).from(dto);

            data.setActivityCenter(response);
        }

        if (request.hasExpand(U_R_EXPAND)) {

            final Role role = data.getRole();
            final Integer id = role.getId();

            final com.epm.gestepm.modelapi.role.dto.Role dto = this.roleService.getRoleById(Long.valueOf(id));
            final Role response = new Role().id(id).name(dto.getRoleName());

            data.setRole(response);
        }

        if (request.hasExpand(U_L_EXPAND)) {

            final Level level = data.getLevel();
            final Integer id = level.getId();

            final com.epm.gestepm.modelapi.subrole.dto.SubRole dto = this.subRoleService.getSubRoleById(Long.valueOf(id));
            final Level response = new Level().id(id).name(dto.getRol());

            data.setLevel(response);
        }
    }
}
