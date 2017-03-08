package IMat;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by Emil on 2017-03-01.
 */
public class CartTableAmountCell extends TableCell<ShoppingItem, ShoppingItem> {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    @FXML protected HBox hBox;

    @FXML private Spinner<Double> amountSpinner;

    private FXMLLoader loader;
    private static final double SPINNER_DOUBLE_INCREMENT = 0.1;
    private static final int SPINNER_INT_INCREMENT = 1;
    private static final int SPINNER_MAX = 99;

    private ShoppingItem item;



    public CartTableAmountCell(){
        loader = new FXMLLoader(getClass().getResource("/cartTableAmountCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) { e.printStackTrace(); }

//        amountSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
//            System.out.println(item.getProduct() + " " + oldValue + " " + newValue);
//            item.setAmount(newValue);
//            IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, false);
//        });
    }

    @Override
    public void updateItem(ShoppingItem item, boolean empty){

        if (!(empty || item == null)) {
            setContent(item);
            setGraphic(hBox);
        }else{
            setGraphic(null);
        }
    }



    private void setContent(ShoppingItem item) {
        this.item = item;
        String unit = item.getProduct().getUnit().toLowerCase();
           if (unit.equals("kr/kg")) {
               amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                       SPINNER_DOUBLE_INCREMENT, SPINNER_MAX, item.getAmount(), SPINNER_DOUBLE_INCREMENT));
           } else {
               amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                       SPINNER_INT_INCREMENT, SPINNER_MAX, item.getAmount(), SPINNER_INT_INCREMENT));
           }
       }
    }






