package com.rrd.airmanager.services;

import java.util.List;

public interface AppService<T> {
    T save(T entity);

    List<T> findAll();

    void deleteInBatch(List<T> entities);
}
