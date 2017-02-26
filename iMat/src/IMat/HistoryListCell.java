package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import static IMat.IMatController.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HistoryListCell extends ListCell<Order> {

    @FXML protected Label dateLabel;
    @FXML protected Label priceLabel;
    @FXML protected Label itemListLabel;
    @FXML protected GridPane gridPane;

    private HistoryController parent;
    private Order associatedOrder;

    private FXMLLoader loader;

    private static final int MAX_PREVIEW_ITEMS = 3;

    public HistoryListCell(HistoryController parent){
        this.parent = parent;

        loader = new FXMLLoader(getClass().getResource("/historyListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) { e.printStackTrace(); }

    }

    @FXML
    private void detailedViewButtonPressed(){
        parent.switchToDetailedView(associatedOrder);
    }

    @Override
    public void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);

        if (!(empty || order == null)) {
            setContent(order);
            setGraphic(gridPane);
            associatedOrder = order;
        }
    }

    private void setContent(Order order) {
        dateLabel.setText(formatDate(order.getDate()));
        itemListLabel.setText(getListOfItems(order));
        priceLabel.setText(formatPrice(getTotalPrice(order)));
    }

    private String getListOfItems(Order order){
        List<ShoppingItem> list = order.getItems();
        if(list.size() == 0)
            return "Empty";
        String listOfItems = list.get(0).getProduct().getName();
        int i = 1;
        for(; i < list.size() && i < MAX_PREVIEW_ITEMS ; i++){
            listOfItems = listOfItems.concat(", " + list.get(i).getProduct().getName());
        }
        if(i < list.size())
            listOfItems = listOfItems.concat("...");
        return listOfItems;
    }
}
