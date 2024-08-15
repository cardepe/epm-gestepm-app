package com.epm.gestepm.lib.controller.decorator;

import java.util.List;
import com.epm.gestepm.lib.controller.RestRequest;

public interface ResponseDataDecorator<T> {

    void decorate(RestRequest request, T data);

    default void decorate(RestRequest request, List<T> data) {
        data.forEach(i -> this.decorate(request, i));
    }

    default <D extends ResponseDataDecorator<S>, S> void decorateChild(Class<D> decoratorClass, RestRequest request,
            S data) {
        getDecorator(decoratorClass).decorate(request, data);
    }

    default <S> void decorateChild(Class<ResponseDataDecorator<S>> decoratorClass, RestRequest request, List<S> data) {
        getDecorator(decoratorClass).decorate(request, data);
    }

    <D extends ResponseDataDecorator<S>, S> ResponseDataDecorator<S> getDecorator(Class<D> decoratorClass);

}
