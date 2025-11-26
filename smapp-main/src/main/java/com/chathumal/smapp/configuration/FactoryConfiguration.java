package com.chathumal.smapp.configuration;

import com.chathumal.smapp.configDb.DatabaseConfig;
import com.chathumal.smapp.configDb.IDatabaseConnection;
import com.chathumal.smapp.configDb.PostgresDatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;
    private IDatabaseConnection databaseConnection;

    private FactoryConfiguration() {
        DatabaseConfig config = new DatabaseConfig();
        databaseConnection = new PostgresDatabaseConnection(config);
    }

    public static FactoryConfiguration getInstance() {
        return factoryConfiguration == null ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Connection getConnection() throws SQLException {
        return databaseConnection.connect();
    }

    public void closeConnection() throws SQLException {
        databaseConnection.disconnect();
    }
}
