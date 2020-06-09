package sample.Menu;

import StateClasses.BigController;
import StateClasses.MenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.sql.*;

import StateClasses.Dbinfo;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements BigController, MenuController,Initializable {


    //db info
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
    @FXML
    private Button waiterButton;
    @FXML
    private ImageView waiterImage;
    @FXML
    private Pane menuPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        try {

            tableview.setItems(getItems("drink"));

        } catch (Exception e) {
            this.show(e);



        }

        tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableview.getSelectionModel().selectedItemProperty().addListener((ov, old, _new) ->
        {
            if (_new == null)
                return;
            menuToUI(_new,idField,titleField,priceField);

        });
        try {
            this.kickOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.hideTopSecret(menuPane,waiterButton,waiterImage);


    }

    @FXML
    private void onCreateButtonClick(ActionEvent event) {
        try {
            DbMenu fromUI = menuFromUI(idField,titleField,priceField);
            DbMenu create = createItem(fromUI);
            tableview.getItems().add(create);
            clearUI(idField,titleField,priceField);

        } catch (Exception e) {
            this.show(e);
        }
    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        try {
            DbMenu form = menuFromUI(idField,titleField,priceField);
            updateItem(form);
            DbMenu selected = tableview.getSelectionModel().getSelectedItem();
            selected.setTitle(form.getTitle());
            selected.setPrice(form.getPrice());
            tableview.refresh();
            tableview.getSelectionModel().clearSelection();
            clearUI(idField,titleField,priceField);
        } catch (Exception e) {
            this.show(e);
        }

    }

    @FXML
    private void onRemoveButtonClick(ActionEvent event) {
        try {
            DbMenu tobeExecuted = tableview.getSelectionModel().getSelectedItem();
            removeItem(tobeExecuted.getId());
            tableview.getSelectionModel().clearSelection();
            tableview.getItems().remove(tobeExecuted);
            clearUI(idField,titleField,priceField);

        } catch (Exception e) {
            this.show(e);
        }

    }





}
