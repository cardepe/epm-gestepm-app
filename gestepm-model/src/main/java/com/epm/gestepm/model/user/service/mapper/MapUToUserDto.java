package com.epm.gestepm.model.user.service.mapper;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.model.user.dao.entity.User;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface MapUToUserDto {

    UserDto from(User entity);

    List<UserDto> from(List<User> list);

    default Page<UserDto> from(Page<User> page) {
        return new Page<>(page.cursor(), from(page.getContent()));
    }

}
