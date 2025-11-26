package com.chathumal.smapp.controller;

import com.chathumal.smapp.HelloApplication;
import com.chathumal.smapp.dto.UserDTO;
import com.chathumal.smapp.exception.DuplicateEntryException;
import com.chathumal.smapp.exception.ExceptionHandlerUtil;
import com.chathumal.smapp.exception.NotFoundException;
import com.chathumal.smapp.service.ServiceFactory;
import com.chathumal.smapp.service.custom.UserService;
import com.chathumal.smapp.util.AlertUtil;
import com.chathumal.smapp.util.UserSession;
import com.chathumal.smapp.util.ValidationUtil;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;

public class DashboardController {
    public AnchorPane root;
    public ImageView imgLogout;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtMobile;
    public TextField txtAddress;
    public TextField txtUserId;
    public TextField txtPassword;
    public ScrollPane scrallpaneNotifi;
    public AnchorPane anchorPaneUserList;

    public Button btnUpdate;

    public Tab tabCreateUser;
    public TextField txtCreateUserName;
    public TextField txtCreateUserEmail;
    public TextField txtCreateUserMobile;
    public TextField txtCreateUserAddress;
    public TextField txtCreateUserPassword;
    public Button btnCreateNewUser;
    public CheckBox chkboxAdmAccess;
    public PieChart userTypeChart;

