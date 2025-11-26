package com.chathumal.smapp.exception;

import com.chathumal.smapp.util.AlertUtil;
import javafx.application.Platform;

public class ExceptionHandlerUtil {
    public static void handleException(String title, Exception ex) {
        Platform.runLater(() -> {
            AlertUtil.showErrorAlert(title, ex.getMessage());
        });
    }

    public static void handleException(String title, String customMessage, Exception ex) {
        Platform.runLater(() -> {
            AlertUtil.showErrorAlert(title, customMessage + "\nDetails: " + ex.getMessage());
        });
    }

}
