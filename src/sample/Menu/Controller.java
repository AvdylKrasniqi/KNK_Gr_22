package sample.Menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final String firstConnection = "jdbc:mysql://185.67.178.114:3306/art_knk_db";
    private static final String username = "art_knk";
    private static final String password = "OyRKDSix1BfEk0+vqgKqTbOqxYz3RVsX7R0HOL7+";

    private Connection conn;
    @FXML
    private TableView<DbMenu> tableview;
    @FXML
    private TableColumn<DbMenu, Integer> idColumn;
    @FXML
    private TableColumn<DbMenu, Double> priceColumn;
    @FXML
    private TableColumn<DbMenu, String> titleColumn;
    @FXML
    private TextField idField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField priceField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {
            initDb();
            tableview.setItems(getItems());


        } catch (Exception e) {
            printError(e);

        }

        tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableview.getSelectionModel().selectedItemProperty().addListener((ov, old, _new) ->
        {
            if (_new == null)
                return;
            menuToUI(_new);

        });


    }

    @FXML
    private void onCreateButtonClick(ActionEvent event) {
        try {
            DbMenu fromUI = menuFromUI();
            DbMenu create = createItem(fromUI);
            tableview.getItems().add(create);
            clearUI();

        } catch (Exception e) {
            printError(e);
        }
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
       try
       {
           DbMenu form=menuFromUI();
           updateItem(form);
           DbMenu selected=tableview.getSelectionModel().getSelectedItem();
           selected.setTitle(form.getTitle());
           selected.setPrice(form.getPrice());
           tableview.refresh();
           tableview.getSelectionModel().clearSelection();
            clearUI();
       }
       catch(Exception e)
       {
        printError(e);
       }

    }

    @FXML
    private void onRemoveButtonClick(ActionEvent event) {
        try
        {
            DbMenu tobeExecuted=tableview.getSelectionModel().getSelectedItem();
            removeItem(tobeExecuted.getId());
            tableview.getSelectionModel().clearSelection();
            tableview.getItems().remove(tobeExecuted);
            clearUI();

        }
        catch(Exception e)
        {
            printError(e);
        }

    }
//INSERT INTO `art_knk_db`.`Menu` (`price`, `MenuType`, `Title`) VALUES ('8', 'drink', 'arsa');

    private DbMenu createItem(DbMenu menu) throws Exception {
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO Menu (price,MenuType,Title) values(%f,'drink','%s')", menu.getPrice(), menu.getTitle());
        int affectedRows = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (affectedRows <= 0)
            throw new Exception("No row created");

        ResultSet res = stmt.getGeneratedKeys();
        if (res.next()) {
            int id = res.getInt(1);
            return new DbMenu(id, menu.getPrice(), menu.getTitle());
        }
        return null;
    }

    private void clearUI() {
        idField.setText("");
        titleField.setText("");
        priceField.setText("");

    }

    private void printError(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.toString());
        alert.showAndWait();
    }

    private void removeItem(int id ) throws Exception {
        String sql="DELETE FROM Menu where MenuId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1,id);

    int affectedRows=stmt.executeUpdate();
    if(affectedRows!=1)
        throw new Exception("Gabime ne fshirje(delete)");
    }

    private DbMenu menuFromUI() {
        String idText = idField.getText();
        int id = idText.length() > 0 ? Integer.parseInt(idText) : -1;
        String titleText = titleField.getText();
        String priceText = priceField.getText();
        double price = Double.parseDouble(priceText);
        return new DbMenu(id, price, titleText);

    }

    private void menuToUI(DbMenu menu) {
        idField.setText(Integer.toString(menu.getId()));
        titleField.setText(menu.getTitle());
        priceField.setText(Double.toString(menu.getPrice()));

    }


    private void initDb() throws SQLException {
        conn = DriverManager.getConnection(firstConnection, username, password);
    }

    private ObservableList<DbMenu> getItems() throws Exception {
        ObservableList<DbMenu> items = FXCollections.observableArrayList();
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT * FROM Menu ");
        while (res.next()) {
            int id = res.getInt(1);
            Double price = res.getDouble("price");
            String title = res.getString("Title");
            items.add(new DbMenu(id, price, title));
        }
        return items;

    }

    private void updateItem(DbMenu menu) throws Exception {
        String sql = "UPDATE Menu SET Title=?,price=? WHERE MenuId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, menu.getTitle());
        stmt.setDouble(2, menu.getPrice());
        stmt.setInt(3, menu.getId());

        int affectedRows=stmt.executeUpdate();
        if(affectedRows!=1)
            throw new Exception("Gabim ne update ");

    }
}
