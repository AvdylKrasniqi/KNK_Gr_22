package sample.kamarieri;

import Helpers.Language;
import Models.SalesModel;
import Models.TableModel;
import StateClasses.BigController;
import StateClasses.LoggedUser;
import StateClasses.Tables;
import com.mysql.jdbc.log.Log;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.Window;
import sample.PartialControllers.HelpController;
import sample.PartialControllers.TableScreenController;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.prefs.Preferences;

//TODO: about ---->(Genci,Albini)  waiters-->(Avdyli,Bardhi)
public class Controller implements BigController, Initializable {
    public static int numberOfTabels=0;
    public static int numerOfActiveTables=0;
    // tehcnically hashmapi  i dyt duhet mu kon <string,double> per produkte
    int currentAnchorPane = 0;
    private boolean[][] occupiedTable = new boolean[5][5];
    //      private ArrayList<Tables> Tavolinat= new ArrayList<>(25);
    private HashMap<Integer, Tables> Tavolinat = new HashMap<>();
    // sales ne databaze 

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
    @FXML
    private Button waiterButton;
    @FXML
    private ImageView waiterImage;
    @FXML
    private Pane menuPane;
    @FXML
    private Label userName;
    @FXML
    private ContextMenu tableContext = new ContextMenu();

    @FXML
    private ContextMenu tableFullContext = new ContextMenu();
    @FXML
    private MenuItem add = new MenuItem("add");
    @FXML
    private MenuItem update = new MenuItem("Update");
    @FXML
    private MenuItem delete = new MenuItem("delete");
    @FXML
    private Label numberOfTable;
    @FXML
    private Label numberOfActiveTables;
    @FXML
    private Label brutoEarnings;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            goToSales();
        } catch (Exception e) {

        }

        try {
            userName.setText(LoggedUser.getName());
        } catch (Exception e) {

        }
        try {
            ResultSet result = TableModel.getTables();
            System.out.println(numberOfTabels);
            while (result.next()) {
                System.out.println("test 1");
                if (occupiedTable[result.getInt(2)][result.getInt(3)]) {
                    System.out.println("test 2");
                    continue;
                }

                ImageView table = new ImageView(getClass().getResource(".././Images/tableforview.png").toExternalForm());
                table.setFitHeight(100);
                table.setFitWidth(100);
                Label text = new Label("Tavolina " + (numberOfTabels + 1));
                text.setPadding(new Insets(70, 0, 0, 20));

                tablesGrid.add(table, result.getInt(2), result.getInt(3));

                tablesGrid.add(text, result.getInt(2), result.getInt(3));
                occupiedTable[result.getInt(2)][result.getInt(3)] = true;
                int place = result.getInt(2) * 5 + result.getInt(3);
                this.Tavolinat.put(place, new Tables(place));

                numberOfTabels++;
            }
        } catch (Exception e) {

        }

        try {
            this.kickOut();
        } catch (Exception e) {
            this.show(e);
        }
        this.hideTopSecret(menuPane, waiterButton, waiterImage);

        if (LoggedUser.getStatus().equalsIgnoreCase("Admin")) {
            tableContext.getItems().addAll(add);
            tableFullContext.getItems().addAll(update, delete);
        } else {
            tableFullContext.getItems().add(update);
        }

        try {
            updateStatus();
        } catch (Exception exception) {

        }

    }

    public void updateNumberOfTables() {
        this.numberOfTable.setText("Numri i tavolinave: " + numberOfTabels);
    }

    public void updateActiveTables() {
        int counter = 0;
        for (Map.Entry<Integer, Tables> entry : this.Tavolinat.entrySet()) {
            if (entry.getValue().getOccupied())
                counter++;

        }

        this.numberOfActiveTables.setText("Numri i tavolinave te zena: " + counter);
    }


    public void updateStatus() throws Exception {
        updateNumberOfTables();
        updateActiveTables();
        updateEarnings();
    }

    public void updateEarnings() throws Exception {
        int todaySales = SalesModel.getTodaysSales();
        System.out.println(todaySales);
        this.brutoEarnings.setText("Fitimi bruto  per sot: " + todaySales);


    }


    @FXML
    public void logOut(ActionEvent event) throws Exception {
        LoggedUser.logOut();
        this.loadView(event, "../login/login.fxml");
    }

    @FXML
    public void openHelpView(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.F1)
            new HelpController().openHelp();
        if (event.getCode() == KeyCode.L && event.isControlDown()) {
            LoggedUser.logOut();
            this.loadView((Event) event, ".././login/login.fxml");
        }
        if (event.getCode() == KeyCode.W && event.isControlDown() && event.isAltDown()) {

            Stage currentStage = (Stage) MainPane.getScene().getWindow();
            currentStage.close();
        }
    }

    @FXML
    public void goToSales() throws Exception {
        if (currentAnchorPane == 7)
            return;
        loadAnchor(".././partials/Sales.fxml");
        currentAnchorPane = 7;
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

    public void loadAnchor(String path) {
        if (currentAnchorPane != 0) {
            removeAnchor();
        }
        AnchorPane root = null;
        try {

            root = FXMLLoader.load(getClass().getResource(path), Language.getBundle());
            MainPane.add((AnchorPane) root, 1, 1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        loadAnchor("../partials/FinalMenu.fxml");
        currentAnchorPane = 2;


    }

    @FXML
    public void goToWaiters(javafx.event.ActionEvent actionEvent) throws Exception {
        if (currentAnchorPane == 5)
            return;
        loadAnchor("../partials/Waiter.fxml");
        currentAnchorPane = 5;


    }


    public void loadViewData(String path, int id) throws IOException, SQLException {
        // TODO:HERE

        FXMLLoader loader = new FXMLLoader(getClass().getResource(path), Language.getBundle());
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




    public static int[] getGridLocation(double x, double y) {
        int gridLocation[] = new int[2];

        gridLocation[0] = (int) Math.floor((x / 120.4));
        gridLocation[1] = (int) Math.floor((y / 99.8));
        return gridLocation;

    }

    public void initializeOccupancy() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                occupiedTable[i][j] = false;
            }
        }
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    @FXML
    public void openContextMenu(ContextMenuEvent event) {

        int[] currentLocation = getGridLocation(event.getScreenX(), event.getScreenY());
        int[] cord = getGridLocation(event.getX(), event.getY());
        if (occupiedTable[cord[0]][cord[1]]) {
            update.setOnAction(e -> {
                try {
                    updateTable(cord);
                } catch (Exception exception) {
                    this.show(exception);
                }
            });
            delete.setOnAction(e -> {
                try {
                    deleteTable(cord);
                    numberOfTabels--;
                    updateStatus();
                } catch (Exception exception) {
                    this.show(exception);
                }
            });


            tableFullContext.show(tablesGrid, event.getScreenX(), event.getScreenY());


            tableContext.hide();
        } else {
            add.setOnAction(e -> {
                try {
                    addNewTable(cord);
                    updateStatus();
                } catch (Exception exception) {
                    this.show(exception);
                }
            });
            tableContext.show(tablesGrid, event.getScreenX(), event.getScreenY());

            tableFullContext.hide();
        }

    }

    @FXML
    public void updateValues(MouseEvent event) {
//        try {
////            updateStatus();
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
    }

    public void addNewTable(int[] gridLocation) throws Exception {

        ImageView table = new ImageView(getClass().getResource(".././Images/tableforview.png").toExternalForm());
        table.setFitHeight(100);
        table.setFitWidth(100);
        Label text = new Label("Tavolina " + (numberOfTabels + 1));
        text.setPadding(new Insets(70, 0, 0, 20));
        numberOfTabels++;
        tablesGrid.add(table, gridLocation[0], gridLocation[1]);

        tablesGrid.add(text, gridLocation[0], gridLocation[1]);
        occupiedTable[gridLocation[0]][gridLocation[1]] = true;

        this.Tavolinat.put(convertFromGrid(gridLocation), new Tables(convertFromGrid(gridLocation)));
        TableModel.insertTable(gridLocation[0], gridLocation[1]);

    }

    public int convertFromGrid(int[] gridLocation) {
        return gridLocation[0] * 5 + gridLocation[1];
    }


    public void updateTable(int[] gridLocation) throws IOException, SQLException {
        if (this.Tavolinat.containsKey(convertFromGrid(gridLocation)))
            loadViewData(".././partials/SpecificTable.fxml", convertFromGrid(gridLocation));
    }

    public void deleteTable(int[] coordinates) throws SQLException {
        // hashmap remove
        this.Tavolinat.remove(convertFromGrid(coordinates));
        occupiedTable[coordinates[0]][coordinates[1]] = false;
        // komanda e pare e hjek imageview kurse e dyta e hjek label
        tablesGrid.getChildren().remove(getNodeByRowColumnIndex(coordinates[1], coordinates[0], tablesGrid));
        tablesGrid.getChildren().remove(getNodeByRowColumnIndex(coordinates[1], coordinates[0], tablesGrid));

        // db remove
        TableModel.removeTable(coordinates[0], coordinates[1]);
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