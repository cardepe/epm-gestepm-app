package com.epm.gestepm.rest.inspection.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.inspection.dto.InspectionFileDto;
import com.epm.gestepm.modelapi.inspection.dto.filter.InspectionFileFilterDto;
import com.epm.gestepm.modelapi.inspection.service.InspectionFileService;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.inspection.mappers.MapIFToFileResponse;
import com.epm.gestepm.rest.inspection.request.InspectionFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.Inspection;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("inspectionResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class InspectionResponseDecorator extends BaseResponseDataDecorator<Inspection> {

    public static final String I_FILES_EXPAND = "files";

    public static final String I_FIRST_TECHNICAL_EXPAND = "firstTechnical";

    public static final String I_SECOND_TECHNICAL_EXPAND = "secondTechnical";

    private final InspectionFileService inspectionFileService;

    private final UserService userService;
    
    public InspectionResponseDecorator(ApplicationContext applicationContext, InspectionFileService inspectionFileService, UserService userService) {
        super(applicationContext);
        this.inspectionFileService = inspectionFileService;
        this.userService = userService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating inspection response",
            msgOut = "Inspection decorated OK",
            errorMsg = "Error decorating inspection response")
    public void decorate(RestRequest request, Inspection data) {

        if (request.getLinks()) {

            final InspectionFindRestRequest selfReq = new InspectionFindRestRequest(data.getId(), data.getShare().getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(I_FILES_EXPAND) && data.getFiles() != null && !data.getFiles().isEmpty()) {
            final List<Integer> fileIds = data.getFiles().stream().map(ShareFile::getId).collect(Collectors.toList());

            final InspectionFileFilterDto filterDto = new InspectionFileFilterDto();
            filterDto.setIds(fileIds);

            final List<InspectionFileDto> files = this.inspectionFileService.list(filterDto);

            data.setFiles(getMapper(MapIFToFileResponse.class).from(files));
        }

        if (request.hasExpand(I_FIRST_TECHNICAL_EXPAND) && data.getFirstTechnical() != null && data.getFirstTechnical().getId() != null) {
            final Integer firstTechnicalId = data.getFirstTechnical().getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(firstTechnicalId));
            final User response = new User().id(firstTechnicalId).name(userDto.getName()).surnames(userDto.getSurnames());

            data.setFirstTechnical(response);
        }

        if (request.hasExpand(I_SECOND_TECHNICAL_EXPAND) && data.getSecondTechnical() != null && data.getSecondTechnical().getId() != null) {
            final Integer secondTechnicalId = data.getSecondTechnical().getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(secondTechnicalId));
            final User response = new User().id(secondTechnicalId).name(userDto.getName()).surnames(userDto.getSurnames());

            data.setSecondTechnical(response);
        }
    }
}
