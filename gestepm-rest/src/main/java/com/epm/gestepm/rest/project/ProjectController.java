package com.epm.gestepm.rest.project;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.creator.ProjectCreateDto;
import com.epm.gestepm.modelapi.project.dto.deleter.ProjectDeleteDto;
import com.epm.gestepm.modelapi.project.dto.filter.ProjectFilterDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.dto.updater.ProjectUpdateDto;
import com.epm.gestepm.modelapi.project.service.ProjectDelegator;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.project.decorators.ProjectResponseDecorator;
import com.epm.gestepm.rest.project.mappers.*;
import com.epm.gestepm.rest.project.operations.FindProjectV1Operation;
import com.epm.gestepm.rest.project.operations.ListProjectV1Operation;
import com.epm.gestepm.rest.project.request.ProjectFindRestRequest;
import com.epm.gestepm.rest.project.request.ProjectListRestRequest;
import com.epm.gestepm.rest.project.response.ResponsesForProject;
import com.epm.gestepm.rest.project.response.ResponsesForProjectList;
import com.epm.gestepm.restapi.openapi.api.ProjectV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_EDIT_PR;
import static com.epm.gestepm.modelapi.project.security.ProjectPermission.PRMT_READ_PR;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProjectController extends BaseController implements ProjectV1Api,
        ResponsesForProject, ResponsesForProjectList {

    private final ProjectDelegator projectDelegator;

    private final ProjectService projectService;

    public ProjectController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                             final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper, ProjectDelegator projectDelegator,
                             final ProjectService projectService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.projectDelegator = projectDelegator;
        this.projectService = projectService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PR, action = "Get project list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListProjectsV1200Response> listProjectsV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                              final List<Integer> ids, final String nameContains, final Boolean isStation, final List<Integer> activityCenterIds, final Boolean isTeleworking,
                                                                    final Integer state, final List<Integer> responsibleIds, final List<Integer> memberIds) {

        final ProjectListRestRequest req = new ProjectListRestRequest(ids, nameContains, isStation, activityCenterIds, isTeleworking, state, responsibleIds, memberIds);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ProjectFilterDto filterDto = getMapper(MapPRToProjectFilterDto.class).from(req);
        final Page<ProjectDto> page = this.projectService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListProjectV1Operation());
        final List<Project> data = getMapper(MapPRToProjectResponse.class).from(page);

        this.decorate(req, data, ProjectResponseDecorator.class);

        return toListProjectsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PR, action = "Find project")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateProjectV1200Response> findProjectByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final ProjectFindRestRequest req = new ProjectFindRestRequest(id);

        this.setCommon(req, meta, links, expand);

        final ProjectByIdFinderDto finderDto = getMapper(MapPRToProjectByIdFinderDto.class).from(req);
        final ProjectDto dto = this.projectService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindProjectV1Operation());
        final Project data = getMapper(MapPRToProjectResponse.class).from(dto);

        this.decorate(req, data, ProjectResponseDecorator.class);

        return toResProjectResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Create project")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateProjectV1200Response> createProjectV1(final CreateProjectV1Request reqCreateProject) {

        final ProjectCreateDto createDto = getMapper(MapPRToProjectCreateDto.class).from(reqCreateProject);

        final ProjectDto projectDto = this.projectService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Project data = getMapper(MapPRToProjectResponse.class).from(projectDto);

        final CreateProjectV1200Response response = new CreateProjectV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Update project")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateProjectV1200Response> updateProjectV1(final Integer id, final UpdateProjectV1Request reqUpdateProject) {

        final ProjectUpdateDto updateDto = getMapper(MapPRToProjectUpdateDto.class).from(reqUpdateProject);
        updateDto.setId(id);

        final ProjectDto countryDto = this.projectService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final Project data = getMapper(MapPRToProjectResponse.class).from(countryDto);

        final CreateProjectV1200Response response = new CreateProjectV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Delete project")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProjectV1(final Integer id) {

        final ProjectDeleteDto deleteDto = new ProjectDeleteDto();
        deleteDto.setId(id);

        this.projectService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PR, action = "Duplicate project")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<CreateProjectV1200Response> duplicateProjectByIdV1(final Integer projectId) {

        final ProjectDto projectDto = this.projectDelegator.duplicate(new ProjectByIdFinderDto(projectId));

        final APIMetadata metadata = this.getDefaultMetadata();
        final Project data = getMapper(MapPRToProjectResponse.class).from(projectDto);

        final CreateProjectV1200Response response = new CreateProjectV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}

