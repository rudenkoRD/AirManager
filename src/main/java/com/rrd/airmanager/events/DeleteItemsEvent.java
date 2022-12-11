package com.rrd.airmanager.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import java.util.List;

public class DeleteItemsEvent<T> extends ApplicationEvent implements ResolvableTypeProvider {
    public DeleteItemsEvent(List<T> source) {
        super(source);
        assert(source.size() != 0);
    }

    @SuppressWarnings("unchecked")
    public List<T> getItems() {
        return (List<T>) getSource();
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(
                getClass(),
                ResolvableType.forInstance(getItems().get(0))
        );
    }
}
