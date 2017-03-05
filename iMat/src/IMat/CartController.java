package IMat;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.*;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;
import se.chalmers.ait.dat215.project.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static IMat.IMatController.formatPrice;

/**
 * Created by Emil on 2017-02-22.
 */
public class CartController implements Initializable {

    //TODO add listener for shopping cart for better code

    //TODO add functionality for removing a single object in the cart

    @FXML
    private javafx.scene.control.Label cartLabel;
    @FXML
    private javafx.scene.control.Label sumLabel;

    @FXML
    private TableView<ShoppingItem> cartItems;

    @FXML
    private TableColumn<ShoppingItem, ShoppingItem> nameColumn;
    @FXML
    private TableColumn<ShoppingItem, String> amountColumn;
    @FXML
    private TableColumn<ShoppingItem, String> priceColumn;
    @FXML
    private TableColumn<ShoppingItem, ShoppingItem> removeColumn;


    @FXML
    private Button checkoutButton;
    @FXML
    private Button clearCartButton;
    @FXML
    private Button saveListButton;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       // dataHandler.getShoppingCart();
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(2));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(11));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(21));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(26));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(30));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(8));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(19));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(20));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(22));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(23));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(24));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(25));
        dataHandler.getShoppingCart().addProduct(dataHandler.getProduct(27));

        nameColumn.setCellFactory(new Callback<TableColumn<ShoppingItem, ShoppingItem>, TableCell<ShoppingItem, ShoppingItem>>() {
            @Override
            public TableCell<ShoppingItem, ShoppingItem> call(TableColumn<ShoppingItem, ShoppingItem> param) {
                TableCell<ShoppingItem, ShoppingItem> cell = new CartTableCell();
                return cell;
            }
        });
        removeColumn.setCellFactory(new Callback<TableColumn<ShoppingItem, ShoppingItem>, TableCell<ShoppingItem, ShoppingItem>>() {
            @Override
            public TableCell<ShoppingItem, ShoppingItem> call(TableColumn<ShoppingItem, ShoppingItem> param) {
                TableCell<ShoppingItem, ShoppingItem> cell = new CartTableRemoveCell();
                return cell;
                //TODO remove unnecessary types
            }
        });


        nameColumn.setCellValueFactory(c-> new SimpleObjectProperty<ShoppingItem>(c.getValue()));
        amountColumn.setCellValueFactory(c-> new SimpleStringProperty((int)c.getValue().getAmount() + ""));
        priceColumn.setCellValueFactory(c-> new SimpleStringProperty(formatPrice(c.getValue().getTotal())));
        removeColumn.setCellValueFactory(c-> new SimpleObjectProperty<ShoppingItem>(c.getValue()));


        cartItems.setSelectionModel(null);
        cartItems.setPlaceholder(new Label("Kundvagnen är tom."));

        refreshCartView();
        //cartItems.setItems();

        saveListButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Create the custom dialog.
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Skapa Handlingslista");
                dialog.setHeaderText("Ange titel och beskrivning nedan, tryck på Skapa när du är klar.");

// Set the icon (must be included in the project).
// Set the button types.
                ButtonType loginButtonType = new ButtonType("Skapa", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                //grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Titel");
                TextField password = new TextField();
                password.setPromptText("Beskrivning");

                grid.add(new Label("Titel:"), 0, 0);
                grid.add(username, 1, 0, 3, 1);
                grid.add(new Label("Beskrivning:"), 0, 1);
                grid.add(password, 1, 1, 3, 3);

// Enable/Disable login button depending on whether a username was entered.
                Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
                loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
                username.textProperty().addListener((observable, oldValue, newValue) -> {
                    loginButton.setDisable(newValue.trim().isEmpty());
                });

                dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
                Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == loginButtonType) {
                        return new Pair<>(username.getText(), password.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    System.out.println("Titel=" + usernamePassword.getKey() + ", Beskrining=" + usernamePassword.getValue());
                    List<ShoppingItem> itemsList = new ArrayList<>();
                    for (ShoppingItem item : dataHandler.getShoppingCart().getItems()) {
                        itemsList.add(item);
                    }
                    ShoppingList.addNewShoppingList(new ShoppingList(usernamePassword.getKey(), usernamePassword.getValue(), itemsList));
                });
            }
        });

        IMatDataHandler.getInstance().getShoppingCart().addShoppingCartListener(new ShoppingCartListener() {
            @Override
            public void shoppingCartChanged(CartEvent cartEvent) {
                refreshCartView();
            }
        });
    }

    private void refreshCartView() {
        cartItems.setItems(FXCollections.observableList(dataHandler.getShoppingCart().getItems()));
        sumLabel.setText("Summa: " + formatPrice(dataHandler.getShoppingCart().getTotal()));
    }

    public void onEnter(){
        refreshCartView();
    }

    @FXML
    public void clearCartButtonPressed() {
        ButtonType yes = new ButtonType("Ja", ButtonBar.ButtonData.OK_DONE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "",
                yes,
                new ButtonType("Avbryt", ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.setTitle("Bekräfta");
        alert.setHeaderText("Är du säker på att du vill tömma kundvagnen?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes){
            dataHandler.getShoppingCart().clear();
            cartItems.setItems(FXCollections.observableList(dataHandler.getShoppingCart().getItems()));
        } else {
            alert.close();
        }

    }

//    @FXML
//    public void saveListButtonPressed(){
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Spara som lista");
//        dialog.setHeaderText("Fyll i namn och beskrvning för din lista");
//        TextField name = new TextField();
//        TextField description = new TextField();
//
//      /*  GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20,150,10,10));
//        grid.add(new javafx.scene.control.Label("Namn"), 0 ,0);
//        grid.add(name, 1, 0);
//        grid.add(new javafx.scene.control.Label("Beskrvining"), 0 ,0);
//        grid.add(description, 1, 1);
//*/
//        dialog.showAndWait();
//    }


    //test
    @FXML public void checkoutButtonPressed(){
        IMatController.getInstance().checkoutButtonPressed();
    }

    //TODO add spinner for amount, add remove button for each object and functionality for saving cart as a list
}
