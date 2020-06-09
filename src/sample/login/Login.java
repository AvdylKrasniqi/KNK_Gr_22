package sample.login;

import StateClasses.LoggedUser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Connection;
import java.util.prefs.BackingStoreException;

public class Login extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("eCaffe");

        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);
        primaryStage.show();
primaryStage.setOnCloseRequest(e->{
    try {
        LoggedUser.logOut();
    } catch (BackingStoreException backingStoreException) {
        System.out.println("eshte bere gje e keqe");
    }
});

    }



    public static void main(String[] args) {
        launch(args);
    }

}
