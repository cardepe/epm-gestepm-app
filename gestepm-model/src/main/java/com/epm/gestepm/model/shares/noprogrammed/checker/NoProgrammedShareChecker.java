package com.epm.gestepm.model.shares.noprogrammed.checker;

import com.epm.gestepm.modelapi.project.dto.ProjectTypeDto;
import com.epm.gestepm.modelapi.project.exception.ProjectIsNotStationException;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserNotFoundException;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import com.epm.gestepm.modelapi.project.dto.ProjectDto;
import com.epm.gestepm.modelapi.project.dto.finder.ProjectByIdFinderDto;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareStateEnumDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.updater.NoProgrammedShareUpdateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_ADMIN_ID;
import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_PL_ID;

@Component
@AllArgsConstructor
public class NoProgrammedShareChecker {

    private final ProjectService projectService;

    private final UserServiceOld userServiceOld;

    public void checker(final NoProgrammedShareCreateDto dto) {
        this.checker(dto.getUserId(), dto.getProjectId(), dto, null);
    }

    public void checker(final NoProgrammedShareUpdateDto updateDto, final NoProgrammedShareDto dto) {
        this.checker(updateDto.getUserId(), dto.getProjectId(), null, updateDto);
    }

    private void checker(final Integer userId, final Integer projectId, final NoProgrammedShareCreateDto createDto, final NoProgrammedShareUpdateDto updateDto) {
        final boolean closeShare = updateDto != null && NoProgrammedShareStateEnumDto.CLOSED.equals(updateDto.getState());

        final Supplier<RuntimeException> userNotFound = () -> new UserNotFoundException(userId);
        final User user = Optional.ofNullable(this.userServiceOld.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        if (closeShare) {
            this.validateCloseSharePermission(user);
        } else if (createDto != null) {
            this.validateProject(projectId);
        }
    }

    private void validateCloseSharePermission(User user) {
        if (!user.getRole().getId().equals(ROLE_PL_ID) && !user.getRole().getId().equals(ROLE_ADMIN_ID)) {
            throw new NoProgrammedShareForbiddenException(user.getId().intValue(), null);
        }
    }

    private void validateProject(final Integer projectId) {
        final ProjectDto project = this.projectService.findOrNotFound(new ProjectByIdFinderDto(projectId));

        if (!ProjectTypeDto.STATION.equals(project.getType())) {
            throw new ProjectIsNotStationException(projectId);
        }
    }
}
