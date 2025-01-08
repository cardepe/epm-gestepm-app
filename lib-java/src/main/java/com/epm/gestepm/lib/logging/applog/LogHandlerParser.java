package com.epm.gestepm.lib.logging.applog;

import static java.lang.String.join;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_EXCEPTION_MSG;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_EXECUTION_TIME;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_FLOW_MARKER;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_CLASS;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_LAYER;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_LINE;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_METHOD;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_MSG;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_OPERATION;
import static com.epm.gestepm.lib.logging.constants.LogFlowMarker.FLOW_MARK_LINE;
import static com.epm.gestepm.lib.logging.constants.LogLayerMarkers.UNKNOWN;
import static com.epm.gestepm.lib.logging.constants.LogOperations.OP_PROCESS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.epm.gestepm.lib.utils.stringutils.StringCleanUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandlerParser {

    private static final Logger logger = LoggerFactory.getLogger(LogHandlerParser.class);

    private LogHandlerParser() {
    }

    public static String parse(LogHandler logHandler) {

        final LogContext dataMap = logHandler.getDataMap();
        final Map<String, Object> contextMap = dataMap.getContextMap();

        String flowMarker = dataMap.getAsOrDefault(LOG_CODE_TRACE_FLOW_MARKER, String.class, FLOW_MARK_LINE);
        flowMarker = StringUtils.rightPad(flowMarker, 3);

        String layerMarker = dataMap.getAsOrDefault(LOG_CODE_TRACE_ON_LAYER, String.class, UNKNOWN);
        layerMarker = StringUtils.rightPad(layerMarker, 5);

        String operation = dataMap.getAsOrDefault(LOG_OPERATION, String.class, OP_PROCESS);
        operation = StringUtils.rightPad(operation, 10);

        String className = dataMap.getAsOrDefault(LOG_CODE_TRACE_ON_CLASS, String.class, "");
        className = shortClassName(className);

        String methodName = dataMap.getAsOrDefault(LOG_CODE_TRACE_ON_METHOD, String.class, "");
        Integer line = dataMap.getAsOrDefault(LOG_CODE_TRACE_ON_LINE, Integer.class, null);

        String lineMarker = line != null ? line.toString() : "";

        String methodExec = join(".", className, methodName);
        methodExec = join(":", methodExec, lineMarker);

        final String execTime = dataMap.getAsOrDefault(LOG_CODE_TRACE_EXECUTION_TIME, String.class, ".....executing");
        String msg = dataMap.getAsOrDefault(LOG_MSG, String.class, "no-msg");

        final String errorMsg = dataMap.get(LOG_CODE_TRACE_EXCEPTION_MSG)
            .map(e -> " due to: ".concat(String.valueOf(e)))
            .orElse("");
        msg = msg.concat(errorMsg);

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        final ObjectNode json = objectMapper.createObjectNode();

        contextMap.keySet().stream().sorted().forEach(k -> addProp(json, k, contextMap.get(k)));

        String jsonData = json.toString();
        jsonData = StringCleanUtils.makeSingleLine(jsonData);

        return join(" | ", flowMarker, layerMarker, operation, execTime, msg, methodExec,
                "jsonData = ".concat(jsonData));
    }

    private static String shortClassName(String className) {

        if (className == null) {
            return "<no-class>";
        }

        final List<String> shortClassTokens = new ArrayList<>();

        final String[] split = className.split("\\.");
        shortClassTokens.add(split[split.length - 1]);

        return join(".", shortClassTokens);
    }

    private static void addProp(final ObjectNode json, String key, Object value) {

        try {

            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            SimpleModule module = new SimpleModule();
            module.addSerializer(byte[].class, new ByteArraySerializer());

            objectMapper.registerModule(module);

            final List<String> path = new ArrayList<>(Arrays.asList(key.split("\\.")));
            final String valueKey = path.get(path.size() - 1);

            ObjectNode currentNode = json;

            for (int i = 0; i < path.size() - 1; i++) {

                final String subPath = path.get(i);

                if (!currentNode.has(subPath)) {
                    currentNode.set(subPath, objectMapper.createObjectNode());
                }

                currentNode = (ObjectNode) currentNode.get(subPath);
            }

            final JsonNode jsonNode = objectMapper.valueToTree(value);

            currentNode.set(valueKey, jsonNode);

        } catch (Exception e) {
            logger.info(String.format("Could not display the log of the object ‘%s’.", key));
        }
    }

}
