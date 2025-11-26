package com.chathumal.smapp.service.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionCallback<T> {
    
    T execute(Connection connection) throws Exception;
}
