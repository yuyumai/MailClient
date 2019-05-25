package sample.MailClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Check.Pop3;

import java.io.InputStream;

public class Main extends Application {

    public Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 400;
    private final double MINIMUM_WINDOW_HEIGHT = 250.0;
    public Pop3 pop3 = new Pop3();
    public static String user;
    public static String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        Main.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Main.password = password;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        gotoLogin();
        stage.show();
    }

    public void gotoLogin(){
        try{
            LoginWindows login = (LoginWindows)replaceSceneContent("LoginWindows.fxml", 400, 200);
            login.setApp(this);

        }catch(Exception e){

        }
    }

    public void gotoSendEmail(){
        try {
            SendEmailWindows send = (SendEmailWindows) replaceSceneContent("SendEmailWindows.fxml", 620, 400);
            send.setApp(this);
        } catch (Exception ex) {
        }
    }

    public void gotoMain(){
        try {
            MainWindows main = (MainWindows) replaceSceneContent("MainWindows.fxml", 680, 400);
            main.setApp(this);
        } catch (Exception ex) {
        }
    }

    public void userLogin(String account,String password) {
        if(pop3.check(account, password)){
            gotoMain();
        }
        else{
            Alert error = new Alert(Alert.AlertType.ERROR,"登陆信息错误");
            error.showAndWait();
        }
    }

    private Initializable replaceSceneContent(String fxml, double width, double height) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
