package IMat;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {

    @FXML private AnchorPane checkoutPane;
    @FXML private AnchorPane checkoutPageOne;
    @FXML private AnchorPane checkoutPageTwo;
    @FXML private AnchorPane checkoutPageThree;
    @FXML private AnchorPane checkoutPageFour;

    @FXML private ListView<ShoppingItem> checkoutOverviewList;
    @FXML private TextArea checkoutOverviewField;

    @FXML private TextField checkoutNameField;
    @FXML private TextField checkoutAddressField;
    @FXML private TextField checkoutApartmentField;
    @FXML private TextField checkoutFloorField;
    @FXML private TextField checkoutDoorField;
    @FXML private TextField checkoutCityField;
    @FXML private TextField checkoutPhoneField;

    @FXML private ChoiceBox<String> checkoutCardTypeField;
    @FXML private TextField checkoutCardOwnerField;
    @FXML private TextField checkoutCardNumberField;
    @FXML private TextField checkoutCardDayField;
    @FXML private TextField checkoutCardMonthField;
    @FXML private TextField checkoutCardCVVField;

    @FXML private Button backButton;
    @FXML private Button forwardButton;

    private int currentPage;
    private boolean orderPlaced;

    private static String BACK = "Bakåt";
    private static String FORWARD = "Framåt";
    private static String CONFIRM = "Beställ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPage = 0;
        orderPlaced = false;
        checkoutPageOne.setVisible(true);
        checkoutPageTwo.setVisible(true);
        checkoutPageThree.setVisible(true);
        checkoutPageFour.setVisible(true);

        List<String> cardTypeList = new ArrayList<String>();
        cardTypeList.add("VISA");
        cardTypeList.add("MasterCard");
        checkoutCardTypeField.setItems(FXCollections.observableList(cardTypeList));
        checkoutCardTypeField.getSelectionModel().selectFirst();

//        checkoutOverviewList

        updateState();
    }

    public void onEnter(){
        if(orderPlaced){
            currentPage = 0;
            orderPlaced = false;
        }
        updateState();
    }

    private void updateState(){
        checkoutPane.getChildren().clear();
        switch(currentPage){
            case 0:
                backButton.setDisable(true);
                backButton.setText(BACK);
                forwardButton.setDisable(false);
                forwardButton.setText(FORWARD);
                //TODO Set wizard progress
                checkoutPane.getChildren().add(checkoutPageOne);
                break;
            case 1:
                //TODO Set wizard progress
                backButton.setDisable(false);
                backButton.setText(BACK);
                forwardButton.setDisable(false);
                forwardButton.setText(FORWARD);
                checkoutPane.getChildren().add(checkoutPageTwo);
                break;
            case 2:
                //TODO Set wizard progress
                backButton.setDisable(false);
                backButton.setText(BACK);
                forwardButton.setDisable(false);
                forwardButton.setText(CONFIRM);
                updateOverview();
                checkoutPane.getChildren().add(checkoutPageThree);
                break;
            case 3:
                //TODO Set wizard progress
                backButton.setDisable(true);
                backButton.setText(BACK);
                forwardButton.setDisable(true);
                forwardButton.setText(FORWARD);
                checkoutPane.getChildren().add(checkoutPageFour);
                IMatDataHandler.getInstance().getShoppingCart().clear();
                //IMatDataHandler.getInstance().placeOrder(true);
                orderPlaced = true;
                break;
        }
    }

    private void updateOverview(){
        checkoutOverviewList.setItems(FXCollections.observableList(IMatDataHandler.getInstance().getShoppingCart().getItems()));
        checkoutOverviewField.setText("Personuppgifter: \n \n" +
                "Namn: " + checkoutNameField.getText() + "\n" +
                "Adress: " + checkoutAddressField.getText() + "  |  " +
                "Lägenhetsnummer: " + checkoutApartmentField.getText() + "\n" +
                "Våning: " + checkoutFloorField.getText() + "  |  " +
                "Portkod: " + checkoutDoorField.getText() + "\n" +
                "Postort: " + checkoutCityField.getText() + "\n" +
                "Telefonnummer: " + checkoutPhoneField.getText() + "\n \n" +
                "Betalningsuppgifter: \n \n" +
                "Kort: " + checkoutCardTypeField.getValue() + "  " + checkoutCardNumberField.getText() + "\n" +
                "Kortinnehavare: " + checkoutCardOwnerField.getText() + "\n" +
                "Utgångsdatum: " + checkoutCardDayField.getText() + " / " + checkoutCardMonthField.getText() + "\n" +
                "CVV/CVC: " + checkoutCardCVVField.getText());
    }

    @FXML
    private void backButtonPressed(){
        currentPage = Math.max(currentPage - 1, 0);
        updateState();
    }

    @FXML
    private void forwardButtonPressed(){
        currentPage = Math.min(currentPage + 1, 3);
        updateState();
    }
}
