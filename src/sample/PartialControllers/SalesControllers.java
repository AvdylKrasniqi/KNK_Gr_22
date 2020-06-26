package sample.PartialControllers;

import Models.SalesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;

import StateClasses.BigController;
import StateClasses.LoggedUser;
import StateClasses.Sales;
import StateClasses.Waiter;
import com.mysql.jdbc.log.Log;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class SalesControllers implements Initializable, BigController {

    @FXML
    private TableView<Sales> salesTable;
    @FXML
    private TableColumn<Sales, Integer> idColumn;
    @FXML
    private TableColumn<Sales, Double> amountColumn;
    @FXML
    private TableColumn<Sales, Date> dateColumn;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;


    public ObservableList<Sales> getSales(LocalDate from, LocalDate to) throws SQLException {
        ResultSet results = SalesModel.getSales(from, to);

        ObservableList<Sales> sales = FXCollections.observableArrayList();
        while (results.next()) {

            sales.add(new Sales(results.getInt(1), results.getDouble(2), results.getDate(3)));

        }
        return sales;
    }

  public void checkPermissions()
  {

      if(!LoggedUser.getStatus().equalsIgnoreCase("Admin"))
      {

          fromDatePicker.setDisable(true);
          toDatePicker.setDisable(true);
      }

  }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkPermissions();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        toDatePicker.setValue(LocalDate.now());
        fromDatePicker.setValue(LocalDate.now().minusDays(7));
        fromDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            salesTable.getItems().clear();
            try {
                salesTable.setItems(getSales(fromDatePicker.getValue(), toDatePicker.getValue()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        toDatePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            salesTable.getItems().clear();
            try {
                salesTable.setItems(getSales(fromDatePicker.getValue(), toDatePicker.getValue()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });


        try {

            salesTable.setItems(getSales(fromDatePicker.getValue(), toDatePicker.getValue()));
        } catch (Exception e) {
            this.show(e);
            e.printStackTrace();
        }


    }


}
