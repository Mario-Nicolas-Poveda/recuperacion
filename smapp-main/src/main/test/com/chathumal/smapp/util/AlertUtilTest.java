package com.chathumal.smapp.util;

import javafx.application.Application;
import javafx.stage.Stage;


public class AlertUtilTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        AlertUtil.showInfoAlert("Info Alert Test", "This is an information alert!");

        boolean isConfirmed = AlertUtil.showConfirmationAlert("Confirmation Alert Test", "Do you want to proceed?");
        System.out.println("Confirmation result: " + (isConfirmed ? "OK" : "Cancel"));


        AlertUtil.showErrorAlert("Error Alert Test", "An error has occurred!");

        AlertUtil.showWarningAlert("Warning Alert Test", "This is a warning alert!");
    }

    public static void main(String[] args) {
        launch(args);
    }


}
