package StateClasses;

import Helpers.Language;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.PartialControllers.TableScreenController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public interface BigController {

    default public void kickOut() throws Exception {
        if(!LoggedUser.loggedIn())
            throw new Exception("Nuk je logged in");

//        if (!LoggedUser.loggedIn()) {
//            System.out.println("JA KU JAM ");
//            Parent nodeRoot = null;
//            try {
//
//                nodeRoot = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
//            } catch (Exception e) {
//                this.show(e);
//            }
//
//
//            Scene dashboard = new Scene(nodeRoot);
//
//            Stage mainStage = new Stage();
//            mainStage.setScene(dashboard);
//            mainStage.show();

//    }

}


    default public void hideTopSecret(Pane root, Button waiterButton, ImageView waiterImage) {
        try {//currentstage.close()
            if (LoggedUser.getStatus().equalsIgnoreCase("waiter")) {
                waiterButton.setVisible(false);
                root.getChildren().remove(waiterButton);
                waiterImage.setVisible(false);
                root.getChildren().remove(waiterImage);
            }
        } catch (Exception e) {
            // me qellim ky fucking bug  its a feature
        }

    }

    default public void loadView(Event actionEvent, String path) {
        Parent nodeRoot = null;

        try {

            nodeRoot = FXMLLoader.load(getClass().getResource(path), Language.getBundle());


        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }


        Scene dashboard = new Scene(nodeRoot);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }

    default public double getPrice(String title) throws Exception {
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



    default public void show(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
     if(e.getMessage().equalsIgnoreCase("Nuk je logged in"))
         System.exit(1);

    }

}
