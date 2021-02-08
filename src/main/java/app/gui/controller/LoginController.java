package main.java.app.gui.controller;

import com.google.inject.Inject;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.app.gui.fxml.FXMLFiles;
import main.java.app.gui.service.AuthenticationService;
import main.java.app.gui.service.FileStorageService;
import main.java.app.gui.service.NavigationService;

import java.util.logging.Logger;

/**
 * The class LoginController is responsible for handling Login and Registration requests.
 */
public class LoginController extends Controller {
    private AuthenticationService authService;
    private FileStorageService fileService;
    private NavigationService navigationService;

    private boolean registerMode = false;

    @FXML private TextField usernameInput;
    @FXML private PasswordField passwordInput;
    @FXML private Button loginButton;
    @FXML private Button passwordResetButton;
    @FXML private Button toggleRegisterButton;

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());

    @Inject
    public LoginController(
            AuthenticationService authService, FileStorageService fileService, NavigationService navigationService) {
        this.authService = authService;
        this.fileService = fileService;
        this.navigationService = navigationService;
    }

    @FXML
    private void initialize() {
        enableInputErrorClass(usernameInput);
        enableInputErrorClass(passwordInput);

        loginButton.setOnAction((event) -> login());
        loginButton.getStyleClass().add("login");
        toggleRegisterButton.setOnAction((event) -> toggleRegistrationMode());
    }

    /**
     * Toggles between Login and Registration mode and changes the behavior of
     * the Controller accordingly.
     */
    private void toggleRegistrationMode() {
        if (!registerMode) {
            // Enable registration mode
            loginButton.setOnAction((event -> register()));
            loginButton.setText("REGISTRIEREN");
            loginButton.getStyleClass().remove("login");
            loginButton.getStyleClass().add("register");
            toggleRegisterButton.setText("Schon registriert? Einloggen");
        } else {
            // Disable registration mode
            loginButton.setOnAction((event -> login()));
            loginButton.setText("LOGIN");
            loginButton.getStyleClass().remove("register");
            loginButton.getStyleClass().add("login");
            toggleRegisterButton.setText("Noch kein Benutzer? Registrieren");
        }
        registerMode = !registerMode;
    }

    /**
     * Dynamically adds / removes a CSS style class that renders the TextField's border red
     * depending on whether it is empty or not.
     *
     * @param textField TextField, to which the effect should be applied to.
     */
    private void enableInputErrorClass(TextField textField) {
        ChangeListener<Boolean> changeListener = (observableValue, oldBool, newBool) -> {
            if (!newBool && textField.getText().isEmpty()) {
                textField.getStyleClass().add("empty");
            } else {
                textField.getStyleClass().remove("empty");
            }
        };

        textField.focusedProperty().addListener(changeListener);
    }

    /**
     * Checks whether at least one of the TextField inputs is empty.
     *
     * @return boolean, true if both inputs are not empty, false otherwise
     */
    private boolean assertNotEmptyInput() {
        return !usernameInput.getText().isEmpty() && !passwordInput.getText().isEmpty();
    }

    /**
     * Attempts to register a new user using the provided credentials. Will fail
     * if a user with the same name is already registered.
     */
    private void register() {
        if (!assertNotEmptyInput()) {
            return;
        }

        if (authService.register(usernameInput.getText(), passwordInput.getText())) {
            logger.info(String.format("User '%s' has been successfully registered.", usernameInput.getText()));

            navigationService.closeStage((Stage) loginButton.getScene().getWindow());
            navigationService.createMasterStage(
                    FXMLFiles.DASHBOARD,
                    FXMLFiles.SIDEBAR_DASHBOARD
            );
        }
    }

    /**
     * Attempts to login using the provided credentials. Will fail if no such user exists
     * or the password is invalid.
     */
    private void login() {
        if (!assertNotEmptyInput()) {
            return;
        }

        if (authService.authenticate(usernameInput.getText(), passwordInput.getText())) {
            navigationService.closeStage((Stage) loginButton.getScene().getWindow());
            navigationService.createMasterStage(
                    FXMLFiles.DASHBOARD,
                    FXMLFiles.SIDEBAR_DASHBOARD
            );

            logger.info(String.format("User '%s' logged in successfully.", usernameInput.getText()));
        }
    }
}
