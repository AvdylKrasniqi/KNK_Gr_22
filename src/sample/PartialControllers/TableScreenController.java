package sample.PartialControllers;

import Helpers.Language;
import Models.SalesModel;
import StateClasses.BigController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import sample.kamarieri.Controller;
import StateClasses.Tables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

import StateClasses.Dbinfo;


public class TableScreenController implements BigController, Initializable {
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
    @FXML
    private TableView detailsTable;
    @FXML
    private TableColumn productColumn;
    @FXML
    private TableColumn quantityColumn;
    @FXML
    private TableColumn priceColumn;
    @FXML
    private Text orderText;


    public void setTable(Tables table) throws SQLException {
        this.table = table;
        this.tableCapacity.setText("Kjo tavoline ka " + this.table.getCapacity() + " karrika");

        if (this.table.getOccupied())
            this.yesCheck.setSelected(true);
        else this.noCheck.setSelected(true);
        getMenuItems();
    }

    public void setId(int id) {
        this.id = id;
    }

    @FXML
    public void clearTable() throws Exception {
        SalesModel.insertSales(this.table.getId(), this.table.getTotalPrice());

        this.table.clearTable();
        this.noCheck.setSelected(true);
        this.yesCheck.setSelected(false);
        Controller.numerOfActiveTables--;

    }

    @FXML
    public void goToDetails(ActionEvent actionEvent) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(".././partials/Detajet.fxml"), Language.getBundle());
        Parent root = loader.load();
        SpecificTableController ctrl = loader.getController();
        ctrl.setSpecificProducts(this.table.getProducts());
        Scene dashboard = new Scene(root);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();


    }

    public double getPriceId() throws Exception {
        Connection con = Dbinfo.startConnection();
        String sql = "Select price from Menu where id=?";
        PreparedStatement stmt = con.prepareStatement(sql);

        ResultSet rez = stmt.executeQuery();
        double price;
        if (rez.next()) {
            price = rez.getDouble(1);
            return price;
        } else {
            throw new Exception("Gabim!");
        }

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
    public void saveTable(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(".././kamarieri/sample.fxml"), Language.getBundle());
        Parent root = (Parent) loader.load();
        Controller controller = loader.getController();
        controller.changeTable(this.id, this.table);

        Controller.numerOfActiveTables++;
        Stage stage = (Stage) ((Node) ((ActionEvent) event).getSource()).getScene().getWindow();
        stage.close();


    }



    @FXML
    public void addItem(ActionEvent event) throws SQLException {
        this.table.occupyTable();
        this.yesCheck.setSelected(true);
        this.noCheck.setSelected(false);
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

        this.table.addProduct(currentItem, price, quantity);
        this.table.increaseTotalPrice(price * quantity);
        System.out.println(this.table.toString());

        con.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getMenuItems();

        } catch (SQLException e) {
            this.show(e);
        }
//        updateActive();

    }
}