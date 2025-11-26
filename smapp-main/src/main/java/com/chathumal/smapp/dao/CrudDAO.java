package com.chathumal.smapp.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface CrudDAO<T, ID extends Serializable> extends SuperDAO {
    void setConnection(Connection connection);

    void add(T t) throws Exception;

    void delete(ID id) throws Exception;

    void update(T t) throws Exception;

    List<T> getAll() throws Exception;

    T search(ID id) throws Exception;
}
