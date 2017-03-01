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
public class CartTableRemoveCell extends TableCell<ShoppingItem, ShoppingItem> {

    @FXML protected ImageView removeImage;
    @FXML protected Button removeButton;


    @FXML protected HBox hBox;


    private FXMLLoader loader;

    private static final int THUMBNAIL_SIZE = 50;

    public CartTableRemoveCell(){
        loader = new FXMLLoader(getClass().getResource("/cartTableRemoveCell.fxml"));
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) { e.printStackTrace(); }

    }

    @Override
    public void updateItem(ShoppingItem item, boolean empty){
//        super.updateItem(item, empty);

        if (!(empty || item == null)) {
            setContent();
            setGraphic(hBox);
        }else{
            setGraphic(null);
        }
    }


    private void setContent() {
        Image removeIcon = new Image("/resources/remove-icon.png");
        removeImage.setImage(removeIcon);

        //TODO fix image size, button size and some fine adjusting
    }
}


