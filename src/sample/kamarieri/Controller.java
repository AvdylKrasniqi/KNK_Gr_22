package sample.kamarieri;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

//TODO: about ---->(Genci,Albini)  waiters-->(Avdyli,Bardhi)
public class Controller implements Initializable {
    public static int numberOfTabels;
  // tehcnically hashmapi  i dyt duhet mu kon <string,double> per produkte 
  private HashMap<int[],Character> occupiedTables = new HashMap<>();

    private HashMap<String,HashMap> table = new HashMap<>();
    @FXML
    private GridPane tablesGrid;
    @FXML
    private AnchorPane tablePane;
    @FXML
    private Button MenuTab;
    @FXML
    private AnchorPane aboutPane;
    @FXML
    private GridPane MainPane;
    @FXML
    private ToggleButton addToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeOccupancy();

        System.out.println(occupiedTables.toString());
    }

    @FXML
    public void openAbout(javafx.event.ActionEvent actionEvent) throws Exception {

        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(".././about/about.fxml"));
            MainPane.add((AnchorPane) root, 1, 1);

        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
    }


    @FXML
    public void openTables(javafx.event.ActionEvent actionEvent) throws Exception {

        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(".././partials/Tables.fxml"));
            MainPane.add((AnchorPane) root, 1, 1);

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }


    @FXML
    public void openMenu(javafx.event.ActionEvent actionEvent) throws Exception {


        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(".././partials/MenuChoices.fxml"));
            MainPane.add((AnchorPane) root, 1, 1);
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }

    }

    @FXML
    public void goToDrinks(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../Menu/Menu.fxml"));

        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
        Scene dashboard = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }

    @FXML
    public void goToFood(ActionEvent actionEvent) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("./../Food/Food.fxml"));
        } catch (IOException e) {
            System.out.println("Path eshte gabim");
        }
        Scene dashboard = new Scene(root);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(dashboard);
        window.show();
    }

    public static int[] getGridLocation(double x, double y) {
        int gridLocation[] = new int[2];

        gridLocation[0] = (int) Math.floor((x / 120.4));
        gridLocation[1] = (int) Math.floor((y / 91.2));
        return gridLocation;

    }

    public  void initializeOccupancy()
    {
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                int a[]={i,j};
                occupiedTables.put(a,'n');

            }
        }
    }



    @FXML
    public void addNewTable(MouseEvent event) {
        if (addToggle.isSelected()) {
            int[] gridLocation = getGridLocation(event.getX(), event.getY());
            System.out.println("GridLocation" + gridLocation[0] + gridLocation[1]);
            System.out.println(occupiedTables.get(gridLocation));
            if(occupiedTables.containsKey(gridLocation)&&  occupiedTables.get(gridLocation)=='y')
            {
                return ;
            }
            ImageView table = new ImageView(getClass().getResource(".././Images/tableforview.png").toExternalForm());
            table.setFitHeight(100);
            table.setFitWidth(100);
            Label text = new Label("Tavolina " + (numberOfTabels + 1));
            text.setPadding(new Insets(70, 0, 0, 20));
            numberOfTabels++;
            tablesGrid.add(table, gridLocation[0], gridLocation[1]);
            tablesGrid.add(text, gridLocation[0], gridLocation[1]);
            occupiedTables.replace(gridLocation,'n');
        }
    }

    // top right bottom left
    public void addTable(MouseEvent mouseEvent) {
        // duhet me fshi kete shit
    }
}
