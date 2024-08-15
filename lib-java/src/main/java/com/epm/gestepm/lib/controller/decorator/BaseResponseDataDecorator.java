package com.epm.gestepm.lib.controller.decorator;

import org.springframework.context.ApplicationContext;

public abstract class BaseResponseDataDecorator<T> implements ResponseDataDecorator<T> {

    private final ApplicationContext applicationContext;

    protected BaseResponseDataDecorator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public <D extends ResponseDataDecorator<S>, S> ResponseDataDecorator<S> getDecorator(Class<D> decoratorClass) {
        return applicationContext.getBean(decoratorClass);
    }

}
