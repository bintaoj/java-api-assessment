package com.cbfacademy.apiassessment.core;

import java.util.List;
import java.util.UUID;


public interface Repository<T, ID> extends org.springframework.data.repository.Repository<T, ID>{

    List<T> findAll() throws PersistenceException;

    T find(UUID id) throws IllegalArgumentException, PersistenceException;

    T create(T entity) throws IllegalArgumentException, PersistenceException;

    void delete(T entity) throws IllegalArgumentException, PersistenceException;

    T update(T entity) throws IllegalArgumentException, PersistenceException;

}
