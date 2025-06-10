package com.epm.gestepm.controller;

import com.epm.gestepm.controller.provisional.NoProgrammedShareUserPermissionsResponse;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.deprecated.user.dto.User;
import com.epm.gestepm.modelapi.deprecated.user.service.UserServiceOld;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_ADMIN_ID;
import static com.epm.gestepm.modelapi.common.utils.classes.Constants.ROLE_PL_ID;

@Controller
@AllArgsConstructor
@RequestMapping("/provisional")
public class ProvisionalController {

    private final NoProgrammedShareService noProgrammedShareService;

    private final SubFamilyService subFamilyService;

    private final UserServiceOld userServiceOld;

    @ResponseBody
    @GetMapping("/shares/no-programmed/user-permissions")
    public NoProgrammedShareUserPermissionsResponse findNoProgrammedShareUserPermissions(@RequestParam Integer shareId, @RequestParam Integer userId) {

        final User user = this.userServiceOld.getUserById(userId.longValue());
        final NoProgrammedShareDto share = this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(shareId));

        boolean hasRole = false;
        boolean canClose = false;

        if (share.getFamilyId() != null && share.getSubFamilyId() != null) {
            final List<RoleDTO> subRoles = this.subFamilyService.getSubRolsById(share.getSubFamilyId().longValue());
            final String userLevel = user.getSubRole().getRol();

            hasRole = subRoles.isEmpty() || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));
            canClose = user.getRole().getId().equals(ROLE_PL_ID) || user.getRole().getId().equals(ROLE_ADMIN_ID);
        }

        return new NoProgrammedShareUserPermissionsResponse(hasRole, canClose);
    }
}