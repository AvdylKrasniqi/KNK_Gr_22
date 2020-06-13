package sample.login;

import Helpers.PasswordGenerator;
import Models.PersonModel;
import StateClasses.BigController;
import StateClasses.Dbinfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import StateClasses.LoggedUser;

public class LoginController implements BigController, Initializable {

    private Connection conn;

    @FXML
    private Button loginButton;
    @FXML
    private GridPane mainPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoggedUser.initialize();


    }
@FXML
public void loginWithEnter(KeyEvent event) throws Exception
{

   if(event.getCode()== KeyCode.ENTER)
      loginButton.fire();


}


    @FXML
    public void login(ActionEvent event) throws Exception {
        try {
            String email = usernameField.getText();
            String password = passwordField.getText();


            if (PasswordGenerator.checkPassword(email, password)) {
                ResultSet result = PersonModel.returnInfo(email);
                Preferences userPreferences = Preferences.userRoot();
                userPreferences.putInt("id", result.getInt("id"));
                userPreferences.put("name", result.getString("name"));
                userPreferences.put("status", result.getString("status"));
                userPreferences.putBoolean("loggedIn", true);


                this.loadView(event, ".././kamarieri/sample.fxml");

            } else
                throw new Exception("Passwordi eshte gabim");
        } catch (Exception e) {
            this.show(e);
        }
    }

}
