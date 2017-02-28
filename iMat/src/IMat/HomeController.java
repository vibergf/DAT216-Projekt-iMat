package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

import java.net.URL;
import java.util.ResourceBundle;




/**
 * Created by Erik on 2017-02-22.
 */
public class HomeController implements Initializable{

    private static HomeController instance;

    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    @FXML
    private Button searchButt;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea Valkommen;
    @FXML
    private ListView aList;
    @FXML
    private GridPane itemGrid;
    private int c =0;
    private  int r = 0;
    private int size = 0;



    @Override
        public void initialize (URL location, ResourceBundle resources){

        Valkommen.setText("Välkommen till IMat" + "\n"
                + "I denna app kan du: " + "\n"
                + "\t" + "-Söka & handla alla våra matvaror"
                + "\n" + "\t" + "-Hålla koll på dina köp mha vår histoikfunktion"
                + "\n" + "\t" + "-Göra dina egna inköpslistor så att du snabbt och lätt kan handla"
                + "\n" + "\n" + "Längst upp på skärmen har du verktyg för att komma åt alla delar av programmet"
                + "\n" + "Till vänster kan du söka efter varor eller välja en kategori.");
        Valkommen.setFont(Font.font(14));
        ObservableList<String> items = FXCollections.observableArrayList("Baljväxter","Bröd", "Bär", "Citrusfrukter","Drycker","Exotiska frukter","Grönsaker","Kål","Kött & Fisk",
                "Mejeriprodukter","Meloner","Mjöl,Socker,Salt & Örtkryddor","Nötter, Stenfrukter & Frön","Pasta,Ris & Potatis","Sötsaker");
        aList.setItems(items);
        aList.setFixedCellSize(25.0);
        System.out.println(aList.getItems());
        System.out.println(aList.getItems().size());
        itemGrid.setVisible(false);
    }
    public  static HomeController getInstance(){return instance;}

        @FXML void returnSearch () {
        System.out.println(searchField.getText());
        Valkommen.setVisible(false);
    }
        @FXML void listClicked () {
        String o = aList.getFocusModel().getFocusedItem().toString();
        Valkommen.setVisible(false);
        System.out.println(o);
        itemGrid.setVisible(true);
        c=0;
        r=0;
        getItems(o);
    }
    protected void getItems(String s){
        r=0;
        c=0;
        switch (s) {

            case "Baljväxter":
                size = dataHandler.getProducts(ProductCategory.POD).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.POD).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {
                        c = 1;
                    } else {
                        c = 0;
                        r++;
                    }
                }
            break;

            case"Bröd":
                size = dataHandler.getProducts(ProductCategory.BREAD).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.BREAD).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {c = 1;}
                    else {c = 0;r++;}
                }
                break;

            case"Citrusfrukter":
                size = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {c = 1;}
                    else {c = 0;r++;}
                }
                break;

            case"Drycker":
                size = dataHandler.getProducts(ProductCategory.COLD_DRINKS).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.COLD_DRINKS).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {c = 1;}
                    else {c = 0;r++;}
                }
                size = dataHandler.getProducts(ProductCategory.HOT_DRINKS).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.HOT_DRINKS).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {c = 1;}
                    else {c = 0;r++;}
                }
                break;

            case"Bär":
                size = dataHandler.getProducts(ProductCategory.BERRY).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.BERRY).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.add(h, c, r);
                    if (c == 0) {c = 1;}
                    else {c = 0;r++;}
                }
                break;
        }
    }
}


