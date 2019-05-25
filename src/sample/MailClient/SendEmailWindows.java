package sample.MailClient;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sample.Check.Smtp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SendEmailWindows implements Initializable {
    public TextField subject;
    public TextArea text;
    public TextArea attach;
    public TextField reciver;
    private Main application = new Main();
    private String user;
    private String password;
    private Smtp smtp;
    public static  ArrayList<String> fileNames = new ArrayList<String>();
    FileChooser fileChooser = new FileChooser();


    public void setApp(Main application){
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user = application.getUser();
        password = application.getPassword();
        smtp = new Smtp(user, password);
    }

    public void openFile(ActionEvent actionEvent) {
        fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(application.stage);
        if(selectedFile.getPath() != null){
            fileNames.add(selectedFile.getPath());
            attach.setText(attach.getText() + "\n" + selectedFile.getPath());
        }
    }

    public void sendEmail(ActionEvent actionEvent) {
        smtp.sendEmail(user, password, reciver.getText(), subject.getText(), text.getText(), fileNames);
    }

    public void deleteFile(ActionEvent actionEvent) {
        fileNames.clear();
        attach.setText("");
    }

    public void success() {
        Alert information = new Alert(Alert.AlertType.INFORMATION,"发送成功");
        information.showAndWait();
    }

    public void fail() {
        Alert error = new Alert(Alert.AlertType.ERROR,"发送失败");
        error.showAndWait();
    }
}
