package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Vibergf on 27/02/2017.
 */
public class HomeGridCell extends AnchorPane{

    @FXML Button button;

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
        button.setText(product.getName());
    }
}
