package com.chathumal.smapp.service.transaction;

import com.chathumal.smapp.configuration.FactoryConfiguration;

import java.sql.Connection;
import java.sql.SQLException;


public class TransactionManagerImpl implements ITransactionManager {
    private final FactoryConfiguration factoryConfiguration;

    public TransactionManagerImpl(FactoryConfiguration factoryConfiguration) {
        this.factoryConfiguration = factoryConfiguration;
    }

    @Override
    public <T> T executeInTransaction(TransactionCallback<T> callback) throws Exception {
        Connection connection = null;
        try {
        
            connection = factoryConfiguration.getConnection();
            connection.setAutoCommit(false);


            T result = callback.execute(connection);

  
            connection.commit();
            return result;

        } catch (Exception e) {
 
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {

                    System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            throw e;

        } finally {

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    System.err.println("Failed to close connection: " + closeEx.getMessage());
                }
            }
        }
    }
}
