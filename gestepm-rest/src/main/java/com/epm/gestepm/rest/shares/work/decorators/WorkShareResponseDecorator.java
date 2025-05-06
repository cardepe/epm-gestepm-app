package com.epm.gestepm.rest.shares.work.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.shares.work.request.WorkShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.WorkShare;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("workShareResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class WorkShareResponseDecorator extends BaseResponseDataDecorator<WorkShare> {

    public static final String WS_U_EXPAND = "user";

    public static final String WS_P_EXPAND = "project";

    private final ProjectService projectService;

    private final UserService userService;

    public WorkShareResponseDecorator(ApplicationContext applicationContext, ProjectService projectService, UserService userService) {
        super(applicationContext);
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating work share response",
            msgOut = "Work share decorated OK",
            errorMsg = "Error decorating work share response")
    public void decorate(RestRequest request, WorkShare data) {

        if (request.getLinks()) {

            final WorkShareFindRestRequest selfReq = new WorkShareFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(WS_U_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getFullName());

            data.setUser(response);
        }

        if (request.hasExpand(WS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }
    }
}
