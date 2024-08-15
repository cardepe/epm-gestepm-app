package com.epm.gestepm.lib.logging.outputparser;

import java.util.List;
import java.util.Map;

public interface LogOutputParser {

    Map<String, Object> parse(Object value);

    Map<String, Object> parse(List<Object> value);

}
