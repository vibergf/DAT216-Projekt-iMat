package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    private static IMatController instance;

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

    private Button selectedButton;

    private Parent homeView;
    private Parent listView;
    private Parent historyView;
    private Parent cartView;
    private Parent checkoutView;

    private static final String DATE_FORMAT = "dd/MM - yyyy";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
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

    public static IMatController getInstance(){
        return instance;
    }

    private void switchView(Node newView, Button newButton){
        mainStackPane.getChildren().clear();
        mainStackPane.getChildren().add(newView);

        if(selectedButton != null)
            selectedButton.getStyleClass().remove("buttonHighlight");
        newButton.getStyleClass().add("buttonHighlight");
        selectedButton = newButton;
    }

    public static int getTotalPrice(Order order){
        List<ShoppingItem> itemList = order.getItems();
        int total = 0;
        for(ShoppingItem s : itemList)
            total += s.getTotal();
        return total;
    }

    public static String formatPrice(double price){
        return (price + " kr").replaceAll("\\.", ",");
        //return price + " :-";
    }

    public static String formatDate(Date date){
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    @FXML
    public void homeButtonPressed(){
        switchView(homeView, homeButton);
    }

    @FXML
    public void listButtonPressed(){
        switchView(listView, listButton);
    }

    @FXML
    public void historyButtonPressed(){
        switchView(historyView, historyButton);
    }

    @FXML
    public void cartButtonPressed(){
        switchView(cartView, cartButton);
    }

    @FXML
    public void checkoutButtonPressed(){
        switchView(checkoutView, checkoutButton);
    }
}
