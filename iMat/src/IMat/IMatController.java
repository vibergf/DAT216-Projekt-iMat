package IMat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import se.chalmers.ait.dat215.project.*;

import java.net.URL;
import java.text.DecimalFormat;
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
//    @FXML
//    private Button checkoutButton;


    private Button selectedButton;

    private Parent homeView;
    private Parent listView;
    private Parent historyView;
    private Parent cartView;
    private Parent checkoutView;

    private CheckoutController checkoutController;
    private ShoppingListController listController;
    private HistoryController historyController;
    private CartController cartController;

    private static final String DATE_FORMAT = "dd/MM - yyyy";
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        FXMLLoader loader;
        try {
            loader = new FXMLLoader(getClass().getResource("/homeView.fxml"));
            homeView = loader.load();
            loader = new FXMLLoader(getClass().getResource("/shoppingListView.fxml"));
            listView = loader.load();
            listController = loader.getController();
            loader = new FXMLLoader(getClass().getResource("/historyView.fxml"));
            historyView = loader.load();
            historyController = loader.getController();
            loader = new FXMLLoader(getClass().getResource("/cartView.fxml"));
            cartView = loader.load();
            cartController = loader.getController();
            loader = new FXMLLoader(getClass().getResource("/checkoutView.fxml"));
            checkoutView = loader.load();
            checkoutController = loader.getController();
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(cartEvent -> {
            updateCartButtonTotal();
        });
        updateCartButtonTotal();
        homeButtonPressed();
    }

    private void updateCartButtonTotal(){
        int total = (int) IMatDataHandler.getInstance().getShoppingCart().getTotal();
        if (total == 0) {
            cartButton.setText("Kundvagn");
        } else {
            cartButton.setText("Kundvagn ("+ formatPrice(total) + ")");
        }
    }

    public static IMatController getInstance(){
        return instance;
    }

    public void addItem(ShoppingItem item){
        int index = -1;
        List<ShoppingItem> list = IMatDataHandler.getInstance().getShoppingCart().getItems();
        for (int i = 0 ; i < list.size() ; i++){
            if(list.get(i).getProduct().getName().equals(item.getProduct().getName()))
                index = i;
        }
        if(index != -1){
            ShoppingItem si = IMatDataHandler.getInstance().getShoppingCart().getItems().get(index);
            si.setAmount(si.getAmount() + item.getAmount());
            IMatDataHandler.getInstance().getShoppingCart().fireShoppingCartChanged(item, false);
        }else
            IMatDataHandler.getInstance().getShoppingCart().addItem(item);
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
        return (DECIMAL_FORMAT.format(price) + " kr").replaceAll("\\.", ",");
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
        listController.onEnter();
        switchView(listView, listButton);
    }

    @FXML
    public void historyButtonPressed(){
        historyController.onEnter();
        switchView(historyView, historyButton);
    }

    @FXML
    public void cartButtonPressed(){
        cartController.onEnter();
        switchView(cartView, cartButton);
    }

    @FXML
    public void checkoutButtonPressed(){
        checkoutController.onEnter();
        switchView(checkoutView, cartButton);
    }
}
