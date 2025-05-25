package com.epm.gestepm.rest.user;

import com.epm.gestepm.lib.applocale.apimodel.service.AppLocaleService;
import com.epm.gestepm.lib.controller.BaseController;
import com.epm.gestepm.lib.controller.metadata.APIMetadata;
import com.epm.gestepm.lib.controller.response.ResponseSuccessfulHelper;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.creator.UserCreateDto;
import com.epm.gestepm.modelapi.user.dto.deleter.UserDeleteDto;
import com.epm.gestepm.modelapi.user.dto.filter.UserFilterDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.common.CommonProviders;
import com.epm.gestepm.rest.common.MetadataMapper;
import com.epm.gestepm.rest.common.ResSuccessMapper;
import com.epm.gestepm.rest.user.decorators.UserResponseDecorator;
import com.epm.gestepm.rest.user.mappers.*;
import com.epm.gestepm.rest.user.operations.FindUserV1Operation;
import com.epm.gestepm.rest.user.operations.ListUserV1Operation;
import com.epm.gestepm.rest.user.request.UserFindRestRequest;
import com.epm.gestepm.rest.user.request.UserListRestRequest;
import com.epm.gestepm.rest.user.response.ResponsesForUser;
import com.epm.gestepm.rest.user.response.ResponsesForUserList;
import com.epm.gestepm.restapi.openapi.api.UserV1Api;
import com.epm.gestepm.restapi.openapi.model.*;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.REST;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.user.security.UserPermission.PRMT_EDIT_U;
import static com.epm.gestepm.modelapi.user.security.UserPermission.PRMT_READ_U;
import static org.mapstruct.factory.Mappers.getMapper;

@RestController
@EnableExecutionLog(layerMarker = REST)
public class UserController extends BaseController implements UserV1Api,
        ResponsesForUser, ResponsesForUserList {

    private final UserService userService;

    public UserController(final CommonProviders commonProviders, final ApplicationContext appCtx,
                          final AppLocaleService appLocaleService, final ResponseSuccessfulHelper successHelper,
                          final UserService userService) {

        super(commonProviders.localeProvider(), commonProviders.executionRequestProvider(),
                commonProviders.executionTimeProvider(), commonProviders.restContextProvider(), appCtx, appLocaleService,
                successHelper);

        this.userService = userService;
    }

    @Override
    @RequirePermits(value = PRMT_READ_U, action = "Get user list")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<ListUsersV1200Response> listUsersV1(final List<String> meta, final Boolean links, final Set<String> expand, final Long offset, final Long limit, final String order, final String orderBy,
                                                              final List<Integer> ids, final String email, final String password, final List<Integer> activityCenterIds,
                                                              final Integer state, final List<Integer> signingIds, final List<Integer> roleIds, final List<Integer> levelIds) {

        final UserListRestRequest req = new UserListRestRequest(ids, email, password, activityCenterIds, state, signingIds, roleIds, levelIds);

        this.setCommon(req, meta, links, expand);
        this.setDefaults(req);
        this.setPagination(req, limit, offset);
        this.setOrder(req, order, orderBy);

        final UserFilterDto filterDto = getMapper(MapUToUserFilterDto.class).from(req);
        final Page<UserDto> page = this.userService.list(filterDto, offset, limit);

        final APIMetadata metadata = this.getMetadata(req, page, new ListUserV1Operation());
        final List<User> data = getMapper(MapUToUserResponse.class).from(page);

        this.decorate(req, data, UserResponseDecorator.class);

        return toListUsersV1200Response(metadata, data, page.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_READ_U, action = "Find user")
    @LogExecution(operation = OP_READ)
    public ResponseEntity<CreateUserV1200Response> findUserByIdV1(final Integer id, final List<String> meta, final Boolean links, final Set<String> expand) {

        final UserFindRestRequest req = new UserFindRestRequest(id);

        this.setCommon(req, meta, links, expand);

        final UserByIdFinderDto finderDto = getMapper(MapUToUserByIdFinderDto.class).from(req);
        final UserDto dto = this.userService.findOrNotFound(finderDto);

        final APIMetadata metadata = this.getMetadata(req, new FindUserV1Operation());
        final User data = getMapper(MapUToUserResponse.class).from(dto);

        this.decorate(req, data, UserResponseDecorator.class);

        return toResUserResponse(metadata, data, dto.hashCode());
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_U, action = "Create user")
    @LogExecution(operation = OP_CREATE)
    public ResponseEntity<CreateUserV1200Response> createUserV1(final CreateUserV1Request reqCreateUser) {

        final UserCreateDto createDto = getMapper(MapUToUserCreateDto.class).from(reqCreateUser);

        final UserDto userDto = this.userService.create(createDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final User data = getMapper(MapUToUserResponse.class).from(userDto);

        final CreateUserV1200Response response = new CreateUserV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_U, action = "Update user")
    @LogExecution(operation = OP_UPDATE)
    public ResponseEntity<CreateUserV1200Response> updateUserV1(final Integer id, final UpdateUserV1Request reqUpdateUser) {

        final UserUpdateDto updateDto = getMapper(MapUToUserUpdateDto.class).from(reqUpdateUser);
        updateDto.setId(id);

        final UserDto countryDto = this.userService.update(updateDto);

        final APIMetadata metadata = this.getDefaultMetadata();
        final User data = getMapper(MapUToUserResponse.class).from(countryDto);

        final CreateUserV1200Response response = new CreateUserV1200Response();
        response.setMetadata(getMapper(MetadataMapper.class).from(metadata));
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_U, action = "Delete user")
    @LogExecution(operation = OP_DELETE)
    public ResponseEntity<ResSuccess> deleteUserV1(final Integer id) {

        final UserDeleteDto deleteDto = new UserDeleteDto();
        deleteDto.setId(id);

        this.userService.delete(deleteDto);

        return this.success(getMapper(ResSuccessMapper.class)::from);
    }
}

