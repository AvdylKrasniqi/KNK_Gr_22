package sample.PartialControllers;

import Helpers.Language;
import Helpers.PasswordGenerator;
import Models.PersonModel;
import StateClasses.Waiter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class CreateWaiterController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField salaryField;
    private FXMLLoader loader;

    public void updateWaiterController(Waiter waiter) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".././partials/Waiter.fxml"), Language.getBundle());
        Parent root =loader.load();
        WaiterController controller=loader.getController();
        controller.updateWaiters(waiter);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onSaveCreatedWaiter(ActionEvent actionEvent) throws Exception {

        if (PersonModel.isUser(emailField.getText())) {
            throw new Exception("User already exists");
        }


        PersonModel.insertUser
                (
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        Double.parseDouble(salaryField.getText()),
                        "Waiter"
                );
            Waiter updateWaiter = new Waiter(nameField.getText(),emailField.getText(),Double.parseDouble(salaryField.getText()),new Date());
            updateWaiterController(updateWaiter);
     
           Stage currentStage = (Stage) nameField.getScene().getWindow();
        currentStage.close();


    }

}
