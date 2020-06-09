package sample.Menu;

import StateClasses.LoggedUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.prefs.BackingStoreException;

public class Menu extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{





        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
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
