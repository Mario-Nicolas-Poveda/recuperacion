package com.chathumal.smapp.service.transaction;

import com.chathumal.smapp.configuration.FactoryConfiguration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementation of transaction management following SOLID principles.
 * Modeled after PostgresDatabaseConnection in configDb package.
 * 
 * Follows:
 * - SRP: Single responsibility of transaction lifecycle management
 * - OCP: Can be extended (e.g., LoggingTransactionManager wrapper)
 * - LSP: Perfectly substitutable with any ITransactionManager
 */
public class TransactionManagerImpl implements ITransactionManager {
    private final FactoryConfiguration factoryConfiguration;

    /**
     * Constructor with dependency injection (like PostgresDatabaseConnection
     * receives DatabaseConfig)
     * 
     * @param factoryConfiguration Factory to get database connections
     */
    public TransactionManagerImpl(FactoryConfiguration factoryConfiguration) {
        this.factoryConfiguration = factoryConfiguration;
    }

    @Override
    public <T> T executeInTransaction(TransactionCallback<T> callback) throws Exception {
        Connection connection = null;
        try {
            // Acquire connection
            connection = factoryConfiguration.getConnection();
            connection.setAutoCommit(false);

            // Execute business logic
            T result = callback.execute(connection);

            // Commit if successful
            connection.commit();
            return result;

        } catch (Exception e) {
            // Rollback on error
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    // Log rollback failure but throw original exception
                    System.err.println("Failed to rollback transaction: " + rollbackEx.getMessage());
                }
            }
            throw e;

        } finally {
            // Always cleanup connection
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
