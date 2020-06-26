package sample.PartialControllers;

import Helpers.Language;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.ComboBoxTableCell.forTableColumn;

public class WaiterController implements Initializable, BigController {
    @FXML
    private TableView<Waiter> waiterTable;
    //-- name,email,salary,status,creation_time
    @FXML
    private TableColumn<Waiter, String> nameColumn;
    @FXML
    private TableColumn<Waiter, String> emailColumn;
    @FXML
    private TableColumn<Waiter, Number> salaryColumn;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../partials/CreateWaiterDialog.fxml"), Language.getBundle());
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

    @FXML
    public void updateName(TableColumn.CellEditEvent<Waiter,String> nameEditEvent) throws Exception {
    Waiter currentWaiter=waiterTable.getSelectionModel().getSelectedItem();
    currentWaiter.setName(nameEditEvent.getNewValue());
    PersonModel.updateWaiterOnDb(currentWaiter,currentWaiter.getEmail());
    }

    @FXML
    public void updateEmail(TableColumn.CellEditEvent<Waiter,String> emailEditEvent) throws Exception {
        Waiter currentWaiter=waiterTable.getSelectionModel().getSelectedItem();
        if(PersonModel.isUser(emailEditEvent.getNewValue()))
        {
            throw new Exception("Email taken");
        }
        PersonModel.updateWaiterOnDb(currentWaiter,emailEditEvent.getNewValue());

        currentWaiter.setEmail(emailEditEvent.getNewValue());

    }
    @FXML
    public void updateSalary(TableColumn.CellEditEvent<Waiter,Double> salaryEditEvent) throws Exception
    {
        Waiter currentWaiter=waiterTable.getSelectionModel().getSelectedItem();
        currentWaiter.setSalary(Double.parseDouble(String.valueOf(salaryEditEvent.getNewValue())));
        PersonModel.updateWaiterOnDb(currentWaiter,currentWaiter.getEmail());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<Waiter, Number>("salary"));
        joinedColumn.setCellValueFactory(new PropertyValueFactory<>("joined"));
//        -----

        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        salaryColumn.setCellFactory(TextFieldTableCell.<Waiter, Number>forTableColumn(new NumberStringConverter())
        );

        try {
            waiterTable.setItems(getWaiters());
        } catch (Exception e) {
            this.show(e);
        }

    }
}
