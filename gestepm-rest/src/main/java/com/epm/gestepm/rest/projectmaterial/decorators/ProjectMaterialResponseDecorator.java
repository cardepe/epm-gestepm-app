package com.epm.gestepm.rest.projectmaterial.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.rest.project.mappers.MapPRToProjectResponse;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.ProjectMaterial;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("projectMaterialResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class ProjectMaterialResponseDecorator extends BaseResponseDataDecorator<ProjectMaterial> {

    public static final String PRMAT_P_EXPAND = "project";
    
    private final ProjectService projectService;
    
    public ProjectMaterialResponseDecorator(ApplicationContext applicationContext, UserServiceOld userServiceOld, ProjectService projectService) {  // , PersonalExpenseService personalExpenseService) {
        super(applicationContext);
        this.projectService = projectService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating project material response",
            msgOut = "Project material decorated OK",
            errorMsg = "Error decorating project material response")
    public void decorate(RestRequest request, ProjectMaterial data) {

        if (request.getLinks()) {

            final ProjectMaterialFindRestRequest selfReq = new ProjectMaterialFindRestRequest(data.getProject().getId(), data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(PRMAT_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final ProjectDto projectDto = this.projectService.findOrNotFound(new ProjectByIdFinderDto(id));
            final Project response = getMapper(MapPRToProjectResponse.class).from(projectDto);

            data.setProject(response);
        }
    }
}
