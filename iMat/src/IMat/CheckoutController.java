package IMat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

import static IMat.IMatController.*;

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
    @FXML private Label checkoutOverviewTotalLabel;
    @FXML private Label wizardProgressLabel;
    @FXML private Label obligatoryLabel;

    @FXML private TextField checkoutNameField;
    @FXML private TextField checkoutAddressField;
    @FXML private TextField checkoutApartmentField;
    @FXML private TextField checkoutFloorField;
    @FXML private TextField checkoutDoorField;
    @FXML private TextField checkoutCityField;
    @FXML private TextField checkoutPhoneField;
    @FXML private CheckBox checkoutSaveCustomerCheckbox;

    @FXML private ChoiceBox<String> checkoutCardTypeField;
    @FXML private TextField checkoutCardOwnerField;
    @FXML private TextField checkoutCardNumberField;
    @FXML private TextField checkoutCardMonthField;
    @FXML private TextField checkoutCardYearField;
    @FXML private TextField checkoutCardCVVField;
    @FXML private CheckBox checkoutSaveCardCheckbox;

    @FXML private Button backButton;
    @FXML private Button forwardButton;

    @FXML private ImageView wizardImage;
    @FXML private ImageView successImage;

    private ChangeListener<String> checkoutPageOneListener;
    private ChangeListener<String> checkoutPageTwoListener;

    private Customer customer;

    private int currentPage;

    private static String OBLIGATORY = "*Obligatoriskt fält";
    private static String BACK = "Bakåt";
    private static String FORWARD = "Nästa";
//    private static String CONFIRM = "Beställ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPage = 0;
        checkoutPageOne.setVisible(true);
        checkoutPageTwo.setVisible(true);
        checkoutPageThree.setVisible(true);
        checkoutPageFour.setVisible(true);

        customer = IMatDataHandler.getInstance().getCustomer();
        if(customer.getLastName().length() == 0) {
            setSaveInfo();
        }
        String[] info = customer.getLastName().split("_");
