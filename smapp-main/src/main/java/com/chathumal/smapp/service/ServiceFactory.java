package com.chathumal.smapp.service;

import com.chathumal.smapp.configuration.FactoryConfiguration;
import com.chathumal.smapp.dao.DAOFactory;
import com.chathumal.smapp.dao.custom.UserDAO;
import com.chathumal.smapp.service.custom.UserService;
import com.chathumal.smapp.service.custom.impl.UserServiceImpl;
import com.chathumal.smapp.service.mapper.IUserMapper;
import com.chathumal.smapp.service.mapper.UserMapperImpl;
import com.chathumal.smapp.service.transaction.ITransactionManager;
import com.chathumal.smapp.service.transaction.TransactionManagerImpl;

/**
 * Factory for creating service instances with proper dependency injection.
 * Modeled after DAOFactory pattern.
 * 
 * Follows:
 * - SRP: Single responsibility of service creation
 * - OCP: Open for extension (add more service types)
 * - LSP: Services are substitutable through interfaces
 */
public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance == null ? instance = new ServiceFactory() : instance;
    }

    public enum Type {
        USER
    }

    /**
     * Get service instance with all dependencies properly injected
     * 
     * @param type Type of service to create
     * @return Service instance
     */
    public Object getService(Type type) {
        switch (type) {
            case USER:
                // Get dependencies
                UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.Type.USER);
                ITransactionManager transactionManager = new TransactionManagerImpl(FactoryConfiguration.getInstance());
                IUserMapper mapper = new UserMapperImpl();

                // Create service with dependency injection (like PostgresDatabaseConnection
                // receives DatabaseConfig)
                return new UserServiceImpl(userDAO, transactionManager, mapper);

            default:
                return null;
        }
    }

    /**
     * Convenience method for getting UserService
     * 
     * @return UserService instance
     */
    public UserService getUserService() {
        return (UserService) getService(Type.USER);
    }
}
