package sample.PartialControllers;

import StateClasses.BigController;
import StateClasses.Products;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Menu.DbMenu;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SpecificTableController implements BigController, Initializable {
    @FXML
    private TableView<Products> mainTable;
    @FXML
    private TableColumn<Products, String> titulliColumn;
    @FXML
    private TableColumn<Products, Double> cmimiColumn;
    @FXML
    private TableColumn<Products, Integer> kualitetiColumn;

    private HashMap<String, Double> products;

    @FXML
    public void printThem()
    {
        System.out.println(this.products);
    }

    public void setProducts(HashMap products) {
        this.products = products;

    }

    public void setSpecificProducts(HashMap products) throws Exception {


        setProducts(products);
        ObservableList<Products> items = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : this.products.entrySet()) {
            String title = entry.getKey();
            Double price = entry.getValue();

            int quantity = (int) (price / this.getPrice(title));
            items.add(new Products(title, price, quantity));

        }

        mainTable.setItems(items);
//       mainTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titulliColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        cmimiColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        kualitetiColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }
}
