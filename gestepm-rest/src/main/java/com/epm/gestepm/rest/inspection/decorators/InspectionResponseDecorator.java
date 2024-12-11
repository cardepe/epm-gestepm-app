package com.epm.gestepm.rest.inspection.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.rest.inspection.request.InspectionFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.Inspection;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("inspectionResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class InspectionResponseDecorator extends BaseResponseDataDecorator<Inspection> {

    public static final String I_MATERIALS_EXPAND = "materials";

    public static final String I_FILES_EXPAND = "files";
    
    public InspectionResponseDecorator(ApplicationContext applicationContext) {
        super(applicationContext);
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

        /*if (request.hasExpand(I_MATERIALS_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getName()).surnames(userDto.getSurnames());

            data.setUser(response);
        }

        if (request.hasExpand(I_FILES_EXPAND) && data.() != null) {

            final SubFamily subFamily = data.getSubFamily();
            final Integer id = subFamily.getId();

            // FIXME
            final com.epm.gestepm.modelapi.subfamily.dto.SubFamily subFamilyDto = this.subFamilyService.getById(Long.valueOf(id));
            final SubFamily response = new SubFamily()
                    .id(id)
                    .name(request.getLocale().equals("es") ? subFamilyDto.getNameES() : subFamilyDto.getNameFR());

            data.setSubFamily(response);
        }*/
    }
}
