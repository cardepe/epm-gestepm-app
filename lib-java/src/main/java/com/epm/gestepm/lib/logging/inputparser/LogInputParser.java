package com.epm.gestepm.lib.logging.inputparser;

import java.util.Map;

public interface LogInputParser {

    Map<String, Object> parse(String[] parameterNames, Object[] args);

}
