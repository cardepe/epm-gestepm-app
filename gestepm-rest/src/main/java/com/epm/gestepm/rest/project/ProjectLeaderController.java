package com.epm.gestepm.rest.project;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectLeaderCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectLeaderDeleteDto;
import com.epm.gestepm.modelapi.project.service.ProjectLeaderService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.project.mappers.*;
import com.epm.gestepm.restapi.openapi.api.ProjectLeaderV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProjectLeaderController extends BaseController implements ProjectLeaderV1Api {

    private final ProjectLeaderService projectLeaderService;

    public ProjectLeaderController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                   final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                   final ProjectLeaderService projectLeaderService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.projectLeaderService = projectLeaderService;
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create project leader")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<ResSuccess> createProjectLeaderV1(final Integer projectId, final CreateProjectLeaderV1Request reqCreateProjectLeader) {

        final ProjectLeaderCreateDto createDto = getMapper(MapPRLToProjectLeaderCreateDto.class).from(reqCreateProjectLeader);
        createDto.setProjectId(projectId);

        this.projectLeaderService.create(createDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project leader")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProjectLeaderV1(final Integer projectId, final Integer userId) {

        final ProjectLeaderDeleteDto deleteDto = new ProjectLeaderDeleteDto(projectId, userId);

        this.projectLeaderService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

