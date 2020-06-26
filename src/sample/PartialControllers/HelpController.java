package sample.PartialControllers;

import Helpers.Language;
import StateClasses.BigController;
import com.sun.source.tree.Tree;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HelpController implements Initializable, BigController {
    @FXML
    private TreeView<String> helpTree;
    @FXML
    private AnchorPane textPane;
    @FXML
    private Text helpText;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu reportMenu;
    @FXML
    private MenuItem closeItem;
    @FXML
    private MenuItem websiteItem;
    @FXML
    private MenuItem emailItem;

    private final static String OS = System.getProperty("os.name").toLowerCase();


    public void openHelp() throws IOException {
        Parent nodeRoot = FXMLLoader.load(getClass().getResource("../partials/Help.fxml"), Language.getBundle());
        Scene helpScene = new Scene(nodeRoot);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.show();

    }

    public TreeItem<String> createTree(String main, String first, String second, String third) {
        TreeItem<String> tableItem = new TreeItem<>(main);

        tableItem.getChildren().addAll(new TreeItem<>(first), new TreeItem<>(second), new TreeItem<>(third));
        return tableItem;
    }


    public void fillText() {
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            handleMouseClicked(event);

        };

        helpTree.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

    }

    public void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            String name = (String) ((TreeItem) helpTree.getSelectionModel().getSelectedItem()).getValue();
            helpText.setText(readColumn("src/help/" + name + ".txt"));
        }
    }

    public String readColumn(String path) {
        File file = new File(path);
        Scanner fileContent = null;
        try {
            StringBuilder fileText = new StringBuilder();
            fileContent = new Scanner(file);
            while (fileContent.hasNext()) {
                fileText.append(fileContent.nextLine());
            }
            return fileText.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (fileContent != null)
                fileContent.close();
        }
        return null;
    }

    public void initializeTree() {

        TreeItem<String> mainItem = new TreeItem<>("Help");

        TreeItem<String>tableItem;
        TreeItem<String>waiterItem;
        TreeItem<String>menuItem;



        if(Language.returnLanguage().equalsIgnoreCase("albanian")) {
             tableItem = createTree("Tavolinat", "Shtimi i tavolines", "Perditesimi i tavolines", "Fshirja e tavolines");
            waiterItem = createTree("Kamarieret", "Shtimi i kamariereve", "Perditsimi i Kamariereve", "Perjashtimi i kamarierve");
            menuItem = createTree("Menu", "Shtimi i produktit", "Perditesimi i produktit", "Fshirja e produktit");
        }
        else
        {
            tableItem = createTree("Tables", "Adding a table", "Updating a table", "Delte a table");
           waiterItem = createTree("Waiters", "Adding Waiters", "Updating Waiters", "Removing Waiters");
            menuItem = createTree("Menu", "Adding Product", "Updating Products", "Deleting a Product");
        }
        tableItem.setExpanded(true);
        waiterItem.setExpanded(true);
        menuItem.setExpanded(true);
        mainItem.getChildren().addAll(tableItem, waiterItem, menuItem);
        mainItem.setExpanded(true);
        helpTree.setRoot(mainItem);
        fillText();


    }

    public static boolean isWindows() {

        return OS.contains("win");
    }

    public static boolean isMac() {
        return OS.contains("mac");
    }

    public static boolean isUnix() {
        return OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;
    }


    public static void openURL(String url) {

        Runtime rt = Runtime.getRuntime();
        try {
            if (isWindows()) {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url).waitFor();

            } else if (isMac()) {
                String[] cmd = {"open", url};
                rt.exec(cmd).waitFor();

            } else if (isUnix()) {
                String[] cmd = {"xdg-open", url};
                rt.exec(cmd).waitFor();

            } else {
                try {
                    throw new IllegalStateException();
                } catch (IllegalStateException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeItem.setOnAction(e -> {
            Stage currentStage = (Stage) helpTree.getScene().getWindow();
            currentStage.close();

        });
        websiteItem.setOnAction(e ->
        {
            openURL("https://avdylkrasniqi.github.io/knkhtmlcontact/index.html");
        });

        emailItem.setOnAction(e ->
        {
            openURL("https://avdylkrasniqi.github.io/knkhtmlcontact/viaemail.html");
        });


        initializeTree();
    }
}
