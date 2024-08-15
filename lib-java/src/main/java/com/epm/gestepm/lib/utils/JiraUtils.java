package com.epm.gestepm.lib.utils;

public class JiraUtils {

    private JiraUtils() {
    }

    public static Integer numberOfJiraIssueKey(String issueKey) {

        final String[] split = issueKey.split("-");

        return Integer.parseInt(split[1]);
    }

}
