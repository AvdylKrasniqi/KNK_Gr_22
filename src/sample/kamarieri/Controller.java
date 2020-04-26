package sample.kamarieri;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button MenuTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

@FXML
    public void openMenu(javafx.event.ActionEvent actionEvent) throws Exception{
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("C:\\Users\\Rinor\\Desktop\\KNK_Gr_22\\src\\sample\\initialMenu\\initialMenu.fxml"));
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }

        Scene dashboard = new Scene(root);

        //This line gets the Stage Information
        //here we get the stage from event action and setting the root element in the scene and display scene with stage object (window) which is retrieved from action event
        Stage window=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);

        window.show();
    }
}
