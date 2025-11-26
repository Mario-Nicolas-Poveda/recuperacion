package com.chathumal.smapp.service.transaction;


public interface ITransactionManager {

    <T> T executeInTransaction(TransactionCallback<T> callback) throws Exception;
}
