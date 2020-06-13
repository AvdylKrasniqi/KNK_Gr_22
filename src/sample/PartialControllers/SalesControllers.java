package sample.PartialControllers;

import Models.SalesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;

import StateClasses.BigController;
import StateClasses.Sales;
import StateClasses.Waiter;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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


    public ObservableList<Sales> getSales(Date from, Date to) throws SQLException {
        ResultSet results = SalesModel.getSales(from, to);

        ObservableList<Sales> sales = FXCollections.observableArrayList();
        while (results.next()) {

            sales.add(new Sales(results.getInt(1), results.getDouble(2), results.getDate(3)));

        }
        return sales;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());

        Date to = (Date) c.getTime();
        c.add(Calendar.DAY_OF_MONTH, -7);
        Date from = (Date) c.getTime();


        try {
            System.out.println(from);
            System.out.println(to);
            salesTable.setItems(getSales(from, to));
        } catch (Exception e) {
            this.show(e);
           e.printStackTrace();
        }
    }


}
