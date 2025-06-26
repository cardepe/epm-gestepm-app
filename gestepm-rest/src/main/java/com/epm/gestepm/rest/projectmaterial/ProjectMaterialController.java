package com.epm.gestepm.rest.projectmaterial;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.projectmaterial.dto.ProjectMaterialDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.creator.ProjectMaterialCreateDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.deleter.ProjectMaterialDeleteDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.filter.ProjectMaterialFilterDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.finder.ProjectMaterialByIdFinderDto;
import com.epm.gestepm.modelapi.projectmaterial.dto.updater.ProjectMaterialUpdateDto;
import com.epm.gestepm.modelapi.projectmaterial.service.ProjectMaterialService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.projectmaterial.decorators.ProjectMaterialResponseDecorator;
import com.epm.gestepm.rest.projectmaterial.mappers.*;
import com.epm.gestepm.rest.projectmaterial.operations.FindProjectMaterialV1Operation;
import com.epm.gestepm.rest.projectmaterial.operations.ListProjectMaterialV1Operation;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialFindRestRequest;
import com.epm.gestepm.rest.projectmaterial.request.ProjectMaterialListRestRequest;
import com.epm.gestepm.rest.projectmaterial.response.ResponsesForProjectMaterial;
import com.epm.gestepm.rest.projectmaterial.response.ResponsesForProjectMaterialList;
import com.epm.gestepm.restapi.openapi.api.ProjectMaterialV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.projectmaterial.security.ProjectMaterialPermission.PRMT_EDIT_PRMAT;
import static com.epm.gestepm.modelapi.projectmaterial.security.ProjectMaterialPermission.PRMT_READ_PRMAT;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class ProjectMaterialController extends BaseController implements ProjectMaterialV1Api,
        ResponsesForProjectMaterial, ResponsesForProjectMaterialList {

    private final ProjectMaterialService personalExpenseSheetService;

    public ProjectMaterialController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                                     final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                                     final ProjectMaterialService personalExpenseSheetService) {
        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);
        this.personalExpenseSheetService = personalExpenseSheetService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "Get project material list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListProjectMaterialsV1200Response> listProjectMaterialsV1(final Integer projectId, final List<String> meta,
                                                                                    final Boolean links, final Set<String> expand,
                                                                                    final Long offset, final Long limit, final String order,
                                                                                    final String orderBy, final List<Integer> ids, final List<Integer> projectIds, 
                                                                                    final String nameContains, final Boolean required) {
        final ProjectMaterialListRestRequest req = new ProjectMaterialListRestRequest(projectId, ids, projectIds, nameContains, required);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final ProjectMaterialFilterDto filterDto = getMapper(MapPRMATToProjectMaterialFilterDto.class).from(req);
        final Page<ProjectMaterialDto> page = this.personalExpenseSheetService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListProjectMaterialV1Operation());
        final List<ProjectMaterial> data = getMapper(MapPRMATToProjectMaterialResponse.class).from(page);

        this.decorate(req, data, ProjectMaterialResponseDecorator.class);

        return toListProjectMaterialsV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_PRMAT, action = "Find project material")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateProjectMaterialV1200Response> findProjectMaterialByIdV1(final Integer projectId, final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final ProjectMaterialFindRestRequest req = new ProjectMaterialFindRestRequest(projectId, id);

        this.setCommon(req, meta, links, expand);

        final ProjectMaterialByIdFinderDto finderDto = getMapper(MapPRMATToProjectMaterialByIdFinderDto.class).from(req);
        final ProjectMaterialDto dto = this.personalExpenseSheetService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindProjectMaterialV1Operation());
        final ProjectMaterial data = getMapper(MapPRMATToProjectMaterialResponse.class).from(dto);

        this.decorate(req, data, ProjectMaterialResponseDecorator.class);

        return toResProjectMaterialResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Create project material")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateProjectMaterialV1200Response> createProjectMaterialV1(final Integer projectId, final CreateProjectMaterialV1Request reqCreateProjectMaterial) {

        final ProjectMaterialCreateDto createDto = getMapper(MapPRMATToProjectMaterialCreateDto.class).from(reqCreateProjectMaterial);
        createDto.setProjectId(projectId);

        final ProjectMaterialDto personalExpenseSheet = this.personalExpenseSheetService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ProjectMaterial data = getMapper(MapPRMATToProjectMaterialResponse.class).from(personalExpenseSheet);

        final CreateProjectMaterialV1200Response response = new CreateProjectMaterialV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Update project material")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateProjectMaterialV1200Response> updateProjectMaterialV1(final Integer projectId, final Integer id, final UpdateProjectMaterialV1Request reqUpdateProjectMaterial) {

        final ProjectMaterialUpdateDto updateDto = getMapper(MapPRMATToProjectMaterialUpdateDto.class).from(reqUpdateProjectMaterial);
        updateDto.setProjectId(projectId);
        updateDto.setId(id);

        final ProjectMaterialDto personalExpenseSheet = this.personalExpenseSheetService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final ProjectMaterial data = getMapper(MapPRMATToProjectMaterialResponse.class).from(personalExpenseSheet);

        final CreateProjectMaterialV1200Response response = new CreateProjectMaterialV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_PRMAT, action = "Delete project material")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteProjectMaterialV1(final Integer projectId, final Integer id) {

        final ProjectMaterialDeleteDto deleteDto = new ProjectMaterialDeleteDto(id);

        this.personalExpenseSheetService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }

}

