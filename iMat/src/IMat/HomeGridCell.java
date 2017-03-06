package IMat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import se.chalmers.ait.dat215.project.ShoppingItem;

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
    private ShoppingItem item = new ShoppingItem(product,1);
    private double antalIVagn = 0;
    private double i=1;

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
        name.setFont(Font.font(20));
        units.setText(item.getAmount()+"");
        //units.setText(i+"");
        units.setFont(Font.font(20));
        priceLabel.setText(product.getPrice()+" "+product.getUnit());
    }

    @FXML
    void upPressed() {
        i = i+1;
        units.setText(i+"");
       /*  item.setAmount(item.getAmount()+1);
        units.setText(item.getAmount()+"");*/
    }

    @FXML
    void downPressed() {
        if(i > 1){
            i=(i-1);
            units.setText(i+"");
        }/*
        if(item.getAmount()> 1) {
            item.setAmount(item.getAmount() - 1);
            units.setText(item.getAmount() + "");
        }*/
    }

    @FXML
    void add() throws InterruptedException {
        ShoppingItem item = new ShoppingItem(product,i);
        IMatController.getInstance().addItem(item);
        confirmLabel.setTextFill(Color.GREEN);
        confirmLabel.setText("Du har totalt "+ (antalIVagn+item.getAmount())+product.getUnitSuffix()+" "+ product.getName() + " i kundvagnen");
        antalIVagn = antalIVagn+item.getAmount();
        //item.setAmount(1.0);
        i=1.0;
        units.setText(i+"");
       // units.setText(item.getAmount()+"");
    }


}
