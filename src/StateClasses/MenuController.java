package StateClasses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import sample.Menu.DbMenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public interface MenuController {
    default DbMenu createItem(DbMenu menu) throws Exception {
        Connection conn = Dbinfo.startConnection();
        Statement stmt = conn.createStatement();
        String sql = String.format("INSERT INTO Menu (price,MenuType,Title) values(%f,'food','%s')", menu.getPrice(), menu.getTitle());
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

    default void clearUI(javafx.scene.control.TextField id, javafx.scene.control.TextField title, javafx.scene.control.TextField price) {
        id.setText("");
        title.setText("");
        price.setText("");

    }

    default void removeItem(int id) throws Exception {
        Connection conn = Dbinfo.startConnection();
        String sql = "DELETE FROM Menu where MenuId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        int affectedRows = stmt.executeUpdate();
        if (affectedRows != 1)
            throw new Exception("Gabime ne fshirje(delete)");
    }

    default DbMenu menuFromUI(TextField idField, TextField titleField, TextField priceField) {
        String idText = idField.getText();
        int id = idText.length() > 0 ? Integer.parseInt(idText) : -1;
        String titleText = titleField.getText();
        String priceText = priceField.getText();
        double price = Double.parseDouble(priceText);
        return new DbMenu(id, price, titleText);

    }

    default ObservableList<DbMenu> getItems(String type) throws Exception {
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


    default void updateItem(DbMenu menu) throws Exception {
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

    default void menuToUI(DbMenu menu, TextField idField, TextField titleField, TextField priceField) {
        idField.setText(Integer.toString(menu.getId()));
        titleField.setText(menu.getTitle());
        priceField.setText(Double.toString(menu.getPrice()));

    }

}







