package SpeedyProject.Controllers;

import SpeedyProject.Dao.UserDao;
import SpeedyProject.Dao.UserDaoImp;
import SpeedyProject.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass().getResource("/Views/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private TextField txtname;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private Label lblEmail;

    private UserDao dao = new UserDaoImp();


    @FXML
    public void initialize() {

        txtEmail.textProperty().addListener((obs, oldValue, newValue) -> {

            if (newValue.isEmpty()) {
                lblEmail.setText("");
                return;
            }

            if (dao.existeEmail(newValue)) {

                lblEmail.setText("Correo ya registrado");
                lblEmail.setStyle("-fx-text-fill: red;");

                txtEmail.setStyle("""
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: red;
                -fx-border-width: 2;
            """);

            } else {

                lblEmail.setText("Correo disponible");
                lblEmail.setStyle("-fx-text-fill: green;");

                txtEmail.setStyle("""
                -fx-background-radius: 10;
                -fx-border-radius: 10;
                -fx-border-color: green;
                -fx-border-width: 2;
            """);
            }
        });
    }

    @FXML
    private void register(ActionEvent event) throws IOException{
        String name = txtname.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        //Validations to show on the views
        if(name.isEmpty()|| email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
            System.out.println("not enough data");
            return;
        }
        if(!password.equals(confirmPassword)){
            System.out.println("passwords are not equal");
            return;
        }
        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        boolean isRegistered = dao.register(newUser);

        if(isRegistered){
            System.out.println("Registered successfully");
            SpeedyProject.Patterns.Singlenton.Session.getInstance().setUser(newUser);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/PrincipalMenu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            System.out.println("Failed to register in DB");
        }

    }

}
