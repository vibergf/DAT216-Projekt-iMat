package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;

/**
 * Created by Vibergf on 27/02/2017.
 */
public class HomeGridCell extends AnchorPane {

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    @FXML
    private ImageView picture;
    @FXML
    private Label units;
    @FXML
    private Label name;
    @FXML
    private Label confirmLabel;
    @FXML
    private Button incButton;
    @FXML
    private Button decButton;
    @FXML
    private Button addButton;
    @FXML
    private Label priceLabel;

    private FXMLLoader loader;
    private Product product;
    private int i = 1;

    public HomeGridCell(Product product) {
        loader = new FXMLLoader(getClass().getResource("/homeGridCell.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.product = product;
        Image i = dataHandler.getFXImage(product);
        picture.setImage(i);
        name.setText(product.getName());
        name.setFont(Font.font(16));
        units.setText("1");
        units.setFont(Font.font(16));
        priceLabel.setText(product.getPrice()+" "+product.getUnit());
    }

    @FXML
    void upPressed() {
        i = Integer.parseInt(units.getText());
        i++;
        units.setText(i + "");
    }

    @FXML
    void downPressed() {
        i = Integer.parseInt(units.getText());
        if (i > 1) {
            i--;
            units.setText(i + "");
        }
    }

    @FXML
    void add() throws InterruptedException {
        System.out.println(i + " "+ product.getUnitSuffix()+ " " + product.getName() + " lades till i kundvagnen.");
        confirmLabel.setTextFill(Color.GREEN);
        confirmLabel.setText(i + " "+ product.getUnitSuffix()+ " " + product.getName() + " lades till i kundvagnen.");
        units.setText("1");
        i=1;
    }
}
