package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML protected ListView historyListView;

    @FXML private BorderPane mainView;
    @FXML private BorderPane detailedView;

    @FXML private Label detailedViewDateLabel;
    @FXML private Label detailedViewSumLabel;

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

        detailedView.setVisible(false);
        mainView.setVisible(true);
    }

    @FXML private void detailedViewBackButtonPressed(){
        switchToMainView();
    }

    protected void switchToDetailedView(Order order){
        mainView.setVisible(false);

        detailedViewDateLabel.setText(IMatController.formatDate(order.getDate()));
        detailedViewSumLabel.setText("Summa: " + IMatController.getTotalPriceAsString(order));
        detailedView.setVisible(true);
    }

    protected void switchToMainView(){
        detailedView.setVisible(false);
        mainView.setVisible(true);
    }
}
