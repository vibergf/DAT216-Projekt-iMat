package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    @FXML
    private StackPane mainStackPane;

    @FXML
    private Button homeButton;
    @FXML
    private Button listButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button checkoutButton;

    private Parent homeView;
    private Parent listView;
    private Parent historyView;
    private Parent cartView;
    private Parent checkoutView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            homeView = FXMLLoader.load(getClass().getResource("/homeView.fxml"));
            listView = FXMLLoader.load(getClass().getResource("/listView.fxml"));
            historyView = FXMLLoader.load(getClass().getResource("/historyView.fxml"));
            cartView = FXMLLoader.load(getClass().getResource("/cartView.fxml"));
            checkoutView = FXMLLoader.load(getClass().getResource("/checkoutView.fxml"));
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        homeButtonPressed();
    }

    private void switchView(Node newView, Button newButton){
        mainStackPane.getChildren().clear();
        //TODO Clear toolbar selection
        mainStackPane.getChildren().add(newView);
        //TODO Select newButton in toolbar
    }

    @FXML
    private void homeButtonPressed(){
        switchView(homeView, homeButton);
    }

    @FXML
    private void listButtonPressed(){
        switchView(listView, listButton);
    }

    @FXML
    private void historyButtonPressed(){
        switchView(historyView, historyButton);
    }

    @FXML
    private void cartButtonPressed(){
        switchView(cartView, cartButton);
    }

    @FXML
    private void checkoutButtonPressed(){
        switchView(checkoutView, checkoutButton);
    }
}
