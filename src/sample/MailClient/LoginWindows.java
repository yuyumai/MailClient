package sample.MailClient;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindows implements Initializable {
    public TextField account;
    public PasswordField password;
    private Main application;


    public void setApp(Main application){
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void LOGIN_M(ActionEvent actionEvent) {
        application.setUser(account.getText());
        application.setPassword(password.getText());
        application.userLogin(account.getText(), password.getText());
    }

    public void CLEAR_M(ActionEvent actionEvent) {
    }
}
