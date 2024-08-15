/*
package com.epm.gestepm.rest.common;

import com.epm.products.appconfigapi.role.dto.RoleDto;
import com.epm.products.appconfigapi.role.dto.finder.RoleByIdFinderDto;
import com.epm.products.appconfigapi.role.service.RoleService;
import com.epm.products.lib.user.UserProvider;
import com.epm.products.lib.user.data.UserLogin;
import com.epm.products.modelapi.user.dto.UserDto;
import com.epm.products.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.products.modelapi.user.service.UserService;
import org.springframework.ui.Model;

public abstract class BaseViewController {

    private final RoleService roleService;

    private final UserProvider userProvider;

    private final UserService userService;

    protected BaseViewController(RoleService roleService, UserProvider userProvider, UserService userService) {
        this.roleService = roleService;
        this.userProvider = userProvider;
        this.userService = userService;
    }

    protected void setCommonView(Model model) {

        final UserLogin id = userProvider.getOrFail(UserLogin.class);

        final UserByIdFinderDto finderDto = new UserByIdFinderDto(Integer.parseInt(id.getValue()));

        final UserDto userDto = this.userService.findOrNotFound(finderDto);

        model.addAttribute("userDto", userDto);

        final Integer roleId = userDto.getRoleIds().get(0);

        final RoleByIdFinderDto roleFinderDto = new RoleByIdFinderDto(roleId);

        final RoleDto roleDto = this.roleService.findOrNotFound(roleFinderDto);

        model.addAttribute("roleDto", roleDto);
    }
}
*/
