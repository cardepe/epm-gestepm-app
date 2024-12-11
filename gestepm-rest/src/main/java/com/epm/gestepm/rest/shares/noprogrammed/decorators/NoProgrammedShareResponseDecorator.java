package com.epm.gestepm.rest.shares.noprogrammed.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.family.service.FamilyService;
import com.epm.gestepm.modelapi.project.service.ProjectService;
import com.epm.gestepm.modelapi.subfamily.service.SubFamilyService;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.rest.shares.noprogrammed.request.NoProgrammedShareFindRestRequest;
import com.epm.gestepm.restapi.openapi.model.*;
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

    public static final String NPS_I_EXPAND = "inspections";

    public static final String NPS_NPSF_EXPAND = "files";

    private final FamilyService familyService;

    private final ProjectService projectService;

    private final SubFamilyService subFamilyService;

    private final UserService userService;

    public NoProgrammedShareResponseDecorator(ApplicationContext applicationContext, FamilyService familyService, ProjectService projectService, SubFamilyService subFamilyService, UserService userService) {
        super(applicationContext);
        this.familyService = familyService;
        this.projectService = projectService;
        this.subFamilyService = subFamilyService;
        this.userService = userService;
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

        if (request.hasExpand(NPS_U_EXPAND) && data.getUser() != null) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getName()).surnames(userDto.getSurnames());

            data.setUser(response);
        }

        if (request.hasExpand(NPS_P_EXPAND)) {

            final Project project = data.getProject();
            final Integer id = project.getId();

            final com.epm.gestepm.modelapi.project.dto.Project projectDto = this.projectService.getProjectById(Long.valueOf(id));
            final Project response = new Project().id(id).name(projectDto.getName());

            data.setProject(response);
        }

        if (request.hasExpand(NPS_US_EXPAND)) {

            final UserSigning userSigning = data.getUserSigning();
            final Integer id = userSigning.getId();

            // TODO.

            final UserSigning response = new UserSigning().id(id);

            data.setUserSigning(response);
        }

        if (request.hasExpand(NPS_F_EXPAND) && data.getFamily() != null) {

            final Family family = data.getFamily();
            final Integer id = family.getId();

            // FIXME
            final com.epm.gestepm.modelapi.family.dto.Family familyDto = this.familyService.getById(Long.valueOf(id));
            final Family response = new Family()
                    .id(id)
                    .name(request.getLocale().equals("es") ? familyDto.getNameES() : familyDto.getNameFR());

            data.setFamily(response);
        }

        if (request.hasExpand(NPS_SF_EXPAND) && data.getSubFamily() != null) {

            final SubFamily subFamily = data.getSubFamily();
            final Integer id = subFamily.getId();

            // FIXME
            final com.epm.gestepm.modelapi.subfamily.dto.SubFamily subFamilyDto = this.subFamilyService.getById(Long.valueOf(id));
            final SubFamily response = new SubFamily()
                    .id(id)
                    .name(request.getLocale().equals("es") ? subFamilyDto.getNameES() : subFamilyDto.getNameFR());

            data.setSubFamily(response);
        }
    }
}
