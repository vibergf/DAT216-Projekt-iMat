package IMat;

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

            Product product = item.getProduct();
           String unit = product.getUnit().toLowerCase();
           if (unit.equals("kr/kg")) {
               this.item = new ShoppingItem(product, 0.1);
               amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                       SPINNER_DOUBLE_INCREMENT, SPINNER_MAX, SPINNER_DOUBLE_INCREMENT, SPINNER_DOUBLE_INCREMENT));
               amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                   item.setAmount((double) newValue);
               });
               amountSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                   if (newValue.equals(oldValue))
                       return;
                   if (newValue.length() != 0) {
//                    if (Double.parseDouble(newValue) > SPINNER_MAX)
//                        newValue = oldValue;
//                    if (Double.parseDouble(newValue) <= 0)
//                        newValue = "0.1";
//                    Platform.runLater(() -> {
//                        amountSpinner.getValueFactory().setValue(Double.parseDouble(newValue));
//                    });
                   }
//                amountSpinner.getEditor().setText(newValue);
               });
           } else {
               this.item = new ShoppingItem(product, 1);
               amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                       SPINNER_INT_INCREMENT, SPINNER_MAX, SPINNER_INT_INCREMENT, SPINNER_INT_INCREMENT));
               amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                   item.setAmount(newValue);
               });
               amountSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                   if (newValue.equals(oldValue))
                       return;
                   newValue = newValue.replaceAll("\\D", "");
                   if (newValue.length() != 0) {
                       if (Integer.parseInt(newValue) > SPINNER_MAX)
                           newValue = oldValue;
                       if (Integer.parseInt(newValue) <= 0)
                           newValue = "1";
                       amountSpinner.getValueFactory().setValue(Double.parseDouble(newValue));
                   }

                   amountSpinner.getEditor().setText(newValue);
               });
           }
       }
    }






