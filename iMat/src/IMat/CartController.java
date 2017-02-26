package IMat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.awt.*;
import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Emil on 2017-02-22.
 */
public class CartController implements Initializable {

    @FXML
    private javafx.scene.control.Label cartLabel;
    @FXML
    private javafx.scene.control.Label sumLabel;
    @FXML
    private ListView cartList;
    @FXML
    private Button checkoutButton;
    @FXML
    private Button clearCartButton;
    @FXML
    private Button saveListButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //TODO add functionality and cells to listview
}
