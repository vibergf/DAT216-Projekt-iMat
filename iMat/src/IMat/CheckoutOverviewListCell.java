package IMat;

/**
 * Created by Vibergf on 03/03/2017.
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

import static IMat.IMatController.formatDate;
import static IMat.IMatController.formatPrice;
import static IMat.IMatController.getTotalPrice;

public class CheckoutOverviewListCell extends ListCell<ShoppingItem> {

    @FXML protected Label nameLabel;
    @FXML protected Label amountLabel;
    @FXML protected Label totalLabel;
    @FXML protected AnchorPane mainPane;

    private FXMLLoader loader;

    public CheckoutOverviewListCell(){

        loader = new FXMLLoader(getClass().getResource("/checkoutOverviewListCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) { e.printStackTrace(); }

    }

    @Override
    public void updateItem(ShoppingItem item, boolean empty) {
        super.updateItem(item, empty);

        if (!(empty || item == null)) {
            setContent(item);
            setGraphic(mainPane);
        }
    }

    private void setContent(ShoppingItem item) {
        nameLabel.setText(item.getProduct().getName());
        amountLabel.setText((int)item.getAmount() + " x " + formatPrice(item.getProduct().getPrice()));
        totalLabel.setText(formatPrice(item.getTotal()));
    }
}

