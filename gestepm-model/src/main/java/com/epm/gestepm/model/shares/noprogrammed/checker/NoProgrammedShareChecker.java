package com.epm.gestepm.model.shares.noprogrammed.checker;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.exception.ProjectIsNotStationException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_ADMIN_ID;
import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_PL_ID;

@Component
public class NoProgrammedShareChecker {

    private static final Integer STATION_ACTIVE = 1;

    private final ProjectService projectService;

    private final SubFamilyService subFamilyService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public NoProgrammedShareChecker(final ProjectService projectService, final SubFamilyService subFamilyService,
                                    final UserService userService, final UserSigningService userSigningService) {
        this.projectService = projectService;
        this.subFamilyService = subFamilyService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    public void checker(final NoProgrammedShareCreateDto dto) {
        this.checker(dto.getUserId(), dto.getProjectId(), null, dto, null);
    }

    public void checker(final NoProgrammedShareUpdateDto updateDto, final NoProgrammedShareDto dto) {
        this.checker(updateDto.getUserId(), dto.getProjectId(), updateDto.getSubFamilyId(), null, updateDto);
    }

    private void checker(final Integer userId, final Integer projectId, final Integer subFamilyId,
                         final NoProgrammedShareCreateDto createDto, final NoProgrammedShareUpdateDto updateDto) {
        final boolean closeShare = updateDto != null && NoProgrammedShareStateEnumDto.CLOSED.equals(updateDto.getState());

        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(userId);
        final User user = Optional.ofNullable(this.userService.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        if (closeShare) {
            this.validateCloseSharePermission(user);
        } else {
            this.validateUserAccess(user, userId, subFamilyId);

            if (createDto != null) {
                this.validateProject(projectId);
                this.populateCreateDto(createDto, userId);
            }
        }
    }

    private void validateCloseSharePermission(User user) {
        if (!user.getRole().getId().equals(ROLE_PL_ID) && !user.getRole().getId().equals(ROLE_ADMIN_ID)) {
            throw new NoProgrammedShareForbiddenException(user.getId().intValue(), null);
        }
    }

    private void validateUserAccess(final User user, final Integer userId, final Integer subFamilyId) {
        final UserSigning userSigning = userSigningService.getByUserIdAndEndDate(userId.longValue(), null);
        final List<RoleDTO> subRoles = subFamilyId != null
                ? subFamilyService.getSubRolsById(subFamilyId.longValue())
                : Collections.emptyList();

        final boolean hasRole = subRoles.isEmpty() || subRoles.stream()
                .anyMatch(subRole -> subRole.getName().equals(user.getSubRole().getRol()));
        final boolean hasSigning = userSigning != null || Utiles.havePrivileges(user.getSubRole().getRol());

        if (!hasRole || !hasSigning) {
            throw new NoProgrammedShareForbiddenException(userId, user.getSubRole().getRol());
        }
    }

    private void populateCreateDto(final NoProgrammedShareCreateDto createDto, final Integer userId) {
        final UserSigning userSigning = userSigningService.getByUserIdAndEndDate(userId.longValue(), null);

        if (userSigning != null) {
            createDto.setUserSigningId(userSigning.getId().intValue());
        }
    }

    private void validateProject(final Integer projectId) {
        final Project project = Optional.ofNullable(projectService.getProjectById(projectId.longValue()))
                .orElseThrow(() -> new ProjectByIdNotFoundException(projectId));

        if (project.getStation() != STATION_ACTIVE) {
            throw new ProjectIsNotStationException(project.getId().intValue());
        }
    }
}
