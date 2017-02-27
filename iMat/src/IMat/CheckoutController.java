package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    @FXML private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Product> list = IMatDataHandler.getInstance().getProducts();
        List<Node> nodeList = new ArrayList<Node>();
        for(Product p : list){
            nodeList.add(new HomeGridCell(p));
        }
        flowPane.getChildren().addAll(nodeList);
    }
}
