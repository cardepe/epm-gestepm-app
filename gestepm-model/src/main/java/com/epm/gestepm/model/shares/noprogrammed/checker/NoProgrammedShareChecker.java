package com.epm.gestepm.model.shares.noprogrammed.checker;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.exception.ProjectByIdNotFoundException;
import com.epm.gestepm.modelapi.project.exception.ProjectIsNotStationException;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.creator.NoProgrammedShareCreateDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Supplier;

@Component
public class NoProgrammedShareChecker {

    private final ProjectService projectService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public NoProgrammedShareChecker(ProjectService projectService, UserService userService, UserSigningService userSigningService) {
        this.projectService = projectService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    public void checker(final Integer userId, final Integer projectId, final NoProgrammedShareCreateDto dto) {
        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(userId);
        final User user = Optional.ofNullable(this.userService.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        final UserSigning userSigning = this.userSigningService.getByUserIdAndEndDate(userId.longValue(), null);

        if (userSigning == null && !Utiles.havePrivileges(user.getSubRole().getRol())) {
            throw new NoProgrammedShareForbiddenException(userId, user.getSubRole().getRol());
        }

        if (userSigning != null && dto != null) {
            dto.setUserSigningId(userId);
        }

        final Supplier<RuntimeException> projectNotFound = () -> new ProjectByIdNotFoundException(projectId);
        final Project project = Optional.ofNullable(this.projectService.getProjectById(projectId.longValue()))
                .orElseThrow(projectNotFound);

        if (project.getStation() != 1) {
            throw new ProjectIsNotStationException(project.getId().intValue());
        }
    }
}