//        checkoutSaveCustomerCheckbox.setSelected(Boolean.valueOf(info[0]));
        checkoutSaveCardCheckbox.setSelected(Boolean.valueOf(info[1]));

        List<String> cardTypeList = new ArrayList<String>();
        cardTypeList.add("VISA");
        cardTypeList.add("MasterCard");
        checkoutCardTypeField.setItems(FXCollections.observableList(cardTypeList));
        checkoutCardTypeField.getSelectionModel().selectFirst();

        checkoutOverviewList.setCellFactory(c -> {
            return new CheckoutOverviewListCell();
        });

        checkoutPageOneListener = (observable, oldValue, newValue) -> {
            updatePageOne();
        };

        checkoutPageTwoListener = (observable, oldValue, newValue) -> {
            updatePageTwo();
        };

        checkoutNameField.textProperty().addListener(checkoutPageOneListener);
        checkoutAddressField.textProperty().addListener(checkoutPageOneListener);
        checkoutCityField.textProperty().addListener(checkoutPageOneListener);
        checkoutPhoneField.textProperty().addListener(checkoutPageOneListener);
        checkoutPhoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.replaceAll("[^\\d]", "");
            checkoutPhoneField.setText(newValue);
        });
        checkoutSaveCustomerCheckbox.selectedProperty().addListener(c -> {
            updatePageOne();
        });

        checkoutCardOwnerField.textProperty().addListener(checkoutPageTwoListener);

        checkoutCardNumberField.textProperty().addListener(checkoutPageTwoListener);
        checkoutCardNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                checkoutCardNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }});

        checkoutCardMonthField.textProperty().addListener(checkoutPageTwoListener);
        checkoutCardMonthField.textProperty().addListener((observable, oldValue, newValue) -> {
                newValue = newValue.replaceAll("[^\\d]", "");
                newValue = newValue.length() <= 2 ? newValue : newValue.substring(0, 2);
                checkoutCardMonthField.setText(newValue);
            });

        checkoutCardYearField.textProperty().addListener(checkoutPageTwoListener);
        checkoutCardYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.replaceAll("[^\\d]", "");
            newValue = newValue.length() <= 2 ? newValue : newValue.substring(0, 2);
            checkoutCardYearField.setText(newValue);
        });

        checkoutCardCVVField.textProperty().addListener(checkoutPageTwoListener);
        checkoutCardCVVField.textProperty().addListener((observable, oldValue, newValue) -> {
            newValue = newValue.replaceAll("[^\\d]", "");
            newValue = newValue.length() <= 3 ? newValue : newValue.substring(0, 3);
            checkoutCardCVVField.setText(newValue);
        });
        checkoutSaveCardCheckbox.selectedProperty().addListener(c -> {
            updatePageTwo();
        });

        successImage.setImage(new Image("/resources/order_success.png"));

        updateState();
    }

    public void onEnter(){
        currentPage = 0;
        updateState();
    }

    private void updateState(){
        checkoutPane.getChildren().clear();
        switch(currentPage){
            case 0:
                backButton.setDisable(false);
                backButton.setText("Tillbaka till kundvagnen");
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-1.png"));
                wizardProgressLabel.setText("Kontaktuppgifter");
                obligatoryLabel.setText(OBLIGATORY);
                loadCustomerInfo();
                updatePageOne();
                checkoutPane.getChildren().add(checkoutPageOne);
                break;
            case 1:
                backButton.setDisable(false);
                backButton.setText(BACK);
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-2.png"));
                wizardProgressLabel.setText("Kortuppgifter");
                obligatoryLabel.setText(OBLIGATORY);
                loadCardInfo();
                updatePageTwo();
                if(checkoutCardOwnerField.getText().isEmpty())
                    checkoutCardOwnerField.setText(checkoutNameField.getText());
                checkoutPane.getChildren().add(checkoutPageTwo);
                break;
            case 2:
                backButton.setDisable(false);
                backButton.setText(BACK);
                forwardButton.setDisable(false);
                wizardImage.setImage(new Image("/resources/wizard-3.png"));
                wizardProgressLabel.setText("Bekräfta info");
                obligatoryLabel.setText("");
                forwardButton.setText("Bekräfta beställning");
                updateOverview();
                checkoutPane.getChildren().add(checkoutPageThree);
                break;
            case 3:
                backButton.setDisable(true);
                backButton.setText(BACK);
                forwardButton.setDisable(true);
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-4.png"));
                wizardProgressLabel.setText("Beställning klar");
                obligatoryLabel.setText("");
                checkoutPane.getChildren().add(checkoutPageFour);
//                IMatDataHandler.getInstance().getShoppingCart().clear();
                IMatDataHandler.getInstance().placeOrder(true);
                break;
        }
    }

    private void loadCustomerInfo(){
        if(Boolean.valueOf(customer.getLastName().split("_")[0])){
            checkoutNameField.setText(customer.getFirstName());
            checkoutAddressField.setText(customer.getAddress());
            checkoutCityField.setText(customer.getPostAddress());
            checkoutPhoneField.setText(customer.getPhoneNumber());
            String[] info = customer.getPostCode().split("_");
            if(info.length == 2) {
                checkoutApartmentField.setText(info[0]);
                checkoutFloorField.setText(info[1]);
                checkoutDoorField.setText(info[2]);
            }
            checkoutSaveCustomerCheckbox.setSelected(true);
        }
    }

    private void loadCardInfo(){
        if(Boolean.valueOf(customer.getLastName().split("_")[1])){
            CreditCard card = IMatDataHandler.getInstance().getCreditCard();
            checkoutCardTypeField.setValue(card.getCardType());
            checkoutCardOwnerField.setText(card.getHoldersName());
            checkoutCardNumberField.setText(card.getCardNumber());
            checkoutCardMonthField.setText(card.getValidMonth() + "");
            checkoutCardYearField.setText(card.getValidYear() + "");
            checkoutSaveCardCheckbox.setSelected(true);
        }
    }

    private void setSaveInfo(){
        customer.setLastName(Boolean.toString(checkoutSaveCustomerCheckbox.isSelected()) + "_" +
                Boolean.toString(checkoutSaveCardCheckbox.isSelected()));
    }

    private void updatePageOne(){
        if(checkoutNameField.getText().isEmpty() || checkoutAddressField.getText().isEmpty()
                || checkoutCityField.getText().isEmpty() || checkoutPhoneField.getText().isEmpty())
            forwardButton.setDisable(true);
        else
            forwardButton.setDisable(false);
        if(checkoutSaveCustomerCheckbox.isSelected()){
            customer.setFirstName(checkoutNameField.getText());
            customer.setAddress(checkoutAddressField.getText());
            customer.setPostAddress(checkoutCityField.getText());
            customer.setPhoneNumber(checkoutPhoneField.getText());
            customer.setPostCode(checkoutApartmentField.getText() + '_' +
                    checkoutFloorField.getText() + '_' + checkoutDoorField.getText());
        }
        setSaveInfo();
    }

    private void updatePageTwo(){
        if(checkoutCardOwnerField.getText().isEmpty() || checkoutCardNumberField.getText().isEmpty()
                || checkoutCardMonthField.getText().isEmpty() || checkoutCardYearField.getText().isEmpty()
                ||checkoutCardCVVField.getText().isEmpty())
            forwardButton.setDisable(true);
        else
            forwardButton.setDisable(false);

        if(checkoutSaveCardCheckbox.isSelected()){
            CreditCard card = IMatDataHandler.getInstance().getCreditCard();
            card.setCardType(checkoutCardTypeField.getValue());
            card.setHoldersName(checkoutCardOwnerField.getText());
            card.setCardNumber(checkoutCardNumberField.getText());
            try {
                card.setValidMonth(Integer.parseInt(checkoutCardMonthField.getText()));
                card.setValidYear(Integer.parseInt(checkoutCardYearField.getText()));
            }catch(NumberFormatException e){}
        }
        setSaveInfo();
    }

    private void updateOverview(){
        checkoutOverviewList.setItems(FXCollections.observableList(IMatDataHandler.getInstance().getShoppingCart().getItems()));
        checkoutOverviewTotalLabel.setText("Totalt: " + formatPrice(IMatDataHandler.getInstance().getShoppingCart().getTotal()));
        String apartmentNumber = checkoutApartmentField.getText().isEmpty() ? "" :
                "Lägenhetsnummer: " + checkoutApartmentField.getText() + "\n";
        String floorNumber = checkoutFloorField.getText().isEmpty() ? "" :
                "Våning: " + checkoutFloorField.getText() + "\n";
        String doorCode = checkoutDoorField.getText().isEmpty() ? "" :
                "Portkod: " + checkoutDoorField.getText() + "\n";
        checkoutOverviewField.setText(
                "Namn: " + checkoutNameField.getText() + "\n" +
                "Adress: " + checkoutAddressField.getText() + "\n" +
                apartmentNumber +
                floorNumber +
                doorCode +
                "Postort: " + checkoutCityField.getText() + "\n" +
                "Telefonnummer: " + checkoutPhoneField.getText() + "\n \n" +

                "Kort: " + checkoutCardTypeField.getValue() + "  " + checkoutCardNumberField.getText() + "\n" +
                "Kortinnehavare: " + checkoutCardOwnerField.getText() + "\n" +
                "Utgångsdatum: " + checkoutCardMonthField.getText() + " / " + checkoutCardYearField.getText() + "\n" +
                "CVV/CVC: " + checkoutCardCVVField.getText());
    }

    @FXML
    private void backButtonPressed(){
        if(currentPage == 0){
            IMatController.getInstance().cartButtonPressed();
        }
        currentPage = Math.max(currentPage - 1, 0);
        updateState();
    }

    @FXML
    private void forwardButtonPressed(){
        currentPage = Math.min(currentPage + 1, 3);
        updateState();
    }
}
