package IMat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

/**
 * Created by Vibergf on 27/02/2017.
 */
public class HomeGridCell extends GridPane {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    @FXML
    private ImageView productImage;
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label confirmLabel;
    @FXML
    private Spinner<Double> amountSpinner;
    @FXML
    private Button addButton;

    private ShoppingItem item;

    private static final double SPINNER_DOUBLE_INCREMENT = 0.1;
    private static final int SPINNER_INT_INCREMENT = 1;
    private static final int SPINNER_MAX = 99;

    private FXMLLoader loader;

    public HomeGridCell(Product product) {
        loader = new FXMLLoader(getClass().getResource("/homeGridCell.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        productImage.setImage(dataHandler.getFXImage(product, 140, 140));
        nameLabel.setText(product.getName());
        priceLabel.setText(IMatController.formatPriceWithUnit(product.getPrice(), product.getUnit()));

        String unit = product.getUnit().toLowerCase();
        if(unit.equals("kr/kg")){
            this.item = new ShoppingItem(product, 0.1);
            amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                    SPINNER_DOUBLE_INCREMENT, SPINNER_MAX, SPINNER_DOUBLE_INCREMENT, SPINNER_DOUBLE_INCREMENT));
            amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                item.setAmount((double) newValue);
            });
            amountSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if(newValue.equals(oldValue))
                    return;
//                newValue = newValue.replaceAll("\\D", "");
                if(newValue.length() != 0) {
                    addButton.setDisable(false);
//                    if (Double.parseDouble(newValue) > SPINNER_MAX)
//                        newValue = oldValue;
//                    if (Double.parseDouble(newValue) <= 0)
//                        newValue = "0.1";
//                    Platform.runLater(() -> {
//                        amountSpinner.getValueFactory().setValue(Double.parseDouble(newValue));
//                    });
                }else{
                    addButton.setDisable(true);
                }
//                amountSpinner.getEditor().setText(newValue);
            });
        }else {
            this.item = new ShoppingItem(product, 1);
            amountSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(
                    SPINNER_INT_INCREMENT, SPINNER_MAX, SPINNER_INT_INCREMENT, SPINNER_INT_INCREMENT));
            amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                item.setAmount(newValue);
            });
            amountSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if(newValue.equals(oldValue))
                    return;
                newValue = newValue.replaceAll("\\D", "");
                if(newValue.length() != 0) {
                    addButton.setDisable(false);
                    if (Integer.parseInt(newValue) > SPINNER_MAX)
                        newValue = oldValue;
                    if (Integer.parseInt(newValue) <= 0)
                        newValue = "1";
                    amountSpinner.getValueFactory().setValue(Double.parseDouble(newValue));
                }else{
                    addButton.setDisable(true);
                }
                amountSpinner.getEditor().setText(newValue);
            });
        }

        dataHandler.getShoppingCart().addShoppingCartListener(c -> {
            refreshLabel();
        });

        refreshLabel();
    }

//    @FXML
//    void upPressed() {
//        setItemAmount(item.getAmount()+1);
//    }
//
//    @FXML
//    void downPressed() {
//        if(item.getAmount() > 1){
//            setItemAmount((item.getAmount()-1));
//        }
//    }

    private void refreshLabel() {
        List<ShoppingItem> items = dataHandler.getShoppingCart().getItems();
        boolean found = false;
        for(int n=0 ; n < items.size() ; n++) {
            if(items.get(n).getProduct().equals(item.getProduct())) {
                double amountInCart = items.get(n).getAmount();
                confirmLabel.setText("Du har " + IMatController.formatAmount(amountInCart) + " " + item.getProduct().getUnitSuffix() + " " +
                        item.getProduct().getName() + " i kundvagnen.");
                found = true;
            }
        }
        if(!found)
            confirmLabel.setText("");
    }

    @FXML
    void addButtonPressed() {
        IMatController.getInstance().addItem(item);
        amountSpinner.getValueFactory().setValue(0.0);
    }


}
