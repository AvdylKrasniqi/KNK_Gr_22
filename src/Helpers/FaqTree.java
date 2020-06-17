package Helpers;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class FaqTree {
    private  static TreeView<String> helpTree= new TreeView<>();

    public static TreeView getTree()
    {
        TreeItem<String> randomItem=new TreeItem<>("waiters");
        randomItem.getChildren().add(new TreeItem<String>("tavolinat"));
        randomItem.getChildren().add(new TreeItem<String>("produktet"));
        helpTree.setRoot(randomItem);
        return helpTree;

    }


}
