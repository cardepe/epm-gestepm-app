package com.epm.gestepm.controller;

import com.epm.gestepm.controller.provisional.NoProgrammedShareUserPermissionsResponse;
import com.epm.gestepm.modelapi.common.utils.Utiles;
import com.epm.gestepm.modelapi.common.utils.datatables.DataTableResults;
import com.epm.gestepm.modelapi.project.dto.ProjectTableDTO;
import com.epm.gestepm.modelapi.role.dto.RoleDTO;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.usersigning.dto.UserSigning;
import com.epm.gestepm.modelapi.usersigning.service.UserSigningService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/provisional")
public class ProvisionalController {

    private final NoProgrammedShareService noProgrammedShareService;

    private final SubFamilyService subFamilyService;

    private final UserService userService;

    private final UserSigningService userSigningService;

    public ProvisionalController(NoProgrammedShareService noProgrammedShareService, SubFamilyService subFamilyService, UserService userService, UserSigningService userSigningService) {
        this.noProgrammedShareService = noProgrammedShareService;
        this.subFamilyService = subFamilyService;
        this.userService = userService;
        this.userSigningService = userSigningService;
    }

    @ResponseBody
    @GetMapping("/shares/no-programmed/user-permissions")
    public NoProgrammedShareUserPermissionsResponse findNoProgrammedShareUserPermissions(@RequestParam Integer shareId, @RequestParam Integer userId) {

        final User user = this.userService.getUserById(userId.longValue());
        final NoProgrammedShareDto share = this.noProgrammedShareService.findOrNotFound(new NoProgrammedShareByIdFinderDto(shareId));

        boolean hasRole = false;
        boolean hasSigning = false;

        if (share.getFamilyId() != null && share.getSubFamilyId() != null) {
            final List<RoleDTO> subRoles = this.subFamilyService.getSubRolsById(share.getSubFamilyId().longValue());
            final UserSigning userSigning = this.userSigningService.getByUserIdAndEndDate(user.getId(), null);
            final String userLevel = user.getSubRole().getRol();

            hasRole = subRoles.isEmpty()
                    || subRoles.stream().anyMatch(subRole -> subRole.getName().equals(userLevel));
            hasSigning = userSigning != null || Utiles.havePrivileges(userLevel);
        }

        return new NoProgrammedShareUserPermissionsResponse(hasRole, hasSigning);
    }
}