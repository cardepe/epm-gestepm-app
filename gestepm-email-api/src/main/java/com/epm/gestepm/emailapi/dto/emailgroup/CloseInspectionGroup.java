package com.epm.gestepm.emailapi.dto.emailgroup;

import com.epm.gestepm.emailapi.dto.Attachment;
import com.epm.gestepm.emailapi.dto.EmailGroup;
import com.epm.gestepm.emailapi.util.EmailUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections4.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class CloseInspectionGroup extends EmailGroup {

    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @NotNull
    private Integer noProgrammedShareId;

    @NotNull
    private Integer inspectionId;

    @NotNull
    private String fullName;

    @NotNull
    private String projectName;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime closedAt;

    @NotNull
    private String action;

    @NotNull
    private String forumUrl;

    @NotNull
    private String forumTitle;

    @NotNull
    private List<Attachment> attachments;

    @Override
    public String getEmailTemplate() {
        return "close-inspection";
    }

    @Override
    public Map<String, Object> buildVariables() {
        final Map<String, Object> variables = new HashMap<>();
        variables.put("noProgrammedShareId", noProgrammedShareId);
        variables.put("inspectionId", this.inspectionId);
        variables.put("fullName", this.fullName);
        variables.put("projectName", this.projectName);
        variables.put("createdAt", EmailUtils.transform(this.createdAt, DATE_TIME_FORMAT));
        variables.put("closedAt", EmailUtils.transform(this.closedAt, DATE_TIME_FORMAT));
        variables.put("action", this.action);
        variables.put("forumTitle", this.forumTitle);
        variables.put("forumUrl", this.forumUrl);
        variables.put("bodyTemplate", this.getEmailTemplate());
        return variables;
    }

    @Override
    public Boolean containsAttachments() {
        return Boolean.TRUE;
    }

    @Override
    public List<Attachment> getAttachments() {
        return this.attachments;
    }

}
