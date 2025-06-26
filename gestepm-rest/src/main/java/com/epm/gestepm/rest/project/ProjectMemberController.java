package com.epm.gestepm.rest.project;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectMemberCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectMemberDeleteDto;
import com.epm.gestepm.modelapi.project.service.ProjectMemberService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.project.mappers.MapPRMToProjectMemberCreateDto;
import com.epm.gestepm.restapi.openapi.api.ProjectMemberV1Api;
import com.epm.gestepm.restapi.openapi.model.CreateProjectMemberV1Request;
import com.epm.gestepm.restapi.openapi.model.ResSuccess;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_CREATE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_DELETE;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProjectMemberController extends BaseController implements ProjectMemberV1Api {

    private final ProjectMemberService projectMemberService;

    public ProjectMemberController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                   final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                   final ProjectMemberService projectMemberService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.projectMemberService = projectMemberService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create project member")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<ResSuccess> createProjectMemberV1(final Integer projectId, final CreateProjectMemberV1Request reqCreateProjectMember) {

        final ProjectMemberCreateDto createDto = getMapper(MapPRMToProjectMemberCreateDto.class).from(reqCreateProjectMember);
        createDto.setProjectId(projectId);

        this.projectMemberService.create(createDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project member")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProjectMemberV1(final Integer projectId, final Integer userId) {

        final ProjectMemberDeleteDto deleteDto = new ProjectMemberDeleteDto(projectId, userId);

        this.projectMemberService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

