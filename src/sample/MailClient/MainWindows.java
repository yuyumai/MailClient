package sample.MailClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Check.Pop3;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainWindows implements Initializable {

    public TextField emailNo;
    public TextArea mailText;
    public Label mailAmount;
    private Main application;
    private Pop3 pop3 = new Pop3();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            mailAmount.setText("邮件数为" + pop3.getMailAmount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setApp(Main application){
        this.application = application;
    }

    public void sendEmail(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sendEmailWindows.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root, 620, 400));
        stage.show();
    }

    public void deleteEmail(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"确认删除？");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){
            pop3.deleteMail(emailNo.getText());
            mailText.setText("");
            emailNo.setText("");
            mailAmount.setText("邮件数为" + pop3.getMailAmount());
        }
    }

    public void submit(ActionEvent actionEvent) throws IOException {
        mailText.setText(pop3.getContent(emailNo.getText()).toString());
    }
}
