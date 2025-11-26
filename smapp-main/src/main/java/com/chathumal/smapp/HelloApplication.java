package com.chathumal.smapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Check Database Connection
        try (Connection connection = com.chathumal.smapp.configuration.FactoryConfiguration.getInstance()
                .getConnection()) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("--------------------------------------------------");
                System.out.println("DATABASE CONNECTION SUCCESSFUL");
                System.out.println("Connection: " + connection.toString());
                System.out.println("--------------------------------------------------");
            } else {
                System.err.println("--------------------------------------------------");
                System.err.println("DATABASE CONNECTION FAILED: Connection is null or closed");
                System.err.println("--------------------------------------------------");
            }
        } catch (Exception e) {
            System.err.println("--------------------------------------------------");
            System.err.println("DATABASE CONNECTION FAILED: " + e.getMessage());
            e.printStackTrace();
            System.err.println("--------------------------------------------------");
        }

        URL login = HelloApplication.class.getResource("login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(login);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
