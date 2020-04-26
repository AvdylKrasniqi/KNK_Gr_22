package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Menu.Controller;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private static final String firstConnection = "jdbc:mysql://185.67.178.114:3306/art_knk_db";
    private static final String username = "art_knk";
    private static final String password = "OyRKDSix1BfEk0+vqgKqTbOqxYz3RVsX7R0HOL7+";
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
        try {
            initDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getHashedPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }


    private void initDb() throws SQLException {
        conn = DriverManager.getConnection(firstConnection, username, password);
    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        boolean validLogin = false;
        String sql = "Select * from Staff where email=?";


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, usernameField.getText());

        ResultSet password = stmt.executeQuery();

        try {
            if (password.next()) {
                // masi hala skem usera pe lojna qishtu tani e bojme me hashed

                if (passwordField.getText().contentEquals(password.getString("password")))
                    validLogin = true;
                else {
                    throw new Exception("Incorrect login.");
                }

            }

        } catch (Exception e) {


            Controller.printError(e);
        }

        if (validLogin) {

            Parent root = null;
            try {


                root = FXMLLoader.load(getClass().getResource("./../kamarieri/sample.fxml"));

            } catch (IOException e) {
                System.out.println("Path eshte gabim");

            }

            Scene dashboard = new Scene(root);

            //This line gets the Stage Information
            //here we get the stage from event action and setting the root element in the scene and display scene with stage object (window) which is retrieved from action event
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dashboard);

            window.show();
        }
    }


}
