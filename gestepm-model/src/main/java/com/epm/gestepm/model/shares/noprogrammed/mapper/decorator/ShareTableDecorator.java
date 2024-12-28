package com.epm.gestepm.model.shares.noprogrammed.mapper.decorator;

import com.epm.gestepm.modelapi.common.config.ApplicationContextProvider;
import com.epm.gestepm.modelapi.inspection.dto.InspectionDto;
import com.epm.gestepm.modelapi.interventionshare.dto.ShareTableDTO;
import com.epm.gestepm.modelapi.project.dto.Project;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.NoProgrammedShareDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.dto.finder.NoProgrammedShareByIdFinderDto;
import com.epm.gestepm.modelapi.shares.noprogrammed.service.NoProgrammedShareService;
import com.epm.gestepm.modelapi.user.dto.User;
import com.epm.gestepm.modelapi.user.service.UserService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public abstract class ShareTableDecorator {

    @AfterMapping
    public void transform(@MappingTarget ShareTableDTO shareTable, InspectionDto inspection) {
        final NoProgrammedShareDto noProgrammedShare = ApplicationContextProvider.getBean(NoProgrammedShareService.class)
                .findOrNotFound(new NoProgrammedShareByIdFinderDto(inspection.getShareId()));
        final Project project = ApplicationContextProvider.getBean(ProjectService.class)
                .getProjectById(noProgrammedShare.getProjectId().longValue());
        final User user = ApplicationContextProvider.getBean(UserService.class)
                .getUserById(noProgrammedShare.getUserId().longValue());

        shareTable.setShareType("is");
        shareTable.setProjectId(project.getName());
        shareTable.setUsername(user.getFullName());
    }
}
