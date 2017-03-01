package IMat;

import com.sun.istack.internal.Nullable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.Duration;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static IMat.IMatController.formatDate;
import static IMat.IMatController.formatPrice;
import static IMat.IMatController.getTotalPrice;

/**
 *  Lists all available Shopping Lists.
 *
 * Created by Erik on 2017-02-22.
 */
public class ShoppingListController implements Initializable {

    private @FXML
    ListView<ShoppingList> shoppingList;
    private ObservableList<ShoppingList> items;


    @FXML private BorderPane shoppingListView;
    @FXML private BorderPane listDetailsView;
    @FXML private Label detailedViewSumLabel;

    @FXML private Button btnBackToList;
    @FXML private Label labelTopTitle;

    @FXML private TableView<ShoppingItem> detailedViewTable;
    @FXML private TableColumn<ShoppingItem, ShoppingItem> detailedViewNameColumn;
    @FXML private TableColumn<ShoppingItem, String> detailedViewAmountColumn;
    @FXML private TableColumn<ShoppingItem, String> detailedViewPriceColumn;

    @Nullable private ShoppingList selectedList = null;

    private void switchToList() {
        shoppingListView.setVisible(true);
        listDetailsView.setVisible(false);
    }

    private void switchToDetails(ShoppingList list) {
        shoppingListView.setVisible(false);
        listDetailsView.setVisible(true);
        labelTopTitle.setText(list.getName());
        selectedList = list;

        detailedViewSumLabel.setText("Summa: " + list.getPriceString());

        detailedViewNameColumn.setCellFactory(new Callback<TableColumn<ShoppingItem, ShoppingItem>, TableCell<ShoppingItem, ShoppingItem>>() {
            @Override
            public TableCell<ShoppingItem, ShoppingItem> call(TableColumn<ShoppingItem, ShoppingItem> param) {
                TableCell<ShoppingItem, ShoppingItem> cell = new HistoryTableCell();
                return cell;
            }
        });
        detailedViewNameColumn.setCellValueFactory(c-> new SimpleObjectProperty<ShoppingItem>(c.getValue()));
        detailedViewAmountColumn.setCellValueFactory(c-> new SimpleStringProperty((int)c.getValue().getAmount() + ""));
        detailedViewPriceColumn.setCellValueFactory(c-> new SimpleStringProperty(formatPrice(c.getValue().getTotal())));

        detailedViewTable.setItems(FXCollections.observableList(list.getItems()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = FXCollections.observableArrayList (ShoppingList.getAllShoppingLists());
        shoppingList.setItems(items);
        shoppingList.setCellFactory(listView -> new ListViewCell());
        shoppingList.setSelectionModel(new DisabledSelectionModel<ShoppingList>());
        btnBackToList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switchToList();
            }
        });
        detailedViewTable.setSelectionModel(null);
    }

    public class ListViewCell extends ListCell<ShoppingList>
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

    private class Data
    {
        @FXML
        private AnchorPane hBox;
        @FXML
        private Label labelTitle;
        @FXML
        private Label labelSummary;
        @FXML
        private Label labelDetails;

        @FXML
        private Label labelPrice;

        @FXML private Button btnDelete;

        @FXML private Button btnDetails;
        @FXML private Button btnAddCart;

        Data() {
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
            labelDetails.setText(list.getDescription());
            labelSummary.setText(list.getShortIngredients());
            labelPrice.setText(list.getPriceString());
            btnDelete.setOnAction(e -> {
                ;Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Bekräfta");
                alert.setHeaderText("Är du säker att du vill ta bort listan?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    ObservableList<ShoppingList> items = listView.getItems();
                    items.remove(list);
                    listView.refresh();
                } else {
                    alert.close();
                }
            });
            btnDetails.setOnAction(e -> switchToDetails(list));
            btnAddCart.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    for (ShoppingItem shoppingItem : list.getItems()) {
                        IMatDataHandler.getInstance().getShoppingCart().addItem(shoppingItem);
                    }
                    btnAddCart.setText("Listan är Tillagd");
                    btnAddCart.setDefaultButton(true);
                    btnAddCart.setDisable(true);
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.millis(3500),
                            event -> {
                                btnAddCart.setText("Lägg till i Kundvagn");
                                btnAddCart.setDefaultButton(false);
                                btnAddCart.setDisable(false);
                            }));
                    timeline.play();
                }
            });
        }

        AnchorPane getBox()
        {
            return hBox;
        }
    }

}
