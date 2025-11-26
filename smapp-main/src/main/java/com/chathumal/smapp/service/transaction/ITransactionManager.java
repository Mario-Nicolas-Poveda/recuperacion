package com.chathumal.smapp.service.transaction;

/**
 * Interface for transaction management following SOLID principles.
 * Modeled after IDatabaseConnection in configDb package.
 * 
 * Follows:
 * - SRP: Single responsibility of managing database transactions
 * - OCP: Open for extension (can create logging, monitoring implementations)
 * - LSP: All implementations are substitutable
 */
public interface ITransactionManager {
    /**
     * Execute a database operation within a transaction.
     * Automatically handles:
     * - Connection acquisition
     * - Transaction begin/commit/rollback
     * - Connection cleanup
     * 
     * @param callback The operation to execute
     * @param <T>      Return type of the operation
     * @return Result from the callback
     * @throws Exception if the operation fails
     */
    <T> T executeInTransaction(TransactionCallback<T> callback) throws Exception;
}
