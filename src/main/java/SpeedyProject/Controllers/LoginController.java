package SpeedyProject.Controllers;

import SpeedyProject.Dao.UserDao;
import SpeedyProject.Dao.UserDaoImp;
import SpeedyProject.Models.User;
import SpeedyProject.Patterns.Singlenton.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static final String FIELD_ERROR_STYLE =
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; " +
                    "-fx-border-color: #e74c3c; -fx-border-width: 2; " +
                    "-fx-border-radius: 15; -fx-background-radius: 15;";

    private static final String FIELD_OK_STYLE =
            "-fx-focus-color: transparent; -fx-faint-focus-color: transparent; " +
                    "-fx-border-color: #2ecc71; -fx-border-width: 2; " +
                    "-fx-border-radius: 15; -fx-background-radius: 15;";

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblError;

    private final UserDao dao = new UserDaoImp();

    @FXML
    private void login(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (email.isEmpty() || password.isEmpty()) {
            lblError.setText("Debe ingresar email y contraseña");
            txtEmail.setStyle(FIELD_ERROR_STYLE);
            txtPassword.setStyle(FIELD_ERROR_STYLE);
            txtEmail.clear();
            txtPassword.clear();
            txtEmail.requestFocus();
            return;
        }

        User user = dao.login(email, password);
        if (user != null) {
            Session.getInstance().setUser(user);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/PrincipalMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            return;
        }

        User userByEmail = dao.findByEmail(email);
        if (userByEmail == null) {
            lblError.setText("El correo no está registrado");
            txtEmail.setStyle(FIELD_ERROR_STYLE);
            txtPassword.setStyle(FIELD_ERROR_STYLE);
            txtEmail.clear();
            txtPassword.clear();
            txtEmail.requestFocus();
        } else {
            lblError.setText("Contraseña incorrecta");
            txtEmail.setStyle(FIELD_OK_STYLE);
            txtPassword.setStyle(FIELD_ERROR_STYLE);
            txtPassword.clear();
            txtPassword.requestFocus();
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
