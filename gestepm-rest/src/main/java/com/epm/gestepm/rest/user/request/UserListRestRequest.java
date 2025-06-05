package com.epm.gestepm.rest.user.request;

import com.epm.gestepm.lib.controller.RestRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserListRestRequest extends RestRequest {

    private List<Integer> ids;

    private String nameContains;

    private String email;

    private String password;

    private List<Integer> activityCenterIds;

    private Integer state;

    private List<Integer> signingIds;

    private List<Integer> roleIds;

    private List<Integer> levelIds;

}
