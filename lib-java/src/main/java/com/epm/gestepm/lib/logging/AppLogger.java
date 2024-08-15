package com.epm.gestepm.lib.logging;

import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_CLASS;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_LINE;
import static com.epm.gestepm.lib.logging.constants.LogDataKeys.LOG_CODE_TRACE_ON_METHOD;
import com.epm.gestepm.lib.logging.applog.LogContext;
import com.epm.gestepm.lib.logging.applog.LogHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

    private final Logger logger;

    private final LogContext logContext;

    private AppLogger(final Class<?> clazz) {

        this.logger = LoggerFactory.getLogger(clazz);
        logContext = new LogContext();
    }

    public static AppLogger forClass(final Class<?> clazz) {
        return new AppLogger(clazz);
    }

    public void putContext(final String contextKey, final Object contextValue) {
        this.logContext.put(contextKey, contextValue);
    }

    public LogHandler handler() {

        final LogHandler logHandler = new LogHandler(logger);
        logHandler.data(logContext.getContextMap());
        setExecutionContext(logHandler);

        return logHandler;
    }

    public LogHandler handler(final String msg) {
        return handler().msg(msg);
    }

    private void setExecutionContext(final LogHandler logHandlerMsg) {

        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        for (int i = 1; i < 10; i++) {

            final StackTraceElement stackTraceElement = stackTrace[i];
            final String className = stackTraceElement.getClassName();

            if (!className.startsWith(AppLogger.class.getCanonicalName())) {

                final String methodName = stackTraceElement.getMethodName();
                final int lineNumber = stackTraceElement.getLineNumber();

                logHandlerMsg.data(LOG_CODE_TRACE_ON_CLASS, className);
                logHandlerMsg.data(LOG_CODE_TRACE_ON_METHOD, methodName);
                logHandlerMsg.data(LOG_CODE_TRACE_ON_LINE, lineNumber);

                break;
            }
        }
    }

}
