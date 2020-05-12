package sample.PartialControllers;

import StateClasses.Tables;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.kamarieri.Controller;

import javax.swing.*;

public class TableScreenController implements Initializable {
    private Tables table;
    @FXML
    private CheckBox yesCheck;
    @FXML
    private CheckBox noCheck;
    @FXML
    private TextField quantityField;
    @FXML
    private Label tableTitle;

    public void setTable(Tables table) {
        this.table = table;
    if(this.table.getOccupied())
        this.yesCheck.setSelected(true);
    else this.noCheck.setSelected(true);
    }



    @FXML
    public void cleanThings(ActionEvent event)
    {


        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}