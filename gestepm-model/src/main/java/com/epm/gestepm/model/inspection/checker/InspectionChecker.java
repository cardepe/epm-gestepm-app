package com.epm.gestepm.model.inspection.checker;

import com.epm.gestepm.modelapi.inspection.dto.ActionEnumDto;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.inspection.dto.creator.InspectionCreateDto;
import com.epm.gestepm.modelapi.inspection.dto.updater.InspectionUpdateDto;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.exception.NoProgrammedShareForbiddenException;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.exception.UserByIdNotFoundException;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
@AllArgsConstructor
public class InspectionChecker {

    private final SubFamilyService subFamilyService;

    private final UserServiceOld userServiceOld;

    public void checker(final NoProgrammedShareDto noProgrammedShare, final InspectionCreateDto dto) {
        this.checker(dto.getFirstTechnicalId(), noProgrammedShare.getSubFamilyId(), dto.getAction(), dto);
    }

    public void checker(final NoProgrammedShareDto noProgrammedShare, final InspectionDto inspection, final InspectionUpdateDto updateDto) {
        this.checker(updateDto.getUserId() != null ? updateDto.getUserId() : updateDto.getFirstTechnicalId(),
                noProgrammedShare.getSubFamilyId(),
                inspection.getAction(),
                null
        );
    }

    private void checker(final Integer userId, final Integer subFamilyId, final ActionEnumDto action, final InspectionCreateDto createDto) {
        final Supplier<RuntimeException> userNotFound = () -> new UserByIdNotFoundException(userId);
        final User user = Optional.ofNullable(this.userServiceOld.getUserById(userId.longValue()))
                .orElseThrow(userNotFound);

        final List<RoleDTO> subRoles = subFamilyId != null
                ? this.subFamilyService.getSubRolsById(subFamilyId.longValue())
                : new ArrayList<>();

        final String userLevel = user.getSubRole().getRol();
        final boolean hasRole = subRoles.isEmpty()
                || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));

        this.checkPermissions(action, hasRole, userId, user.getSubRole().getRol());
    }

    public void checkPermissions(final ActionEnumDto action, final boolean hasRole, final Integer userId, final String level) {
        if (ActionEnumDto.DIAGNOSIS.equals(action)) {
            handleCondition(!hasRole, () -> new NoProgrammedShareForbiddenException(userId, level));
        }
    }

    private void handleCondition(final boolean condition, final Supplier<RuntimeException> exceptionSupplier) {
        if (condition) {
            throw exceptionSupplier.get();
        }
    }
}
