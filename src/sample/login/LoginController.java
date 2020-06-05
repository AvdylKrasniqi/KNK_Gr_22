package sample.login;

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
        try {
            initDb();
        } catch (SQLException e) {
            this.show(e);
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
        conn = Dbinfo.startConnection();
    }

    @FXML
    public void login(ActionEvent event) throws Exception {
        boolean validLogin = false;
        String sql = "Select * from Staff where email=?";


        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, usernameField.getText());

        ResultSet password = stmt.executeQuery();

        try {
//            TODO: Mos me kqyr permes password.next() po duhet me ndryshu ne result.next() se edhe password ka mu dergu si field ne SQL Query.
            if (password.next()) {
                // masi hala skem usera pe lojna qishtu tani e bojme me hashed

                if (passwordField.getText().contentEquals(password.getString("password"))) {
                    validLogin = true;
//                TODO: qetu duhet me ndreq id, kur te logohet me ja marr  result.id & result.status
//                 Nqet rast po boj assume qe useri osht admin.
//                TODO: Edhe qetu osht mir me marr result.username se ka mundesi qe munet me exploit gjate kohes sa osht tu ndodh kontrollimi me ndrru tekstin ne usernameField
                    LoggedUser.setUser(-999, usernameField.getText(), LoggedUser.Status.Admin);
                } else {
                    throw new Exception("Incorrect login.");
                }
            }

        } catch (Exception e) {


            this.show(e);
        }

        if (validLogin) {

            this.loadView(event, ".././kamarieri/sample.fxml");
        }
    }


}
