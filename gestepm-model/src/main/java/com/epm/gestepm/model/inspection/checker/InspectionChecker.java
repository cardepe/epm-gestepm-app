package com.epm.gestepm.model.inspection.checker;

import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class InspectionChecker {

    private final SubFamilyService subFamilyService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public InspectionChecker(SubFamilyService subFamilyService, UserService userService, UserSigningService userSigningService) {
        this.subFamilyService = subFamilyService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    public void checker(final NoProgrammedShareDto noProgrammedShare, final InspectionCreateDto dto) {
        this.checker(dto.getFirstTechnicalId(), noProgrammedShare.getSubFamilyId(), dto);
    }

    public void checker(final NoProgrammedShareDto noProgrammedShare, final InspectionUpdateDto inspection) {
        this.checker(inspection.getUserId(), noProgrammedShare.getSubFamilyId(), null);
    }

    private void checker(final Integer userId, final Integer subFamilyId, final InspectionCreateDto createDto) {
        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(userId);
        final User user = Optional.ofNullable(this.userService.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        final UserSigning userSigning = this.userSigningService.getByUserIdAndEndDate(userId.longValue(), null);
        final List<RoleDTO> subRoles = subFamilyId != null
                ? this.subFamilyService.getSubRolsById(subFamilyId.longValue())
                : new ArrayList<>();

        final String userLevel = user.getSubRole().getRol();
        final boolean hasRole = subRoles.isEmpty()
                || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));
        final boolean hasSigning = userSigning != null || Utiles.havePrivileges(userLevel);

        if (!hasRole || !hasSigning) {
            throw new NoProgrammedShareForbiddenException(userId, user.getSubRole().getRol());
        }

        if (userSigning != null && createDto != null) {
            createDto.setUserSigningId(userSigning.getId().intValue());
        }
    }
}
