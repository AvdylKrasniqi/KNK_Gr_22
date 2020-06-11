package sample.PartialControllers;

import Helpers.PasswordGenerator;
import Models.PersonModel;
import StateClasses.BigController;
import StateClasses.Dbinfo;
import StateClasses.Waiter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Menu.DbMenu;


import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class WaiterController implements Initializable, BigController {
    @FXML
    private TableView<Waiter> waiterTable;
    //-- name,email,salary,status,creation_time
    @FXML
    private TableColumn<Waiter, String> nameColumn;
    @FXML
    private TableColumn<Waiter, String> emailColumn;
    @FXML
    private TableColumn<Waiter, Double> salaryColumn;
    @FXML
    private TableColumn<Waiter, String> joinedColumn;


    public ObservableList<Waiter> getWaiters() throws Exception {

        ObservableList<Waiter> waiters = FXCollections.observableArrayList();
        ResultSet waitersFromDb = PersonModel.getAllWaiters();
        while (waitersFromDb.next()) {
            waiters.add(
                    new Waiter(waitersFromDb.getString("name"),
                            waitersFromDb.getString("email"),
                            waitersFromDb.getDouble("salary"),
                            waitersFromDb.getDate("creation_time")
                    )
            );
        }

        return waiters;

    }


    @FXML
    public void onCreateWaiter(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../partials/CreateWaiterDialog.fxml"));
        Parent root = (Parent) loader.load();


        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();

    }

    @FXML
    public void onUpdateWaiter(ActionEvent event) {

    }


    public static void removeItem(String email) throws Exception {

        Connection conn = Dbinfo.startConnection();
        String sql = "DELETE FROM Staff where email=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows != 1)
            throw new Exception("Gabime ne fshirje(delete)");
    }

    @FXML
    public void onDeleteWaiter(ActionEvent event) {
        try {
            Waiter tobeExecuted = waiterTable.getSelectionModel().getSelectedItem();
            removeItem(tobeExecuted.getEmail());
            waiterTable.getSelectionModel().clearSelection();
            waiterTable.getItems().remove(tobeExecuted);


        } catch (Exception e) {
            this.show(e);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
            joinedColumn.setCellValueFactory(new PropertyValueFactory<>("joined"));
            System.out.println("test i initialize");
            waiterTable.setItems(getWaiters());
        } catch (Exception e) {
            this.show(e);
        }

    }
}
