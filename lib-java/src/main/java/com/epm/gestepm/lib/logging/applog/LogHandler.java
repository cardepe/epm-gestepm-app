package com.epm.gestepm.lib.logging.applog;

import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_EXCEPTION_CLASS_NAME;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_EXCEPTION_MSG;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_EXECUTION_TIME;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_FLOW_MARKER;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_LAYER;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_LEVEL;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_MSG;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_OPERATION;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import java.util.Map;
import com.epm.gestepm.lib.logging.constants.LogFlowMarker;
import com.epm.gestepm.lib.logging.constants.LogLayerMarkers;
import ch.qos.logback.classic.Level;
import org.slf4j.Logger;

public class LogHandler {

    private final Logger logger;

    private final LogContext dataMap;

    public LogHandler(final Logger logger) {
        this.logger = logger;
        this.dataMap = new LogContext();
    }

    public LogContext getDataMap() {
        return this.dataMap;
    }

    public LogHandler data(final String dataKey, final Object dataValue) {
        this.dataMap.put(dataKey, dataValue);
        return this;
    }

    public LogHandler data(final Map<String, Object> dataMap) {
        dataMap.forEach(this::data);
        return this;
    }

    public LogHandler msg(final String msg, final String... args) {
        String finalMsg = (msg != null) && !msg.isBlank() ? msg : "";
        finalMsg = String.format(finalMsg, args);
        this.data(LOG_MSG, finalMsg);
        return this;
    }

    public LogHandler operation(final String operation) {
        this.data(LOG_OPERATION, operation != null ? operation : OP_PROCESS);
        return this;
    }

    public LogHandler layer(final String layerMarker) {
        this.data(LOG_CODE_TRACE_ON_LAYER, layerMarker != null ? layerMarker : LogLayerMarkers.UNKNOWN);
        return this;
    }

    public LogHandler flow(final String flowMarker) {
        this.data(LOG_CODE_TRACE_FLOW_MARKER, flowMarker != null ? flowMarker : LogFlowMarker.FLOW_MARK_LINE);
        return this;
    }

    public LogHandler flowIn() {
        this.data(LOG_CODE_TRACE_FLOW_MARKER, LogFlowMarker.FLOW_MARK_IN);
        return this;
    }

    public LogHandler flowOut() {
        this.data(LOG_CODE_TRACE_FLOW_MARKER, LogFlowMarker.FLOW_MARK_OUT);
        return this;
    }

    public LogHandler flowErr() {
        this.data(LOG_CODE_TRACE_FLOW_MARKER, LogFlowMarker.FLOW_MARK_EXCEPT);
        return this;
    }

    public LogHandler execTime(final String execTime) {
        this.data(LOG_CODE_TRACE_EXECUTION_TIME, (execTime != null) && !execTime.isBlank() ? execTime : "not-tracked");
        return this;
    }

    public LogHandler exception(final Throwable exception) {

        if (exception != null) {
            this.exceptionClass(exception.getClass().getCanonicalName());
            this.exceptionMsg(exception.getMessage());
        }

        return this;
    }

    public LogHandler exceptionClass(final String exceptionClass) {
        this.data(LOG_CODE_TRACE_EXCEPTION_CLASS_NAME,
                (exceptionClass != null) && !exceptionClass.isBlank() ? exceptionClass : "no-class");
        return this;
    }

    public LogHandler exceptionMsg(final String exceptionMsg) {
        this.data(LOG_CODE_TRACE_EXCEPTION_MSG,
                (exceptionMsg != null) && !exceptionMsg.isBlank() ? exceptionMsg : "no-error-msg");
        return this;
    }

    public void info() {
        this.data(LOG_LEVEL, "INFO");
        this.logger.info(this.logMsg());
    }

    public void warn() {
        this.data(LOG_LEVEL, "WARN");
        this.logger.warn(this.logMsg());
    }

    public void error() {
        this.data(LOG_LEVEL, "ERROR");
        this.logger.error(this.logMsg());
    }

    public void debug() {
        this.data(LOG_LEVEL, "DEBUG");
        this.logger.debug(this.logMsg());
    }

    public void trace() {
        this.data(LOG_LEVEL, "TRACE");
        this.logger.trace(this.logMsg());
    }

    public void logAs(String level) {

        if (level.equals("INFO")) {
            info();
        } else if (level.equals("WARN")) {
            warn();
        } else if (level.equals("ERROR")) {
            error();
        } else if (level.equals("DEBUG")) {
            debug();
        } else if (level.equals("TRACE")) {
            trace();
        }
    }

    public boolean isLevelActive(final Level level) {

        final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) this.logger;
        final Level effectiveLevel = logbackLogger.getEffectiveLevel();

        return level.isGreaterOrEqual(effectiveLevel);
    }

    private String logMsg() {
        return LogHandlerParser.parse(this);
    }

}
