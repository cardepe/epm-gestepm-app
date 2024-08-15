package com.epm.gestepm.lib.controller.error;

import java.util.HashMap;
import java.util.Map;

public class APIError {

    private String errorTraceId;

    private Integer code;

    private String title;

    private String detail;

    private Integer httpStatus;

    private Map<String, String> help;

    public String getErrorTraceId() {
        return errorTraceId;
    }

    public void setErrorTraceId(String errorTraceId) {
        this.errorTraceId = errorTraceId;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, String> getHelp() {
        return help;
    }

    public void setHelp(Map<String, String> help) {
        this.help = help;
    }

    public void putHelp(String key, String value) {

        if (this.help == null) {
            this.help = new HashMap<>();
        }

        this.help.put(key, value);
    }

}
