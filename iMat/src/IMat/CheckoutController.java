package IMat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
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

    @FXML private ChoiceBox<String> checkoutCardTypeField;
    @FXML private TextField checkoutCardOwnerField;
    @FXML private TextField checkoutCardNumberField;
    @FXML private TextField checkoutCardMonthField;
    @FXML private TextField checkoutCardYearField;
    @FXML private TextField checkoutCardCVVField;

    @FXML private Button backButton;
    @FXML private Button forwardButton;

    @FXML private ImageView wizardImage;
    @FXML private ImageView successImage;

    private ChangeListener<String> checkoutPageOneListener;
    private ChangeListener<String> checkoutPageTwoListener;

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
            checkoutCardCVVField.setText(newValue);
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
                //TODO Set wizard progress
                backButton.setDisable(false);
                backButton.setText("Tillbaka till kundvagnen");
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-1.png"));
                wizardProgressLabel.setText("Kontaktuppgifter");
                obligatoryLabel.setText(OBLIGATORY);
                updatePageOne();
                checkoutPane.getChildren().add(checkoutPageOne);
                break;
            case 1:
                //TODO Set wizard progress
                backButton.setDisable(false);
                backButton.setText(BACK);
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-2.png"));
                wizardProgressLabel.setText("Kortuppgifter");
                obligatoryLabel.setText(OBLIGATORY);
                if(checkoutCardOwnerField.getText().isEmpty())
                    checkoutCardOwnerField.setText(checkoutNameField.getText());
                updatePageTwo();
                checkoutPane.getChildren().add(checkoutPageTwo);
                break;
            case 2:
                //TODO Set wizard progress
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
                //TODO Set wizard progress
                backButton.setDisable(true);
                backButton.setText(BACK);
                forwardButton.setDisable(true);
                forwardButton.setText(FORWARD);
                wizardImage.setImage(new Image("/resources/wizard-4.png"));
                wizardProgressLabel.setText("Beställning klar");
                obligatoryLabel.setText("");
                checkoutPane.getChildren().add(checkoutPageFour);
                IMatDataHandler.getInstance().getShoppingCart().clear();
                //IMatDataHandler.getInstance().placeOrder(true);
                break;
        }
    }

    private void updatePageOne(){
        if(checkoutNameField.getText().isEmpty() || checkoutAddressField.getText().isEmpty()
                || checkoutCityField.getText().isEmpty() || checkoutPhoneField.getText().isEmpty())
            forwardButton.setDisable(true);
        else
            forwardButton.setDisable(false);
    }

    private void updatePageTwo(){
        if(checkoutCardOwnerField.getText().isEmpty() || checkoutCardNumberField.getText().isEmpty()
                || checkoutCardMonthField.getText().isEmpty() || checkoutCardYearField.getText().isEmpty()
                ||checkoutCardCVVField.getText().isEmpty())
            forwardButton.setDisable(true);
        else
            forwardButton.setDisable(false);
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
