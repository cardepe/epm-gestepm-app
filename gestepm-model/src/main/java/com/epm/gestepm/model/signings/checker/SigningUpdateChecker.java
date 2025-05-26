package com.epm.gestepm.model.signings.checker;

import com.epm.gestepm.modelapi.common.utils.classes.Constants;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.signings.exception.SigningForbiddenException;
import com.epm.gestepm.modelapi.userold.dto.User;
import com.epm.gestepm.modelapi.userold.service.UserServiceOld;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class SigningUpdateChecker {

    private final UserServiceOld userServiceOld;

    public void checker(final Integer userId, final Integer projectId) {
        final Integer currentUserId = userId != null ? userId : this.getCurrentUserId();
        final User user = this.userServiceOld.getUserById(currentUserId.longValue());
        final Boolean isAdmin = Constants.ROLE_ADMIN.equals(user.getRole().getRoleName());
        final Boolean isProjectTL = Constants.ROLE_PL.equals(user.getRole().getRoleName())
                && user.getBossProjects().stream().map(Project::getId).collect(Collectors.toList()).contains(projectId.longValue());
        final boolean canUpdate = isAdmin || isProjectTL;

        if (!canUpdate) {
            throw new SigningForbiddenException(user.getId().intValue());
        }
    }

    private Integer getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final User user = (User) authentication.getDetails();

        return user.getId().intValue();
    }
}
