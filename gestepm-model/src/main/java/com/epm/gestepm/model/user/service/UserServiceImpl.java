package com.epm.gestepm.model.user.service;

import com.epm.gestepm.forum.model.api.service.UserForumService;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.lib.security.annotation.RequirePermits;
import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.masterdata.api.activitycenter.dto.ActivityCenterDto;
import com.epm.gestepm.masterdata.api.activitycenter.dto.finder.ActivityCenterByIdFinderDto;
import com.epm.gestepm.masterdata.api.activitycenter.service.ActivityCenterService;
import com.epm.gestepm.model.user.dao.UserDao;
import com.epm.gestepm.model.user.dao.entity.User;
import com.epm.gestepm.model.user.dao.entity.creator.UserCreate;
import com.epm.gestepm.model.user.dao.entity.deleter.UserDelete;
import com.epm.gestepm.model.user.dao.entity.filter.UserFilter;
import com.epm.gestepm.model.user.dao.entity.finder.UserByIdFinder;
import com.epm.gestepm.model.user.dao.entity.updater.UserUpdate;
import com.epm.gestepm.model.user.service.mapper.*;
import com.epm.gestepm.modelapi.common.utils.CipherUtil;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.creator.UserCreateDto;
import com.epm.gestepm.modelapi.user.dto.deleter.UserDeleteDto;
import com.epm.gestepm.modelapi.user.dto.filter.UserFilterDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;
import com.epm.gestepm.modelapi.user.exception.UserForumAlreadyException;
import com.epm.gestepm.modelapi.user.exception.UserNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.function.Supplier;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.SERVICE;
import static com.epm.gestepm.lib.logging.constants.LogOperations.*;
import static com.epm.gestepm.modelapi.user.security.UserPermission.PRMT_EDIT_U;
import static com.epm.gestepm.modelapi.user.security.UserPermission.PRMT_READ_U;
import static org.mapstruct.factory.Mappers.getMapper;

@Validated
@Service
@AllArgsConstructor
@EnableExecutionLog(layerMarker = SERVICE)
public class UserServiceImpl implements UserService {

    private static final Double DEFAULT_WORKING_HOURS = 8.0;

    private static final Integer HOLIDAYS_COUNT_SPAIN = 22;

    private static final Integer HOLIDAYS_COUNT_FRANCE = 30;

    private final ActivityCenterService activityCenterService;

    private final UserDao userDao;

    private final UserForumService userForumService;

