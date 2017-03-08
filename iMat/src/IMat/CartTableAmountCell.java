package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by Emil on 2017-03-01.
 */
public class CartTableAmountCell extends TableCell<ShoppingItem, ShoppingItem> {


    @FXML protected HBox hBox;
    @FXML
    private Label units;
    @FXML protected Button incAmountButton;
    @FXML protected Button decAmountButton;

    @FXML protected ImageView incImageView;
    @FXML protected ImageView decImageView;

    @FXML protected TextField unitsField;

    private FXMLLoader loader;

    private int i = 1;


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
            setContent();
            setGraphic(hBox);
        }else{
            setGraphic(null);
        }
    }



    private void setContent() {
        Image incImage = new Image("/resources/Arrow-Down-icon.png");
        Image decImage = new Image("/resources/Arrow-Up-icon.png");
        incImageView.setImage(incImage);
        decImageView.setImage(decImage);

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
}




