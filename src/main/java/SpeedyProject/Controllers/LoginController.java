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
    private Label lblError;

    private UserDao dao = new UserDaoImp();

    @FXML
    private void login(ActionEvent event) throws IOException{
        User user = dao.login(txtEmail.getText(), txtPassword.getText());
        if(user != null){
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
        } else {
            lblError.setText("Correo o contraseña incorrectos");

            txtEmail.setStyle("""
        -fx-background-radius: 10;
        -fx-border-radius: 10;
        -fx-border-color: red;
        -fx-border-width: 2;
    """);

            txtPassword.setStyle("""
        -fx-background-radius: 10;
        -fx-border-radius: 10;
        -fx-border-color: red;
        -fx-border-width: 2;
    """);
        }
    }
    @FXML
    private void goToRegister(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(
                getClass().getResource("/Views/Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
