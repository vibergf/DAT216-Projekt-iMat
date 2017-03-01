package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by Emil on 2017-02-28.
 */
public class CartTableCell extends TableCell<ShoppingItem, ShoppingItem> {
    @FXML
    protected Label productLabel;
    @FXML protected ImageView productThumbnail;
    @FXML protected ImageView removeImage;

    @FXML protected HBox hBox;


    private FXMLLoader loader;

    private static final int THUMBNAIL_SIZE = 50;

    public CartTableCell(){
        loader = new FXMLLoader(getClass().getResource("/cartTableCell.fxml"));
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
        Image removeIcon = new Image("/resources/remove-icon.png");
        productLabel.setText(item.getProduct().getName());
        productThumbnail.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct(), THUMBNAIL_SIZE, THUMBNAIL_SIZE));
        removeImage.setImage(removeIcon);
    }
}
