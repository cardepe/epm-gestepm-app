package com.epm.gestepm.lib.controller;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import com.epm.gestepm.lib.logging.ReflectionUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class APIOperation<T, R extends RestRequest> {

    private final String operationId;

    private final String operationName;

    private final String path;

    private List<String> expandableFields;

    private BiFunction<T, R, ResponseEntity<?>> linkGenerator;

    private boolean hasLinks;

    private boolean hasExpand;

    private boolean hasPagination;

    private boolean hasLocale;

    protected APIOperation(String operationId) {

        this.operationId = operationId;

        final Stream<Method> apiMethods = Stream.of(this.apiClass().getMethods());
        final Predicate<Method> methodMatcher = v -> v.getName().equals(this.operationId);

        final Optional<Method> matchedMethod = apiMethods.filter(methodMatcher).findFirst();

        this.operationName = matchedMethod.map(method -> {
            final ApiOperation annotation = method.getAnnotation(ApiOperation.class);
            return annotation.value();
        }).orElse("No name");

        this.path = matchedMethod.map(method -> {
            final RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            return annotation.value();
        }).map(l -> l.length == 1 ? l[0] : null).orElse("No path");

        matchedMethod.map(Executable::getParameters).ifPresent(params -> {
            for (Parameter parameter : params) {

                switch (parameter.getName()) {
                    case "locale":
                        this.hasLocale = true;
                        break;
                    case "links":
                        this.hasLinks = true;
                        break;
                    case "expand":
                        this.hasExpand = true;
                        break;
                    case "limit":
                    case "offset":
                        this.hasPagination = true;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private Class<T> apiClass() {
        return (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private Class<R> reqClass() {
        return (Class<R>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public APIOperation<T, R> generateLinksWith(BiFunction<T, R, ResponseEntity<?>> linkGenerator) {
        this.linkGenerator = linkGenerator;
        return this;
    }

    public String getLinkFor(R req) {

        final ResponseEntity<?> response = linkGenerator.apply(WebMvcLinkBuilder.methodOn(apiClass()), req);

        return WebMvcLinkBuilder.linkTo(response).withSelfRel().getHref();
    }

    public String tryLinkFor(RestRequest req) {

        if (req != null && reqClass().equals(req.getClass())) {

            final ResponseEntity<?> response = linkGenerator.apply(WebMvcLinkBuilder.methodOn(apiClass()), (R) req);

            return WebMvcLinkBuilder.linkTo(response).withSelfRel().getHref();
        }

        return null;
    }

    public String defaultLink(RestRequest req) {

        final R self = ReflectionUtils.getInstance(reqClass());

        if (self != null) {
            self.commonValuesFrom(req);
        }
        return tryLinkFor(self);
    }

    public void expandableFields(List<String> expandableFields) {
        this.expandableFields = expandableFields;
    }

    public String getPath() {
        return this.path;
    }

    public boolean hasLinks() {
        return hasLinks;
    }

    public boolean hasExpand() {
        return hasExpand;
    }

    public boolean hasPagination() {
        return hasPagination;
    }

    public boolean hasLocale() {
        return hasLocale;
    }

    public String getOperationName() {
        return operationName;
    }

    public String getOperationId() {
        return operationId;
    }

    public List<String> getExpandableFields() {
        return expandableFields;
    }

}
