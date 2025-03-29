package com.epm.gestepm.rest.timecontrol.decorators;

import com.epm.gestepm.lib.controller.RestRequest;
import com.epm.gestepm.lib.controller.decorator.BaseResponseDataDecorator;
import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.restapi.openapi.model.TimeControl;
import com.epm.gestepm.restapi.openapi.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.DELEGATOR;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;

@Component("timeResponseDecorator")
@EnableExecutionLog(layerMarker = DELEGATOR)
public class TimeControlResponseDecorator extends BaseResponseDataDecorator<TimeControl> {

    public static final String TC_U_EXPAND = "user";

    private final UserService userService;
    
    public TimeControlResponseDecorator(ApplicationContext applicationContext, UserService userService) {
        super(applicationContext);
        this.userService = userService;
    }

    @Override
    @LogExecution(operation = OP_PROCESS,
            msgIn = "Decorating time control response",
            msgOut = "Time control decorated OK",
            errorMsg = "Error decorating time control response")
    public void decorate(RestRequest request, TimeControl data) {

        if (request.hasExpand(TC_U_EXPAND)) {

            final User user = data.getUser();
            final Integer id = user.getId();

            final com.epm.gestepm.modelapi.user.dto.User userDto = this.userService.getUserById(Long.valueOf(id));
            final User response = new User().id(id).name(userDto.getName());

            data.setUser(response);
        }
    }
}
