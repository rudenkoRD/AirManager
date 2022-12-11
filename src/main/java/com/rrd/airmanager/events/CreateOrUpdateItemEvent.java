package com.rrd.airmanager.events;

import com.sun.istack.NotNull;
import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public class CreateOrUpdateItemEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
    protected T item;

    public CreateOrUpdateItemEvent(@NotNull T item) {
        super(item);
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(item)
        );
    }
}
