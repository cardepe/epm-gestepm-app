package com.epm.gestepm.rest.shares.noprogrammed.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.NoProgrammedShare;
import com.epm.gestepm.restapi.openapi.model.Project;
import com.epm.gestepm.restapi.openapi.model.User;
import com.epm.gestepm.restapi.openapi.model.UserSigning;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import static org.mapstruct.factory.Mappers.getMapper;

@Component("noProgrammedShareResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class NoProgrammedShareResponseDecorator extends BaseResponseDataDecorator<NoProgrammedShare> {

    public static final String NPS_U_EXPAND = "user";

    public static final String NPS_P_EXPAND = "project";

    public static final String NPS_US_EXPAND = "userSigning";

    public static final String NPS_F_EXPAND = "family";

    public static final String NPS_SF_EXPAND = "subFamily";

    public static final String NPS_I_EXPAND = "interventions";

    public static final String NPS_NPSF_EXPAND = "files";

    public NoProgrammedShareResponseDecorator(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating no programmed share response",
            msgOut = "No programmed share decorated OK",
            errorMsg = "Error decorating no programmed share response")
    public void decorate(RestRequest request, NoProgrammedShare data) {

        if (request.getLinks()) {

            final NoProgrammedShareFindRestRequest selfReq = new NoProgrammedShareFindRestRequest(data.getId());
            selfReq.commonValuesFrom(request);
        }

        if (request.hasExpand(NPS_U_EXPAND)) {

            final User user = data.getUser();
            final Integer id = user.getId();

            // final NoProgrammedShareTypeByIdFinderDto finderDto = new NoProgrammedShareTypeByIdFinderDto(id);
            // final NoProgrammedShareTypeDto typeDto = this.productTypeService.findOrNotFound(finderDto);

            // final User response = getMapper(MapPTToNoProgrammedShareTypeResponse.class).from(typeDto);

            final User response = new User().id(id);

            data.setUser(response);
        }

        if (request.hasExpand(NPS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            // TODO.

            final Project response = new Project().id(id);

            data.setProject(response);
        }

        if (request.hasExpand(NPS_US_EXPAND)) {

            final UserSigning userSigning = data.getUserSigning();
            final Integer id = userSigning.getId();

            // TODO.

            final UserSigning response = new UserSigning().id(id);

            data.setUserSigning(response);
        }

        // TODO.
    }
}
