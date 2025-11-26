package com.chathumal.smapp.configDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDatabaseConnection implements IDatabaseConnection {
    private final DatabaseConfig config;
    private Connection connection;

    public PostgresDatabaseConnection(DatabaseConfig config) {
        this.config = config;
    }

    @Override
    public Connection connect() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(
                    config.getJdbcUrl(),
                    config.getUser(),
                    config.getPassword());
            System.out.println("Conexión establecida con PostgresSQL");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC de PostgresSQL", e);
        }
        return connection;
    }

    @Override
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexión cerrada correctamente");
        }
    }
}
