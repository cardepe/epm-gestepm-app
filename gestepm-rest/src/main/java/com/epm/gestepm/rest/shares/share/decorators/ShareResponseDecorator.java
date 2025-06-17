package com.epm.gestepm.rest.shares.share.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectService;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.Share;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("shareResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class ShareResponseDecorator extends BaseResponseDataDecorator<Share> {

    public static final String S_U_EXPAND = "user";

    public static final String S_P_EXPAND = "project";

    private final ProjectService projectService;

    private final UserServiceOld userServiceOld;

    public ShareResponseDecorator(ApplicationContext applicationContext, ProjectService projectService, UserServiceOld userServiceOld) {
        super(applicationContext);
        this.projectService = projectService;
        this.userServiceOld = userServiceOld;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating share response",
            msgOut = "Share decorated OK",
            errorMsg = "Error decorating share response")
    public void decorate(RestRequest request, Share data) {

        if (request.hasExpand(S_U_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.deprecated.user.dto.User userDto = this.userServiceOld.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getFullName());

            data.setUser(response);
        }

        if (request.hasExpand(S_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.deprecated.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }
    }
}
