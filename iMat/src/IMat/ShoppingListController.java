package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Erik on 2017-02-22.
 */
public class ShoppingListController implements Initializable {


    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    private @FXML
    ListView<ShoppingList> shoppingList;
    private ObservableList<ShoppingList> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = FXCollections.observableArrayList (ShoppingList.getSampleShoppingLists());
        shoppingList.setItems(items);
        shoppingList.setCellFactory(listView -> new ListViewCell());
    }

    public static class ListViewCell extends ListCell<ShoppingList>
    {
        @Override
        public void updateItem(ShoppingList shoppingList, boolean empty)
        {
            super.updateItem(shoppingList,empty);
            if(shoppingList != null) {
                Data data = new Data();
                data.setInfo(shoppingList, getListView());
                setGraphic(data.getBox());
            } else {
                setGraphic(null);
            }
        }
    }

    public static class Data
    {
        @FXML
        private AnchorPane hBox;
        @FXML
        private Label labelTitle;
        @FXML
        private Label labelSummary;

        @FXML
        private Label labelPrice;

        @FXML private Button btnDelete;

        @FXML private Button btnDetails;

        public Data() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoppingListCell.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        public void setInfo(final ShoppingList list, ListView<ShoppingList> listView)
        {
            labelTitle.setText(list.getName());
            labelSummary.setText(list.getDescription());
            labelPrice.setText(list.getPriceString());
            btnDelete.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    // TODO: Delete
                    System.out.println("Delete");
                    ObservableList<ShoppingList> items = listView.getItems();
                    items.remove(list);
                    listView.refresh();
                }
            });
            btnDetails.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    // TODO: Browse
                    System.out.println("btnDetails");
                }
            });
        }

        public AnchorPane getBox()
        {
            return hBox;
        }
    }

}
