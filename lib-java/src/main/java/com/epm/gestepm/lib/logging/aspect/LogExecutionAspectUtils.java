package com.epm.gestepm.lib.logging.aspect;

import static java.util.Optional.ofNullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

public class LogExecutionAspectUtils {

    private LogExecutionAspectUtils() {
    }

    public static Class<?> getTargetClass(final JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass();
    }

    public static <T extends Annotation> Optional<T> getMethodAnnotation(final Class<T> clazz,
            final JoinPoint joinPoint) {

        final Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {

            final MethodSignature ms = (MethodSignature) signature;
            final Method method = ms.getMethod();

            return ofNullable(method.getDeclaredAnnotation(clazz));
        }

        return Optional.empty();
    }

    public static Method getMethod(JoinPoint joinPoint) {

        final Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {

            final MethodSignature ms = (MethodSignature) signature;
            return ms.getMethod();
        }

        return null;
    }

    public static boolean isVoid(final JoinPoint joinPoint) {

        final Signature signature = joinPoint.getSignature();

        if (signature instanceof MethodSignature) {

            return ((MethodSignature) signature).getReturnType().equals(Void.TYPE);
        }

        return false;
    }

}