    public ScrollPane scrollPaneListOfUsers;
    UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.Type.USER);

    public void imgLogoutOnClick(MouseEvent mouseEvent) {
        try {
            Stage window = (Stage) this.root.getScene().getWindow();
            window.setScene(new Scene(FXMLLoader.load(HelloApplication.class.getResource("login.fxml"))));
            window.centerOnScreen();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {
        String userEmail = UserSession.getInstance().getEmail();
        txtEmail.setText(userEmail);
        try {
            UserDTO user = getUserByEmail(userEmail);
            if (user != null) {
                updateProfileFields(user);
                boolean fulacs = user.isFulacs();
                if (fulacs) {
                    tabCreateUser.setDisable(false);
                } else {
                    tabCreateUser.setDisable(true);
                }
                UserSession.getInstance().setCurrentUser(user);
            } else {
                AlertUtil.showErrorAlert("User Not Found", "No user found with the provided email.");
            }
        } catch (NotFoundException e) {
            AlertUtil.showErrorAlert("User Not Found", "User profile could not be found.");
        } catch (Exception e) {
            ExceptionHandlerUtil.handleException("Error", "An error occurred while loading user profile", e);
        }
        loadUserStatistics();
    }

    private UserDTO getUserByEmail(String email) throws NotFoundException, Exception {
        UserDTO user = userService.findByEmail(email);
        if (user == null) {
            throw new NotFoundException("User not found for email: " + email);
        }
        return user;
    }

    private void updateProfileFields(UserDTO user) {
        txtAddress.setText(user.getAddress());
        txtMobile.setText(user.getContact());
        txtName.setText(user.getName());
        txtUserId.setText(String.valueOf(user.getId()));
        txtPassword.setText(user.getPassword());
    }

    public void userListOnChange(Event event) {
        listOfAllUsersUserTab();
    }

    public void listOfAllUsersUserTab() {
        scrollPaneListOfUsers.setContent(null);
        List<UserDTO> allUsers = null;
        UserService userService = (UserService) ServiceFactory.getInstance().getService(ServiceFactory.Type.USER);

        try {
            allUsers = userService.findAllUsers();
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "No se pudieron cargar los usuarios");
            return;
        }

        VBox vBox = new VBox(15);
        vBox.setPadding(new Insets(10));
        scrollPaneListOfUsers.setFitToWidth(true);
        scrollPaneListOfUsers.setFitToHeight(true);

        if (allUsers != null && !allUsers.isEmpty()) {
            for (UserDTO user : allUsers) {
                // Crear un panel para cada usuario con diseño mejorado
                AnchorPane userCard = new AnchorPane();
                userCard.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #e0e0e0; " +
                                "-fx-border-width: 1; " +
                                "-fx-border-radius: 8; " +
                                "-fx-background-radius: 8; " +
                                "-fx-padding: 15; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
                userCard.setPrefHeight(80);

                // GridPane para organizar la información
                GridPane infoGrid = new GridPane();
                infoGrid.setHgap(20);
                infoGrid.setVgap(5);
                AnchorPane.setLeftAnchor(infoGrid, 15.0);
                AnchorPane.setTopAnchor(infoGrid, 10.0);

                // ID del usuario
                Label lblIdTitle = new Label("ID:");
                lblIdTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #666;");
                Label lblId = new Label(String.valueOf(user.getId()));
                lblId.setStyle("-fx-font-size: 14px;");

                // Nombre del usuario
                Label lblNameTitle = new Label("Nombre:");
                lblNameTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #666;");
                Label lblName = new Label(user.getName());
                lblName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                // Email del usuario
                Label lblEmailTitle = new Label("Email:");
                lblEmailTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #666;");
                Label lblEmail = new Label(user.getEmail());
                lblEmail.setStyle("-fx-font-size: 13px; -fx-text-fill: #555;");

                // Rol del usuario
                Label lblRoleTitle = new Label("Rol:");
                lblRoleTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #666;");
                Label lblRole = new Label(user.isFulacs() ? "Administrador" : "Usuario Normal");
                lblRole.setStyle(
                        "-fx-font-size: 12px; " +
                                "-fx-padding: 4 10 4 10; " +
                                "-fx-background-radius: 12; " +
                                (user.isFulacs() ? "-fx-background-color: #4CAF50; -fx-text-fill: white;"
                                        : "-fx-background-color: #2196F3; -fx-text-fill: white;"));

                // Agregar al GridPane
                infoGrid.add(lblIdTitle, 0, 0);
                infoGrid.add(lblId, 1, 0);
                infoGrid.add(lblNameTitle, 2, 0);
                infoGrid.add(lblName, 3, 0);
                infoGrid.add(lblEmailTitle, 0, 1);
                infoGrid.add(lblEmail, 1, 1, 3, 1);
                infoGrid.add(lblRoleTitle, 4, 0);
                infoGrid.add(lblRole, 5, 0);

                userCard.getChildren().add(infoGrid);
                vBox.getChildren().add(userCard);
            }
            scrollPaneListOfUsers.setContent(vBox);
        }
    }

    public void btnCreateNewUserOnAction(ActionEvent actionEvent) {
        boolean confirmUpdate = AlertUtil.showConfirmationAlert("Confirm", "Did you want to really create new account");
        if (confirmUpdate) {
            if (isValidUpdateNewUser()) {
                try {
                    boolean b = userService
                            .addUser(new UserDTO(txtCreateUserName.getText(), txtCreateUserAddress.getText(),
                                    txtCreateUserMobile.getText(), txtCreateUserEmail.getText(),
                                    txtCreateUserPassword.getText(), chkboxAdmAccess.isSelected()));
                    if (b) {
                        AlertUtil.showInfoAlert("Success", "User Create successful");
                    } else {
                        AlertUtil.showWarningAlert("Failed", "User creation failed");
                    }
                } catch (DuplicateEntryException e) {
                    ExceptionHandlerUtil.handleException("Error", "An error occurred while updating the user profile",
                            e);
                } catch (Exception e) {
                    ExceptionHandlerUtil.handleException("Error", "An error occurred while updating the user profile",
                            e);
                }
            } else {
                AlertUtil.showErrorAlert("Invalid Input", "Please ensure all fields are valid before updating.");
            }
        } else {
            AlertUtil.showErrorAlert("Failed", "Update failed");
        }
        clearNewUserAddingFiled();
        loadUserStatistics();

    }

    void clearNewUserAddingFiled() {
        txtCreateUserEmail.clear();
        txtCreateUserPassword.clear();
        txtCreateUserMobile.clear();
        txtCreateUserName.clear();
        txtCreateUserAddress.clear();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        boolean confirmUpdate = AlertUtil.showConfirmationAlert("Confirm",
                "Did you want to really update your account details");
        if (confirmUpdate) {
            if (isValidUpdate()) {
                try {
                    userService.updateUser(new UserDTO(Integer.valueOf(txtUserId.getText()), txtName.getText(),
                            txtAddress.getText(), txtMobile.getText(), txtEmail.getText(), txtPassword.getText(),
                            false));
                    AlertUtil.showInfoAlert("Success", "Update successful");
                } catch (NotFoundException e) {
                    AlertUtil.showErrorAlert("User Not Found", "User profile could not be found.");
                } catch (Exception e) {
                    ExceptionHandlerUtil.handleException("Error", "An error occurred while updating the user profile",
                            e);
                }
            }
        } else {
            AlertUtil.showErrorAlert("Failed", "Update failed");
        }

    }

    private boolean confirmUpdate() {
        return AlertUtil.showConfirmationAlert("Confirm", "Do you really want to update your account details?");
    }

    private boolean isValidUpdate() {
        return isValidName() && isValidEmail() && isValidPassword() && isValidAddress() && isValidMobile();
    }

    private boolean isValidUpdateNewUser() {
        return isValidNameNewUser() && isValidEmailNewUser() && isValidPasswordNewUser() && isValidAddressNewUser()
                && isValidMobileNewUser();
    }

    private boolean isValidName() {
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            AlertUtil.showErrorAlert("Invalid Name", "Name cannot be empty.");
            return false;
        }
        if (name.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Name", "Name cannot exceed 30 characters.");
            return false;
        }
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            AlertUtil.showErrorAlert("Invalid Name",
                    "Name can only contain letters and spaces (no dots, numbers or accents).");
            return false;
        }
        return true;
    }

    private boolean isValidNameNewUser() {
        String name = txtCreateUserName.getText().trim();
        if (name.isEmpty()) {
            AlertUtil.showErrorAlert("Invalid Name", "Name cannot be empty.");
            return false;
        }
        if (name.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Name", "Name cannot exceed 30 characters.");
            return false;
        }
        if (!name.matches("^[a-zA-Z\\s]+$")) {
            AlertUtil.showErrorAlert("Invalid Name",
                    "Name can only contain letters and spaces (no dots, numbers or accents).");
            return false;
        }
        return true;
    }

    private boolean isValidEmail() {
        String email = txtEmail.getText().trim();
        if (email.isEmpty() || !ValidationUtil.isValidEmail(email)) {
            AlertUtil.showErrorAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (email.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Email", "Email cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidEmailNewUser() {
        String email = txtCreateUserEmail.getText().trim();
        if (email.isEmpty() || !ValidationUtil.isValidEmail(email)) {
            AlertUtil.showErrorAlert("Invalid Email", "Please enter a valid email address.");
            return false;
        }
        if (email.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Email", "Email cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidPassword() {
        String password = txtPassword.getText().trim();
        if (password.isEmpty() || password.length() < 6) {
            AlertUtil.showErrorAlert("Invalid Password", "Password must be at least 6 characters long.");
            return false;
        }
        if (password.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Password", "Password cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidPasswordNewUser() {
        String password = txtCreateUserPassword.getText().trim();
        if (password.isEmpty() || password.length() < 6) {
            AlertUtil.showErrorAlert("Invalid Password", "Password must be at least 6 characters long.");
            return false;
        }
        if (password.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Password", "Password cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidAddress() {
        String address = txtAddress.getText().trim();
        if (address.isEmpty()) {
            AlertUtil.showErrorAlert("Invalid Address", "Address cannot be empty.");
            return false;
        }
        if (address.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Address", "Address cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidAddressNewUser() {
        String address = txtCreateUserAddress.getText().trim();
        if (address.isEmpty()) {
            AlertUtil.showErrorAlert("Invalid Address", "Address cannot be empty.");
            return false;
        }
        if (address.length() > 30) {
            AlertUtil.showErrorAlert("Invalid Address", "Address cannot exceed 30 characters.");
            return false;
        }
        return true;
    }

    private boolean isValidMobile() {
        String mobile = txtMobile.getText().trim();
        if (mobile.isEmpty() || !ValidationUtil.isValidMobile(mobile)) {
            AlertUtil.showErrorAlert("Invalid Mobile", "Please enter a valid mobile number.");
            return false;
        }
        return true;
    }

    private boolean isValidMobileNewUser() {
        String mobile = txtCreateUserMobile.getText().trim();
        if (mobile.isEmpty() || !ValidationUtil.isValidMobile(mobile)) {
            AlertUtil.showErrorAlert("Invalid Mobile", "Please enter a valid mobile number.");
            return false;
        }
        return true;
    }

    public void onChangeCreateUserTab(Event event) {
    }

    public Label lblTotalUsers;
    public Label lblUserName;
    public Label lblUserEmail;

    private void loadUserStatistics() {
        try {
            List<UserDTO> allUsers = userService.findAllUsers();

            // Update Total Users Card
            if (lblTotalUsers != null) {
                lblTotalUsers.setText(String.valueOf(allUsers.size()));
            }

            // Update User Info Cards (Green/Red)
            UserDTO currentUser = UserSession.getInstance().getCurrentUser();
            if (currentUser != null) {
                // Green Card: My Posts
                if (lblUserName != null) {
                    ((Label) lblUserName.getParent().getChildrenUnmodifiable().get(0)).setText("Nombre");
                    lblUserName.setText(currentUser.getName());
                }

                // Red Card: My Followers
                // Red Card: Email
                if (lblUserEmail != null) {
                    ((Label) lblUserEmail.getParent().getChildrenUnmodifiable().get(0)).setText("Email");
                    lblUserEmail.setText(currentUser.getEmail());
                }
            }

            long adminCount = allUsers.stream()
                    .filter(UserDTO::isFulacs)
                    .count();

            long normalCount = allUsers.size() - adminCount;

            PieChart.Data adminData = new PieChart.Data("Usuarios Admin (" + adminCount + ")", adminCount);
            PieChart.Data normalData = new PieChart.Data("Usuarios Normales (" + normalCount + ")", normalCount);

            userTypeChart.getData().clear();
            userTypeChart.getData().addAll(adminData, normalData);

        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "No se pudieron cargar las estadísticas");
            e.printStackTrace();
        }
    }
}
