package SpeedyProject.Controllers;

import SpeedyProject.Dao.UserDao;
import SpeedyProject.Dao.UserDaoImp;
import SpeedyProject.Exceptions.DuplicateEmailException;
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

public class RegisterController {

    @FXML
    private TextField txtname;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private Label lblError;

    private final UserDao dao = new UserDaoImp();

    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        String name = txtname.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            lblError.setText("Todos los campos son obligatorios");
            return;
        }
        if (!email.matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
            lblError.setText("Ingresa un correo electrónico válido");
            return;
        }
        if (password.length() < 4) {
            lblError.setText("La contraseña debe tener al menos 4 caracteres");
            return;
        }
        if (!password.equals(confirmPassword)) {
            lblError.setText("Las contraseñas no coinciden");
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        try {
            boolean isRegistered = dao.register(newUser);
            if (isRegistered) {
                User registeredUser = dao.findByEmail(email);
                Session.getInstance().setUser(registeredUser);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/PrincipalMenu.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                lblError.setText("No se pudo completar el registro. Intenta nuevamente");
            }
        } catch (DuplicateEmailException e) {
            lblError.setText(e.getMessage());
        }
    }
}
