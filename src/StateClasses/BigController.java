package StateClasses;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.PartialControllers.TableScreenController;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface  BigController {
   default  public void loadView(Event actionEvent, String path) {
        Parent nodeRoot = null;
        try {
            nodeRoot = FXMLLoader.load(getClass().getResource(path));


        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }



        Scene dashboard = new Scene(nodeRoot);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }
   default  public double getPrice(String title) throws Exception {
        Connection con = Dbinfo.startConnection();
        String sql = "Select price from Menu where Title=?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, title);
        ResultSet rez = stmt.executeQuery();
        double price;
        if (rez.next()) {
            price = rez.getDouble(1);
            con.close();
            return price;
        } else {
            con.close();
            throw new Exception("Gabim!");
        }


    }
    default public  void show(Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

}
