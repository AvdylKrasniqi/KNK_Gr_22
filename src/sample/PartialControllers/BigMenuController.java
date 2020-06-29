package sample.PartialControllers;

import Models.MenuModel;
import StateClasses.BigController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import StateClasses.DbMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.security.Key;
import java.util.ResourceBundle;

public class BigMenuController implements Initializable, BigController {
    private String type;
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
            this.kickOut();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            tableview.setItems(MenuModel.getAllItems());


        } catch (Exception e) {
            this.show(e);

        }
        tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableview.getSelectionModel().selectedItemProperty().addListener((ov, old, _new) ->
        {
            if (_new == null)
                return;
            MenuModel.menuToUI(_new, idField, titleField, priceField);

        });


    }

    @FXML
    private void onCreateButtonClick(ActionEvent event) {
        try {
            DbMenu fromUI = MenuModel.menuFromUI(idField, titleField, priceField);
            DbMenu create = MenuModel.createItem(fromUI,type);
            tableview.getItems().add(create);
            MenuModel.clearUI(idField, titleField, priceField);

        } catch (Exception e) {
            this.show(e);
        }
    }


    @FXML
    public void changeType(KeyEvent event) throws Exception {
        if(event.getCode()== KeyCode.F&& event.isControlDown())
        {
            tableview.setItems(MenuModel.getItems("food"));
            this.type="food";
        }
        if(event.getCode()== KeyCode.D&& event.isControlDown())
        {
            tableview.setItems(MenuModel.getItems("drink"));
            this.type="drink";
        }

    }

    @FXML
    private void onUpdateButtonClick(ActionEvent event) {
        try {
            DbMenu form = MenuModel.menuFromUI(idField, titleField, priceField);
            MenuModel.updateItem(form);
            DbMenu selected = tableview.getSelectionModel().getSelectedItem();
            selected.setTitle(form.getTitle());
            selected.setPrice(form.getPrice());
            tableview.refresh();
            tableview.getSelectionModel().clearSelection();
            MenuModel.clearUI(idField, titleField, priceField);
        } catch (Exception e) {
            this.show(e);
        }

    }

    @FXML
    private void onDeleteButtonClick(ActionEvent event) {
        try {
            DbMenu tobeExecuted = tableview.getSelectionModel().getSelectedItem();
            MenuModel.removeItem(tobeExecuted.getId());
            tableview.getSelectionModel().clearSelection();
            tableview.getItems().remove(tobeExecuted);
            MenuModel.clearUI(idField, titleField, priceField);

        } catch (Exception e) {
            this.show(e);
        }

    }

}
