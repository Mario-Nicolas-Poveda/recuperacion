package com.chathumal.smapp.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public abstract class CrudDAOImpl<T, ID extends Serializable> implements CrudDAO<T, ID> {
    protected Connection connection;

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    // Los métodos CRUD serán implementados por cada DAO específico
    // ya que cada uno tiene queries SQL diferentes
    @Override
    public abstract void add(T t) throws Exception;

    @Override
    public abstract void delete(ID id) throws Exception;

    @Override
    public abstract void update(T t) throws Exception;

    @Override
    public abstract List<T> getAll() throws Exception;

    @Override
    public abstract T search(ID id) throws Exception;
}
