package com.epm.gestepm.rest.signings.teleworking.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.rest.signings.teleworking.request.TeleworkingSigningFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.TeleworkingSigning;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("teleworkingSigningResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class TeleworkingSigningResponseDecorator extends BaseResponseDataDecorator<TeleworkingSigning> {

    public static final String TS_U_EXPAND = "user";
    
    public static final String TS_P_EXPAND = "project";
    
    private final UserServiceOld userServiceOld;
    
    private final ProjectService projectService;
    
    public TeleworkingSigningResponseDecorator(ApplicationContext applicationContext, UserServiceOld userServiceOld, ProjectService projectService) {  // , PersonalExpenseService personalExpenseService) {
        super(applicationContext);
        this.userServiceOld = userServiceOld;
        this.projectService = projectService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating teleworking signing response",
            msgOut = "Teleworking signing decorated OK",
            errorMsg = "Error decorating teleworking signing response")
    public void decorate(RestRequest request, TeleworkingSigning data) {

        if (request.getLinks()) {

            final TeleworkingSigningFindRestRequest selfReq = new TeleworkingSigningFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(TS_U_EXPAND)) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.deprecated.user.dto.User userDto = this.userServiceOld.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getName());

            data.setUser(response);
        }

        if (request.hasExpand(TS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }
    }
}
