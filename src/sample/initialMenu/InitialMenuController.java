package sample.initialMenu;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InitialMenuController  implements Initializable {

    private Button drinksButton;
    private Button foodButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToDrinks(ActionEvent event) throws Exception
    {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../Menu/Menu.fxml"));
        } catch (IOException e) {
            System.out.println("Wrong path!");
        }
        Scene dashboard = new Scene(root);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }
    public void goToFood(ActionEvent event) throws Exception
    {

    }
}
