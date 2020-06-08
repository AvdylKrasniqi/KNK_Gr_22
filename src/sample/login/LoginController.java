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

import StateClasses.LoggedUser;

public class LoginController implements BigController, Initializable {

    private Connection conn;

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
        String email = login_button.getText();
        String password=passwordField.getText();
        String saltedPassword=PasswordGenerator.generateSaltedPassword(password);

        if(PasswordGenerator.checkPassword(email,password)) {
           ResultSet result = PersonModel.returnInfo(email);
         if(result.next())
             LoggedUser.setUser(result.getInt("id"),result.getString("name"), PersonModel.finalRole(email));
        }
        else
            throw new Exception("Passwordi eshte gabim");
    }

}
