package sample.about;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class About extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource(".././kamarieri/sample.fxml"));
        primaryStage.setTitle("eCaffe");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}