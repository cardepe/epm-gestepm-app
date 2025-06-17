package com.epm.gestepm.rest.personalexpensesheet.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.deprecated.project.service.ProjectService;
import com.epm.gestepm.rest.personalexpensesheet.request.PersonalExpenseSheetFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.PersonalExpenseSheet;
import com.epm.gestepm.restapi.openapi.model.Project;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("personalExpenseSheetResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class PersonalExpenseSheetResponseDecorator extends BaseResponseDataDecorator<PersonalExpenseSheet> {

    public static final String PES_P_EXPAND = "project";

    public static final String PES_PE_EXPAND = "personalExpenses";

    // private final PersonalExpenseService personalExpenseService;

    private final ProjectService projectService;
    
    public PersonalExpenseSheetResponseDecorator(ApplicationContext applicationContext, ProjectService projectService) {  // , PersonalExpenseService personalExpenseService) {
        super(applicationContext);
        // this.personalExpenseService = personalExpenseService;
        this.projectService = projectService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating personal expense sheet response",
            msgOut = "Personal expense sheet decorated OK",
            errorMsg = "Error decorating personal expense sheet response")
    public void decorate(RestRequest request, PersonalExpenseSheet data) {

        if (request.getLinks()) {

            final PersonalExpenseSheetFindRestRequest selfReq = new PersonalExpenseSheetFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(PES_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.deprecated.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }

        /*if (request.hasExpand(I_FILES_EXPAND) && data.getFiles() != null && !data.getFiles().isEmpty()) {
            final Set<ShareFile> personal expense sheetFiles = data.getFiles();

            final Set<ShareFile> response = personal expense sheetFiles.stream()
                    .map(personal expense sheetFile -> new PersonalExpenseSheetFileByIdFinderDto(personal expense sheetFile.getId()))
                    .map(this.personal expense sheetFileService::findOrNotFound)
                    .map(personal expense sheetFile -> getMapper(MapIFToFileResponse.class).from(personal expense sheetFile))
                    .collect(Collectors.toSet());

            data.setFiles(response);
        }*/
    }
}
