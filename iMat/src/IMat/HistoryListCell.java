package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

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

    private FXMLLoader loader;

    private static final String DATE_FORMAT = "dd/MM - yyyy";
    private static final int MAX_PREVIEW_ITEMS = 3;

    @Override
    public void updateItem(Order order, boolean empty) {
        super.updateItem(order, empty);
        if (empty || order == null) {
            clearContent();
        } else {
            if (loader == null) {
                loader = new FXMLLoader(getClass().getResource("/historyListCell.fxml"));
                loader.setController(this);
                try {
                    loader.load();
                } catch (IOException e) { e.printStackTrace(); }
            }
            setContent(order);
        }
    }

    private void clearContent() {
        setGraphic(null);
    }

    private void setContent(Order order) {

        dateLabel.setText(new SimpleDateFormat(DATE_FORMAT).format(order.getDate()));
        itemListLabel.setText(getListOfItems(order));
        priceLabel.setText(IMatController.getTotalPrice(order) + " :-");

        setGraphic(gridPane);
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
