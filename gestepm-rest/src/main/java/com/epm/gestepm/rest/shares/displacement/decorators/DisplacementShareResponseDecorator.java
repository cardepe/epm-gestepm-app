package com.epm.gestepm.rest.shares.displacement.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.shares.displacement.request.DisplacementShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("displacementShareResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class DisplacementShareResponseDecorator extends BaseResponseDataDecorator<DisplacementShare> {

    public static final String DS_U_EXPAND = "user";

    public static final String DS_P_EXPAND = "project";

    private final ProjectService projectService;

    private final UserService userService;

    public DisplacementShareResponseDecorator(ApplicationContext applicationContext, ProjectService projectService, UserService userService) {
        super(applicationContext);
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating displacement share response",
            msgOut = "Displacement share decorated OK",
            errorMsg = "Error decorating displacement share response")
    public void decorate(RestRequest request, DisplacementShare data) {

        if (request.getLinks()) {

            final DisplacementShareFindRestRequest selfReq = new DisplacementShareFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(DS_U_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getName()).surnames(userDto.getSurnames());

            data.setUser(response);
        }

        if (request.hasExpand(DS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }
    }
}
