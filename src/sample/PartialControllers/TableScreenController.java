package sample.PartialControllers;

import StateClasses.Tables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Menu.DbMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.kamarieri.Controller;
import StateClasses.Dbinfo;

import javax.swing.*;

public class TableScreenController implements Initializable {
    private int id;
    private Tables table;
    @FXML
    private CheckBox yesCheck;
    @FXML
    private CheckBox noCheck;
    @FXML
    private TextField quantityField;
    @FXML
    private Label tableTitle;
    @FXML
    private Label tableCapacity;
    @FXML
    private ComboBox menuCombo;

    public void setTable(Tables table) throws SQLException {
        this.table = table;
        this.tableCapacity.setText("Kjo tavoline ka " + this.table.getCapacity() + " karrika");

        if (this.table.getOccupied())
            this.yesCheck.setSelected(true);
        else this.noCheck.setSelected(true);
        getMenuItems();
    }
    public void setId(int id )
    {
        this.id=id;
    }

    @FXML
    public void cleanThings(ActionEvent event) {


    }


    public void getMenuItems() throws SQLException {
        ArrayList<String> items = new ArrayList<>();
        Connection con = Dbinfo.startConnection();
        String sql = "SELECT Title FROM Menu ";
        PreparedStatement statement = con.prepareStatement(sql);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            items.add(result.getString(1));
        }
        ObservableList<String> observableItems = FXCollections.observableArrayList(items);
        menuCombo.setItems(observableItems);
        con.close();
    }


    public boolean validateQuantityField(String quantity) {
        return (!((Integer) Integer.parseInt(quantity) instanceof Integer) || Integer.parseInt(quantity) < 0);

    }


    @FXML
    public void saveTable(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".././kamarieri/sample.fxml"));
        Parent root = (Parent) loader.load();
        Controller controller = loader.getController();
       controller.changeTable(this.id,this.table);
        Stage stage = (Stage)((Node)((ActionEvent) event).getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    public void addItem(ActionEvent event) throws SQLException {
        String currentItem = (String) menuCombo.getValue();
        if (quantityField.getText().equals("") || validateQuantityField(quantityField.getText()))
            return;
        int quantity = Integer.parseInt(quantityField.getText());
        Connection con = Dbinfo.startConnection();
        String sql = "Select price from Menu where Title=?";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, currentItem);
        ResultSet result = statement.executeQuery();

        Double price;
        if (result.next()) {
            price = result.getDouble(1);

        } else return;

        this.table.addProduct(quantity +" " + currentItem, price, quantity);
        this.table.increaseTotalPrice(price*quantity);
        System.out.println(this.table.toString());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getMenuItems();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

    }
}