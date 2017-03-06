package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
    private Spinner<Integer> amountSpinner;
    @FXML
    private Button addButton;

    private ShoppingItem item;

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

        this.item = new ShoppingItem(product, 1);
        item.setAmount(1);
        productImage.setImage(dataHandler.getFXImage(product, 140, 140));
        nameLabel.setText(product.getName());
        priceLabel.setText(IMatController.formatPriceWithUnit(product.getPrice(), product.getUnit()));

        amountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99, 1, 1));
        amountSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            item.setAmount(newValue);
            System.out.println(newValue);
        });

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
        amountSpinner.getValueFactory().setValue(1);
    }


}
