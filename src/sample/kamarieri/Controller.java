package sample.kamarieri;

import StateClasses.BigController;
import StateClasses.Tables;
import javafx.collections.ObservableList;
import javafx.event.Event;
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
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import sample.PartialControllers.TableScreenController;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

//TODO: about ---->(Genci,Albini)  waiters-->(Avdyli,Bardhi)
public class Controller implements BigController, Initializable {
    public static int numberOfTabels;
    // tehcnically hashmapi  i dyt duhet mu kon <string,double> per produkte
    int currentAnchorPane = 0;
    private boolean[][] occupiedTable = new boolean[5][5];
    //      private ArrayList<Tables> Tavolinat= new ArrayList<>(25);
    private HashMap<Integer, Tables> Tavolinat = new HashMap<>();
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
    @FXML
    private AnchorPane switchMenu;
    @FXML
    private ToggleButton updateToggle;
    @FXML
    private ToggleButton deleteToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeOccupancy();
//        System.out.println(occupiedTables.toString());
    }

    @FXML
    public void openAbout(javafx.event.ActionEvent actionEvent) throws Exception {
        if (currentAnchorPane == 3)
            return;
        loadAnchor(".././about/about.fxml");
        currentAnchorPane = 3;
    }

    public void removeAnchor() {
// krim kunder njerezimit po spo di qysh leshi me ndreq gridpane u heartless bitch
        MainPane.getChildren().remove(4);



    }

    public void changeTable(int id, Tables table) {
        this.Tavolinat.replace(id, table);
    }

    public void loadAnchor(String path) throws Exception {
        if (currentAnchorPane != 0) {
            removeAnchor();
        }
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            MainPane.add((AnchorPane) root, 1, 1);

        } catch (IOException e) {
           this.show(e);
        }

    }

    @FXML
    public void openTables(javafx.event.ActionEvent actionEvent) throws Exception {
        if (currentAnchorPane == 1)
            return;
        loadAnchor(".././partials/Tables.fxml");
        currentAnchorPane = 1;
    }


    @FXML
    public void openMenu(javafx.event.ActionEvent actionEvent) throws Exception {
        if (currentAnchorPane == 2)
            return;
        loadAnchor(".././partials/MenuChoices.fxml");
        currentAnchorPane = 2;
    }


    public void loadViewData(String path, int id) throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = (Parent) loader.load();


        Scene newScene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.show();


        TableScreenController ctrl = loader.getController();
        ctrl.setTable(this.Tavolinat.get(id));
        ctrl.setId(id);


    }


    @FXML
    public void goToDrinks(ActionEvent actionEvent) throws Exception {

        loadView(actionEvent, "./../Menu/Menu.fxml");
    }

    @FXML
    public void goToFood(ActionEvent actionEvent) throws Exception {

        loadView(actionEvent, "./../Food/Food.fxml");

    }

    public static int[] getGridLocation(double x, double y) {
        int gridLocation[] = new int[2];

        gridLocation[0] = (int) Math.floor((x / 120.4));
        gridLocation[1] = (int) Math.floor((y / 91.2));
        return gridLocation;

    }

    public void initializeOccupancy() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                occupiedTable[i][j] = false;
            }
        }
    }

    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }



    @FXML
    public void addNewTable(MouseEvent event) throws IOException, SQLException {
        if (addToggle.isSelected()) {
            int[] gridLocation = getGridLocation(event.getX(), event.getY());
            System.out.println("GridLocation" + gridLocation[0] + gridLocation[1]);
            if (occupiedTable[gridLocation[0]][gridLocation[1]]) {
                return;
            }

            ImageView table = new ImageView(getClass().getResource(".././Images/tableforview.png").toExternalForm());
            table.setFitHeight(100);
            table.setFitWidth(100);
            Label text = new Label("Tavolina " + (numberOfTabels + 1));
            text.setPadding(new Insets(70, 0, 0, 20));
            numberOfTabels++;
            tablesGrid.add(table, gridLocation[0], gridLocation[1]);

            tablesGrid.add(text, gridLocation[0], gridLocation[1]);
            occupiedTable[gridLocation[0]][gridLocation[1]] = true;

            this.Tavolinat.put(convertFromGrid(event), new Tables());


        } else if (tableDetails(event)) {
            if (this.Tavolinat.containsKey(convertFromGrid(event)))
                loadViewData(".././partials/SpecificTable.fxml", convertFromGrid(event));
        } else if (deleteTable(event)) {
            int[] coordinates = getGridLocation(event.getX(), event.getY());
            this.Tavolinat.replace(convertFromGrid(event), null);
           // komanda e pare e hjek imageview kurse e dyta e hjek label
            tablesGrid.getChildren().remove(getNodeByRowColumnIndex(coordinates[1],coordinates[0],tablesGrid));
            tablesGrid.getChildren().remove(getNodeByRowColumnIndex(coordinates[1],coordinates[0],tablesGrid));
        }
// 
    }
    public boolean tableDetails(MouseEvent event) {
        int coordinates[] = getGridLocation(event.getX(), event.getY());
        return (updateToggle.isSelected() && occupiedTable[coordinates[0]][coordinates[1]]);
    }

    // top right bottom left
    public void addTable(MouseEvent mouseEvent) {
        // duhet me fshi kete shit
    }

    public int convertFromGrid(MouseEvent event) {
        int coordinates[] = getGridLocation(event.getX(), event.getY());
        return coordinates[0] * 5 + coordinates[1];
    }

    public boolean deleteTable(MouseEvent event) {
        int coordinates[] = getGridLocation(event.getX(), event.getY());
        return (deleteToggle.isSelected() && occupiedTable[coordinates[0]][coordinates[1]]);


    }

}