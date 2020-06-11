package sample.PartialControllers;

import Helpers.PasswordGenerator;
import Models.PersonModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateWaiterController  implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField salaryField;

//    @FXML
//    public void onSavedCreatedWaiter(ActionEvent event) throws NoSuchProviderException, NoSuchAlgorithmException, SQLException {
//
//    }
//

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void onSaveCreatedWaiter(ActionEvent actionEvent) throws  Exception {

        if(PersonModel.isUser(emailField.getText()))
        {
            throw new Exception("User already exists");
        }

        PersonModel.insertUser
                (
                        nameField.getText(),
                        emailField.getText(),
                        PasswordGenerator.generateSaltedPassword(passwordField.getText()),
                        Double.parseDouble(salaryField.getText()),
                        "Waiter"
                );

    }
}
