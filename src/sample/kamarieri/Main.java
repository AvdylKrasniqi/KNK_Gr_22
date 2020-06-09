
//TODO: Nese pahiri ju qet error kqyrni Run Configurations se mu po mi ndryshon path te JavaFX.
//TODO: Nese pahiri ju qet error kqyrni Run Configurations se mu po mi ndryshon path te JavaFX.
//TODO: Nese pahiri ju qet error kqyrni Run Configurations se mu po mi ndryshon path te JavaFX.
//TODO: Nese pahiri ju qet error kqyrni Run Configurations se mu po mi ndryshon path te JavaFX.


package sample.kamarieri;

import StateClasses.LoggedUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.prefs.BackingStoreException;

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;
//   3-4 fxml  status qata
    @Override
    public void start(Stage primaryStage) throws Exception{

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("eCaffe");


        primaryStage.setScene(new Scene(root, 800, 600));
       primaryStage.setResizable(false);

        primaryStage.show();
        Button btn_About = (Button) root.lookup("#AboutTab");
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
