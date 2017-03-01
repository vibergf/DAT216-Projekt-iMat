package IMat;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import static IMat.IMatController.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML protected ListView historyListView;

    @FXML private BorderPane mainView;
    @FXML private BorderPane detailedView;

    @FXML private Label detailedViewDateLabel;
    @FXML private Label detailedViewSumLabel;

    @FXML private TableView<ShoppingItem> detailedViewTable;
    @FXML private TableColumn<ShoppingItem, ShoppingItem> detailedViewNameColumn;
    @FXML private TableColumn<ShoppingItem, String> detailedViewAmountColumn;
    @FXML private TableColumn<ShoppingItem, String> detailedViewPriceColumn;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(1));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(10));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(5));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(2));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(11));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(21));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(26));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(30));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(8));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(19));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(3));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(11));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(21));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(26));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(30));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(8));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(19));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(20));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(22));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(23));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(24));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(25));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(4));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(1));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(2));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(3));
        dataHandler.placeOrder();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(4));
        dataHandler.placeOrder();

        HistoryController handle = this;
        historyListView.setSelectionModel(new DisabledSelectionModel<Order>());
        historyListView.setItems(FXCollections.observableList(dataHandler.getOrders()));
        historyListView.setCellFactory(new Callback<ListView<Order>, ListCell<Order>>() {
            @Override
            public ListCell<Order> call(ListView<Order> param) {
                return new HistoryListCell(handle);
            }
        });

        detailedViewTable.setSelectionModel(null);

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

        detailedView.setVisible(false);
        mainView.setVisible(true);
    }

    @FXML private void detailedViewBackButtonPressed(){
        switchToMainView();
    }

    protected void switchToDetailedView(Order order){
        mainView.setVisible(false);

        detailedViewDateLabel.setText(formatDate(order.getDate()));
        detailedViewSumLabel.setText("Summa: " + formatPrice(getTotalPrice(order)));

        detailedViewTable.setItems(FXCollections.observableList(order.getItems()));

        detailedView.setVisible(true);
    }

    protected void switchToMainView(){
        detailedView.setVisible(false);
        mainView.setVisible(true);
    }
}