    @Override
    @RequirePermits(value = PRMT_READ_U, action = "List users")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing users",
            msgOut = "Listing users OK",
            errorMsg = "Failed to list users")
    public List<UserDto> list(UserFilterDto filterDto) {
        final UserFilter filter = getMapper(MapUToUserFilter.class).from(filterDto);

        final List<User> list = this.userDao.list(filter);

        return getMapper(MapUToUserDto.class).from(list);
    }

    @Override
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Listing users",
            msgOut = "Listing users OK",
            errorMsg = "Failed to list users")
    public Page<UserDto> list(UserFilterDto filterDto, Long offset, Long limit) {

        final UserFilter filter = getMapper(MapUToUserFilter.class).from(filterDto);

        final Page<User> page = this.userDao.list(filter, offset, limit);

        return getMapper(MapUToUserDto.class).from(page);
    }

    @Override
    @RequirePermits(value = PRMT_READ_U, action = "Find user by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding user by ID, result can be empty",
            msgOut = "Found user by ID",
            errorMsg = "Failed to find user by ID")
    public Optional<UserDto> find(final UserByIdFinderDto finderDto) {
        final UserByIdFinder finder = getMapper(MapUToUserByIdFinder.class).from(finderDto);

        final Optional<User> found = this.userDao.find(finder);

        return found.map(getMapper(MapUToUserDto.class)::from);
    }

    @Override
    @RequirePermits(value = PRMT_READ_U, action = "Find user by ID")
    @LogExecution(operation = OP_READ,
            debugOut = true,
            msgIn = "Finding user by ID, result is expected or will fail",
            msgOut = "Found user by ID",
            errorMsg = "User by ID not found")
    public UserDto findOrNotFound(final UserByIdFinderDto finderDto) {
        final Supplier<RuntimeException> notFound = () -> new UserNotFoundException(finderDto.getId());

        return this.find(finderDto).orElseThrow(notFound);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_U, action = "Create new user")
    @LogExecution(operation = OP_CREATE,
            debugOut = true,
            msgIn = "Creating new user",
            msgOut = "New user created OK",
            errorMsg = "Failed to create new user")
    public UserDto create(UserCreateDto createDto) {
        final ActivityCenterDto activityCenter = this.activityCenterService.findOrNotFound(
                new ActivityCenterByIdFinderDto(createDto.getActivityCenterId())
        );

        final UserCreate create = getMapper(MapUToUserCreate.class).from(createDto);
        create.setState(1);
        create.setPassword(Utiles.textToMD5(createDto.getPassword()));
        create.setForumPassword(generateForumPassword(createDto.getPassword()));
        create.setWorkingHours(DEFAULT_WORKING_HOURS);
        create.setCurrentYearHolidaysCount(activityCenter.getCountryId() == 1 ? HOLIDAYS_COUNT_SPAIN : HOLIDAYS_COUNT_FRANCE);

        final User result = this.userDao.create(create);

        return getMapper(MapUToUserDto.class).from(result);
    }

    @Override
    @Transactional
    @RequirePermits(value = PRMT_EDIT_U, action = "Update user")
    @LogExecution(operation = OP_UPDATE,
            debugOut = true,
            msgIn = "Updating user",
            msgOut = "User updated OK",
            errorMsg = "Failed to update user")
    public UserDto update(final UserUpdateDto updateDto) {
        final UserByIdFinderDto finderDto = new UserByIdFinderDto(updateDto.getId());

        final UserDto userDto = findOrNotFound(finderDto);

        if (StringUtils.isNoneEmpty(updateDto.getForumUsername())) {
            checkAndCreateForumUser(updateDto.getForumUsername(), userDto);
        }

        final UserUpdate update = getMapper(MapUToUserUpdate.class).from(updateDto,
                getMapper(MapUToUserUpdate.class).from(userDto));

        if (StringUtils.isNoneEmpty(updateDto.getPassword())) {
            update.setPassword(Utiles.textToMD5(updateDto.getPassword()));
            update.setForumPassword(generateForumPassword(updateDto.getPassword()));

            if (StringUtils.isNotEmpty(update.getForumUsername())) {
                userForumService.updateUserPassword(update.getEmail(), updateDto.getPassword());
            }
        }

        final User updated = this.userDao.update(update);

        return getMapper(MapUToUserDto.class).from(updated);
    }

    @Override
    @RequirePermits(value = PRMT_EDIT_U, action = "Delete user")
    @LogExecution(operation = OP_DELETE,
            debugOut = true,
            msgIn = "Deleting user",
            msgOut = "User deleted OK",
            errorMsg = "Failed to delete user")
    public void delete(UserDeleteDto deleteDto) {

        final UserByIdFinderDto finderDto = new UserByIdFinderDto(deleteDto.getId());

        findOrNotFound(finderDto);

        final UserDelete delete = getMapper(MapUToUserDelete.class).from(deleteDto);

        this.userDao.delete(delete);
    }

    private String generateForumPassword(final String password) {
        return Base64.getEncoder().encodeToString(CipherUtil.encryptMessage(password.getBytes(), Constants.ENCRYPTION_KEY.getBytes()));
    }

    private void checkAndCreateForumUser(final String forumUsername, final UserDto userDto) {

        if (StringUtils.isNoneEmpty(userDto.getForumUsername())) {
            throw new UserForumAlreadyException(userDto.getForumUsername());
        }

        final byte[] forumPasswordDecoded = Base64.getDecoder().decode(userDto.getForumPassword().getBytes());
        final String plainPassword = new String(CipherUtil.decryptMessage(forumPasswordDecoded, Constants.ENCRYPTION_KEY.getBytes()));

        // TODO: In future, check if username exists in forum db
        this.userForumService.createUser(forumUsername, userDto.getEmail(), plainPassword);
    }
}
