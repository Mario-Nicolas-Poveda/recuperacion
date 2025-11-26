package com.chathumal.smapp.service.transaction;

import java.sql.Connection;

/**
 * Interface for transaction management following SOLID principles.
 * Similar to IDatabaseConnection in configDb package.
 * 
 * Follows:
 * - SRP: Single responsibility of managing transactions
 * - OCP: Open for extension (can create different implementations)
 * - LSP: All implementations must be substitutable
 */
@FunctionalInterface
public interface TransactionCallback<T> {
    /**
     * Execute business logic within a transaction
     * 
     * @param connection Database connection to use
     * @return Result of the operation
     * @throws Exception if operation fails
     */
    T execute(Connection connection) throws Exception;
}
