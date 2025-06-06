package com.epm.gestepm.modelapi.user.service;

import com.epm.gestepm.lib.types.Page;
import com.epm.gestepm.modelapi.user.dto.UserDto;
import com.epm.gestepm.modelapi.user.dto.creator.UserCreateDto;
import com.epm.gestepm.modelapi.user.dto.deleter.UserDeleteDto;
import com.epm.gestepm.modelapi.user.dto.filter.UserFilterDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByEmailAndPasswordFinderDto;
import com.epm.gestepm.modelapi.user.dto.finder.UserByIdFinderDto;
import com.epm.gestepm.modelapi.user.dto.updater.UserUpdateDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<@Valid UserDto> list(@Valid UserFilterDto filterDto);

    Page<@Valid UserDto> list(@Valid UserFilterDto filterDto, Long offset, Long limit);
    
    Optional<@Valid UserDto> find(@Valid UserByIdFinderDto finderDto);

    Optional<@Valid UserDto> find(@Valid UserByEmailAndPasswordFinderDto finderDto);

    @Valid
    UserDto findOrNotFound(@Valid UserByIdFinderDto finderDto);

    @Valid
    UserDto findOrNotFound(@Valid UserByEmailAndPasswordFinderDto finderDto);

    @Valid
    UserDto create(@Valid UserCreateDto createDto);

    @Valid
    UserDto update(@Valid UserUpdateDto updateDto);

    void delete(@Valid UserDeleteDto deleteDto);

}
