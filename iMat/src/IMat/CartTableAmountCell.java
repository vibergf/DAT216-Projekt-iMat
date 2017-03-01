package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
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
    @FXML
    private Button incButton;
    @FXML
    private Button decButton;

    private FXMLLoader loader;

    private int i = 1;

    private static final int THUMBNAIL_SIZE = 50;

    public CartTableAmountCell(){
        loader = new FXMLLoader(getClass().getResource("/cartTableAmountCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) { e.printStackTrace(); }

    }

    @Override
    public void updateItem(ShoppingItem item, boolean empty){
//        super.updateItem(item, empty);

        if (!(empty || item == null)) {
            setContent(item);
            setGraphic(hBox);
        }else{
            setGraphic(null);
        }
    }


    private void setContent(ShoppingItem item) {

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


