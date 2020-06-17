package sample.PartialControllers;

import Helpers.FaqTree;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelpController implements Initializable {
   private TreeView<String> helpViewTree;

   public  void openHelp() throws IOException {
       Parent nodeRoot = FXMLLoader.load(getClass().getResource("./partials/Help.fxml"));
       Scene helpScene = new Scene(nodeRoot);
       Stage helpStage = new Stage();
       helpStage.setScene(helpScene);
       helpStage.show();

   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
helpViewTree= FaqTree.getTree();
    }
}
