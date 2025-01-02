package com.epm.gestepm.rest.personalexpense.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.personalexpense.dto.PersonalExpenseFileDto;
import com.epm.gestepm.modelapi.personalexpense.dto.filter.PersonalExpenseFileFilterDto;
import com.epm.gestepm.modelapi.personalexpense.service.PersonalExpenseFileService;
import com.epm.gestepm.rest.personalexpense.mappers.MapPEFToFileResponse;
import com.epm.gestepm.rest.personalexpense.request.PersonalExpenseFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.PersonalExpense;
import com.epm.gestepm.restapi.openapi.model.ShareFile;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("personalExpenseResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class PersonalExpenseResponseDecorator extends BaseResponseDataDecorator<PersonalExpense> {

    public static final String PE_FILES_EXPAND = "files";

    private final PersonalExpenseFileService personalExpenseFileService;
    
    public PersonalExpenseResponseDecorator(ApplicationContext applicationContext, PersonalExpenseFileService personalExpenseFileService) {
        super(applicationContext);
        this.personalExpenseFileService = personalExpenseFileService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating personal expense response",
            msgOut = "Personal expense decorated OK",
            errorMsg = "Error decorating personal expense response")
    public void decorate(RestRequest request, PersonalExpense data) {

        if (request.getLinks()) {

            final PersonalExpenseFindRestRequest selfReq = new PersonalExpenseFindRestRequest(data.getId(), data.getPersonalExpenseSheet().getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(PE_FILES_EXPAND) && data.getFiles() != null && !data.getFiles().isEmpty()) {
            final List<Integer> fileIds = data.getFiles().stream().map(ShareFile::getId).collect(Collectors.toList());

            final PersonalExpenseFileFilterDto filterDto = new PersonalExpenseFileFilterDto();
            filterDto.setIds(fileIds);

            final List<PersonalExpenseFileDto> files = this.personalExpenseFileService.list(filterDto);

            data.setFiles(getMapper(MapPEFToFileResponse.class).from(files));
        }
    }
}
