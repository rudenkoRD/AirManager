package com.rrd.airmanager.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class SaveItemEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
    public SaveItemEvent(T source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    public T getItem() {
        return (T) getSource();
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(getItem())
        );
    }
}