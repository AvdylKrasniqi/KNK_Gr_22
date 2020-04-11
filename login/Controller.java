package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button login_button;
    @FXML
    private GridPane mainPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println(username + " " + password);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../kamarieri/sample.fxml"));
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }

        Scene dashboard = new Scene(root);

        //This line gets the Stage Information
        //here we get the stage from event action and setting the root element in the scene and display scene with stage object (window) which is retrieved from action event
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(dashboard);

        window.show();

    }
}
