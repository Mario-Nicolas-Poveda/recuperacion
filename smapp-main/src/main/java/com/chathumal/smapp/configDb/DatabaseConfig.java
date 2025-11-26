package com.chathumal.smapp.configDb;

public class DatabaseConfig {
    private final String host;
    private final String port;
    private final String service;
    private final String user;
    private final String password;

    public DatabaseConfig() {
        // Parámetros de conexión al contenedor Docker de PostgreSQL
        this.host = "localhost"; // Host donde corre Docker
        this.port = "5432"; // Puerto expuesto en Docker
        this.service = "solid"; // Nombre de la base de datos
        this.user = "admin"; // Usuario definido en docker run
        this.password = "admin123"; // Contraseña definida en docker run
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getService() {
        return service;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getJdbcUrl() {
        return "jdbc:postgresql://" + host + ":" + port + "/" + service;
    }
}