package com.chathumal.smapp.controller;

import com.chathumal.smapp.entity.User;
import com.chathumal.smapp.exception.ExceptionHandlerUtil;
import com.chathumal.smapp.service.ServiceFactory;
import com.chathumal.smapp.service.custom.UserService;
import com.chathumal.smapp.util.AlertUtil;
import com.chathumal.smapp.util.UserSession;
import com.chathumal.smapp.util.ValidationUtil;
import com.jfoenix.controls.JFXButton;
import com.chathumal.smapp.HelloApplication;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {
    public AnchorPane root;
    public JFXButton btnLogin;
    public JFXTextField txtID;
    public JFXTextField txtEmail;
    public JFXPasswordField txtPassword;

    public void loginOnClick(ActionEvent actionEvent) {
        UserService service = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.Type.USER);
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        try {
            if (!ValidationUtil.isValidEmail(email)) {
                AlertUtil.showErrorAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }
            if (!ValidationUtil.isValidPassword(password)) {
                AlertUtil.showErrorAlert("Invalid Password", "Password must be at least 4 characters long.");
                return;
            }
            User user = service.findByEmail(email);
            if (user == null) {
                AlertUtil.showErrorAlert("User Not Found", "No user found with the provided email.");
                return;
            }
            if (!user.getPassword().equalsIgnoreCase(password)) {
                AlertUtil.showErrorAlert("Incorrect Password", "The password you entered is incorrect.");
                return;
            }
            UserSession instance = UserSession.getInstance();
            instance.setEmail(email);
            Stage window = (Stage) this.root.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(HelloApplication.class.getResource("dashboard.fxml"))));

        } catch (Exception e) {
            ExceptionHandlerUtil.handleException("Login Error", "An error occurred during login", e);
        }
    }
}
