package sample.kamarieri;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button MenuTab;
    @FXML
    private AnchorPane aboutPane;
    @FXML
    private GridPane MainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void openAbout(javafx.event.ActionEvent actionEvent) throws Exception
    {

        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(".././about/about.fxml"));
            MainPane.add((AnchorPane)root,1,1);
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
    }

    @FXML
    public void openMenu(javafx.event.ActionEvent actionEvent) throws Exception{


        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(".././partials/MenuChoices.fxml"));
            MainPane.add((AnchorPane)root,1,1);
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }

    }

    @FXML
    public void goToDrinks(ActionEvent actionEvent)
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../Menu/Menu.fxml"));

        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
        Scene dashboard = new Scene(root);
         Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }

    @FXML
    public void goToFood(ActionEvent actionEvent)
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../Food/Food.fxml"));
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
        Scene dashboard = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }
}
