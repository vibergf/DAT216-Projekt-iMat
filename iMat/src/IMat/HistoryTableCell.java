package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;

/**
 * Created by vibergf on 26/02/2017.
 */
public class HistoryTableCell extends TableCell<ShoppingItem, ShoppingItem> {

    @FXML protected Label productLabel;
    @FXML protected ImageView productThumbnail;

    @FXML protected HBox hBox;

    private FXMLLoader loader;

    private static final int THUMBNAIL_SIZE = 50;

    public HistoryTableCell(){
        loader = new FXMLLoader(getClass().getResource("/historyTableCell.fxml"));
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
        productLabel.setText(item.getProduct().getName());
        productThumbnail.setImage(IMatDataHandler.getInstance().getFXImage(item.getProduct(), THUMBNAIL_SIZE, THUMBNAIL_SIZE));
    }
}
