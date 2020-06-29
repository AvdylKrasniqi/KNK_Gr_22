package Models;

import StateClasses.Dbinfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import StateClasses.DbMenu;

import java.sql.*;
import java.util.Observable;

public class MenuModel {
    public static ResultSet getItem() throws SQLException {
        Connection con = Dbinfo.startConnection();
        String query = "SELECT * FROM Menu Order by MenuId DESC limit 1";
        PreparedStatement stmt = con.prepareStatement(query);
        return stmt.executeQuery();

    }

    public static DbMenu createItem(DbMenu menu, String type) throws Exception {

        if (!type.equalsIgnoreCase("drink") && !type.equalsIgnoreCase("food")) {
            throw new Exception("You didn't pick a type");
        }
        Connection conn = Dbinfo.startConnection();
        String sql = "INSERT INTO Menu (price,MenuType,Title) values(?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1, menu.getPrice());
        stmt.setString(2, type);
        stmt.setString(3, menu.getTitle());
        stmt.executeUpdate();
        ResultSet result = getItem();
        if (result.next()) {
            return new DbMenu(result.getInt(1), result.getDouble("price"), result.getString("Title"));


        } else throw new Exception("Something went wrong with adding ");

    }

    public static ObservableList<DbMenu> getAllItems() throws SQLException {
        Connection conn = Dbinfo.startConnection();
        ObservableList<DbMenu> items = FXCollections.observableArrayList();
        String query = "SELECT * FROM Menu";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet res = stmt.executeQuery();


        while (res.next()) {
            int id = res.getInt(1);
            double price = res.getDouble("price");
            String title = res.getString("Title");
            items.add(new DbMenu(id, price, title));
        }
        return items;

    }


    public static void clearUI(javafx.scene.control.TextField id, javafx.scene.control.TextField title, javafx.scene.control.TextField price) {
        id.setText("");
        title.setText("");
        price.setText("");

    }

    public static void removeItem(int id) throws Exception {
        Connection conn = Dbinfo.startConnection();
        String sql = "DELETE FROM Menu where MenuId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows != 1)
            throw new Exception("Gabime ne fshirje(delete)");
    }

    public static DbMenu menuFromUI(TextField idField, TextField titleField, TextField priceField) {
        String idText = idField.getText();
        int id = idText.length() > 0 ? Integer.parseInt(idText) : -1;
        String titleText = titleField.getText();
        String priceText = priceField.getText();
        double price = Double.parseDouble(priceText);
        return new DbMenu(id, price, titleText);

    }

    public static ObservableList<DbMenu> getItems(String type) throws Exception {
        Connection conn = Dbinfo.startConnection();
        ObservableList<DbMenu> items = FXCollections.observableArrayList();
        String query = "SELECT * FROM Menu  where MenuType=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, type);
        ResultSet res = stmt.executeQuery();


        while (res.next()) {
            int id = res.getInt(1);
            double price = res.getDouble("price");
            String title = res.getString("Title");
            items.add(new DbMenu(id, price, title));
        }
        return items;
    }


    public static void updateItem(DbMenu menu) throws Exception {
        Connection conn = Dbinfo.startConnection();

        String sql = "UPDATE Menu SET Title=?,price=? WHERE MenuId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, menu.getTitle());
        stmt.setDouble(2, menu.getPrice());
        stmt.setInt(3, menu.getId());

        int affectedRows = stmt.executeUpdate();
        if (affectedRows != 1)
            throw new Exception("Gabim ne update ");

    }

    public static void menuToUI(DbMenu menu, TextField idField, TextField titleField, TextField priceField) {
        idField.setText(Integer.toString(menu.getId()));
        titleField.setText(menu.getTitle());
        priceField.setText(Double.toString(menu.getPrice()));

    }

}







