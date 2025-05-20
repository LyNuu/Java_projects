package org.example.service;

import java.util.List;

public interface Service<T> {

    public T save(T entity);

    public void deleteById(long id);

    public void deleteByEntity(T entity);

    public void deleteAll();

    public T update(T entity);

    public T getById(long id);

    public List<T> getAll();
}
