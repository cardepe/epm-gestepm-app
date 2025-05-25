package com.epm.gestepm.rest.user;

import com.epm.gestepm.lib.logging.annotation.EnableExecutionLog;
import com.epm.gestepm.lib.logging.annotation.LogExecution;
import com.epm.gestepm.modelapi.common.utils.ModelUtil;
import com.epm.gestepm.modelapi.user.service.UserService;
import com.epm.gestepm.modelapi.userold.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.VIEW;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_VIEW;

@Controller
@AllArgsConstructor
@EnableExecutionLog(layerMarker = VIEW)
public class UserViewController {

    private final UserService userService;

    @ModelAttribute
    public User loadCommonModelView(final Locale locale, final Model model) {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return ModelUtil.loadConstants(locale, model, request); // FIXME: this.setCommonView(model);
    }

    @GetMapping("/users")
    @LogExecution(operation = OP_VIEW)
    public String viewUserPage(final Locale locale, final Model model) {

        this.loadCommonModelView(locale, model);


        return "users";
    }

    @GetMapping("/users/{id}")
    @LogExecution(operation = OP_VIEW)
    public String viewUserDetailPage(@PathVariable final Integer id, final Locale locale, final Model model) {

        final User user = this.loadCommonModelView(locale, model);

        return "user-detail";
    }
}
