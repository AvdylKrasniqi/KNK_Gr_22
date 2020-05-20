
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

public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("eCaffe");

        /*
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

         */


        primaryStage.setScene(new Scene(root, 800, 600));
       primaryStage.setResizable(false);

        primaryStage.show();
        Button btn_About = (Button) root.lookup("#AboutTab");
//        TODO: po supozoj qe osht kamarier
        LoggedUser.setUser(-1, "avdyl", false);
        Pane myPane = (Pane) root.lookup("#MyPane");
        if(!LoggedUser.isAdmin()) {
            myPane.getChildren().remove(btn_About);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
