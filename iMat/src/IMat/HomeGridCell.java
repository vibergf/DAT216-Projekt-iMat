package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;

/**
 * Created by Vibergf on 27/02/2017.
 */
public class HomeGridCell extends AnchorPane{

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    @FXML
    private ImageView picture;
    @FXML
    private Label units;
    @FXML
    private Label name;
    @FXML
    private Button addButton;
    @FXML
    private Button decButton;

    private FXMLLoader loader;

    private Product product;

    public HomeGridCell(Product product){
        loader = new FXMLLoader(getClass().getResource("/homeGridCell.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
             loader.load();
        } catch (IOException e) { e.printStackTrace(); }
        this.product = product;
        Image i=dataHandler.getFXImage(product);
        picture.setImage(i);
        name.setText(product.getName());
        name.setFont(Font.font(16));
        units.setText("1");
    }
    @FXML void upPressed(){
        int i = Integer.parseInt(units.getText());
        i++;
        units.setText(i+"");
    }
    @FXML void downPressed(){
        int i = Integer.parseInt(units.getText());
        if(i>0) {
            i--;
            units.setText(i + "");
        }
    }
}
