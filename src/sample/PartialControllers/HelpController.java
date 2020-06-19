package sample.PartialControllers;

import com.sun.source.tree.Tree;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
    @FXML
    private TreeView<String> helpTree;

    public void openHelp() throws IOException {
        Parent nodeRoot = FXMLLoader.load(getClass().getResource("../partials/Help.fxml"));
        Scene helpScene = new Scene(nodeRoot);
        Stage helpStage = new Stage();
        helpStage.setScene(helpScene);
        helpStage.show();

    }



    public void  initializeTree() {
        /*
        help
            tavolinat
            kamarieret
            menu
            sales
         */

        TreeItem<String> mainItem = new TreeItem<>("Help");
        TreeItem<String> tableItem=new TreeItem<>("Tavolinat");
        tableItem.getChildren().add(new TreeItem<String>("Shtimi i tavolines"));
        tableItem.getChildren().add(new TreeItem<String>("Perditesimi i tavolines"));
        tableItem.getChildren().add(new TreeItem<String>("Fshirja e tavolines"));
        TreeItem<String> waiterItem=new TreeItem<>("Kamarieret");
        waiterItem.getChildren().add(new TreeItem<>("Shtimi i kamariereve"));
        waiterItem.getChildren().add(new TreeItem<>("Perditismi i kamariereve"));
        waiterItem.getChildren().add(new TreeItem<>("Perjashtimi i kamariereve"));
        TreeItem<String> menuItem=new TreeItem<>("Menu");
        menuItem.getChildren().add(new TreeItem<>("Shtimi i produktit"));
        menuItem.getChildren().add(new TreeItem<>("Perditesimi i produktit"));
        menuItem.getChildren().add(new TreeItem<>("Fshirja e produktit"));




        tableItem.setExpanded(true);
        waiterItem.setExpanded(true);
        menuItem.setExpanded(true);
        mainItem.getChildren().addAll(tableItem,waiterItem,menuItem);
        mainItem.setExpanded(true);
        helpTree.setRoot(mainItem);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    initializeTree();
    }
}
