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

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private UserDao dao = new UserDaoImp();
    @FXML
    Label lblError;

    @FXML
    private void login(ActionEvent event) throws IOException {
        User user = dao.login(txtEmail.getText(), txtPassword.getText());
        if (user != null) {
            Session.getInstance().setUser(user);
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/Views/PrincipalMenu.fxml"));
            System.out.println(
                    Session.getInstance()
                            .getCurrentUser()
                            .getName()
            );
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            //Email and password validation
        } else {
            User userByEmail = dao.findByEmail(txtEmail.getText());
            if (txtEmail.getText().isEmpty() || txtPassword.getText().isEmpty()) {

                lblError.setText("Debe ingresar email y contraseña");
                txtPassword.setStyle(
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-border-color: #e74c3c;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-radius: 15;"
                );
                txtEmail.setStyle(
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-border-color: #e74c3c;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-radius: 15;"
                );
                txtEmail.clear();
                txtPassword.clear();
                txtEmail.requestFocus();

            }
            else if (userByEmail == null) {

                lblError.setText("El correo no está registrado");
                txtEmail.setStyle(
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-border-color: #e74c3c;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-radius: 15;"
                );
                txtPassword.setStyle(
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-border-color: #e74c3c;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-radius: 15;"
                );

                txtEmail.clear();
                txtPassword.clear();
                txtEmail.requestFocus();

            }
            else {

                lblError.setText("Contraseña incorrecta");
                txtEmail.setStyle(
                        "-fx-focus-color: transparent;" +
                                "-fx-faint-focus-color: transparent;" +
                                "-fx-border-color: #2ecc71;" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 15;" +
                                "-fx-background-radius: 15;"
                );
                txtPassword.setStyle("-fx-border-color: red;" +
                        " -fx-border-width: 2;" +
                        " -fx-border-radius: 15; " +
                        "-fx-background-radius: 15;" +
                        "-fx-focus-color: transparent");

                txtPassword.clear();
                txtPassword.requestFocus();
            }


        }
    }
    @FXML
    private void goToRegister(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/Views/Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
