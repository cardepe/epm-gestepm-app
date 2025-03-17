package com.epm.gestepm.lib.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.epm.gestepm.lib.logging.inputparser.DefaultLogInputParser;
import com.epm.gestepm.lib.logging.inputparser.LogInputParser;
import com.epm.gestepm.lib.logging.outputparser.DefaultLogOutputParser;
import com.epm.gestepm.lib.logging.outputparser.LogOutputParser;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecution {

    String operation();

    String msg() default "";

    String msgIn() default "";

    String msgOut() default "";

    String errorMsg() default "";

    boolean logIn() default true;

    boolean logOut() default true;

    boolean debugOut() default false;

    Class<? extends LogInputParser> inputParser() default DefaultLogInputParser.class;

    Class<? extends LogOutputParser> outputParser() default DefaultLogOutputParser.class;

    String level() default "INFO";

    boolean displayEnabled() default false;

}
