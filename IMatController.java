package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.ShoppingItem;

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
    Image homeImage = new Image( "/resources/Home-icon.png");
    @FXML
    private Button listButton;
    Image listImage = new Image( "/resources/Food-List-Ingredients-icon.png");
    @FXML
    private Button historyButton;
    Image histImage = new Image( "/resources/Clock-icon.png");
    @FXML
    private Button cartButton;
    Image cartImage = new Image( "/resources/shop-cart-icon.png");
    @FXML
    private Button checkoutButton;
    Image checkoutImage = new Image( "/resources/shopping-bag-icon.png");

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
        homeButton.setGraphic(new ImageView(homeImage));
        listButton.setGraphic(new ImageView(listImage));
        historyButton.setGraphic(new ImageView(histImage));
        cartButton.setGraphic(new ImageView(cartImage));
        checkoutButton.setGraphic(new ImageView(checkoutImage));


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
