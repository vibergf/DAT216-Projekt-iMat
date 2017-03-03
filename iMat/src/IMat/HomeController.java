package IMat;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
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
    private FlowPane itemGrid;
    @FXML
    private ScrollPane scrollPane;

    private int size = 0;

    private static final int LIST_CELL_HEIGHT = 36;

    @Override
        public void initialize (URL location, ResourceBundle resources){
          Valkommen.setText("Välkommen till IMat" + "\n"
                + "I denna app kan du: " + "\n"
                + "\t" + "-Söka & handla alla våra matvaror"
                + "\n" + "\t" + "-Hålla koll på dina köp mha vår histoikfunktion"
                + "\n" + "\t" + "-Göra dina egna inköpslistor så att du snabbt och lätt kan handla"
                + "\n" + "\n" + "Längst upp på skärmen har du verktyg för att komma åt alla delar av programmet"
                + "\n" + "Till vänster kan du söka efter varor eller välja en kategori.");

        ObservableList<String> items = FXCollections.observableArrayList(
                "Bröd",
                "Drycker",
                "Frukt",
                "Grönsaker",
                "Kött & Fisk",
                "Mejeriprodukter",
                "Bakning",
                "Örtkryddor",
                "Nötter & Frön",
                "Potatis, ris & pasta",
                "Sötsaker");

        aList.setItems(items);
        aList.setFixedCellSize(LIST_CELL_HEIGHT);
        aList.setPrefHeight(aList.getItems().size() * LIST_CELL_HEIGHT + 2);

        System.out.println(aList.getItems());
        System.out.println(aList.getItems().size());
        itemGrid.setVisible(false);
    }
    public  static HomeController getInstance(){return instance;}

    @FXML void returnSearch (){
        Valkommen.setVisible(false);
        itemGrid.getChildren().clear();
        scrollPane.setVvalue(0);
        System.out.println(searchField.getText());
        String input = searchField.getText().toLowerCase();
        size=dataHandler.getProducts().size();
        itemGrid.setVisible(true);

        for(int n=0;n<size;n++){
            Product product = dataHandler.getProducts().get(n);
            String proName = product.getName().toLowerCase();
            if(input.equals("")){}
            else if(proName.startsWith(input)){
                HomeGridCell h = new HomeGridCell(product);
                itemGrid.getChildren().add(h);
            }
        }
    }

    @FXML void listClicked () {
        String o = aList.getFocusModel().getFocusedItem().toString();
        Valkommen.setVisible(false);
        System.out.println(o);
        itemGrid.setVisible(true);
        getItems(o);
    }

    protected void getItems(String s){
        itemGrid.getChildren().clear();
        scrollPane.setVvalue(0);

        switch (s) {

            case"Bröd":
                size = dataHandler.getProducts(ProductCategory.BREAD).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.BREAD).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Drycker":
                size = dataHandler.getProducts(ProductCategory.COLD_DRINKS).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.COLD_DRINKS).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size =dataHandler.getProducts(ProductCategory.HOT_DRINKS).size();
                for (int m = 0; m < size; m++) {
                    Product product = dataHandler.getProducts(ProductCategory.HOT_DRINKS).get(m);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Frukt":
                size = dataHandler.getProducts(ProductCategory.FRUIT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.FRUIT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.CITRUS_FRUIT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.EXOTIC_FRUIT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.EXOTIC_FRUIT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.MELONS).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.MELONS).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.BERRY).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.BERRY).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;
            case"Grönsaker":
                size = dataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.VEGETABLE_FRUIT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.POD).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.POD).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size =dataHandler.getProducts(ProductCategory.ROOT_VEGETABLE).size();
                for (int m = 0; m < size; m++) {
                    Product product = dataHandler.getProducts(ProductCategory.ROOT_VEGETABLE).get(m);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.CABBAGE).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.CABBAGE).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Kött & Fisk":
               size = dataHandler.getProducts(ProductCategory.MEAT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.MEAT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size =dataHandler.getProducts(ProductCategory.FISH).size();
                for (int m = 0; m < size; m++) {
                    Product product = dataHandler.getProducts(ProductCategory.FISH).get(m);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Mejeriprodukter":
                size = dataHandler.getProducts(ProductCategory.DAIRIES).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.DAIRIES).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Bakning":
                size = dataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.FLOUR_SUGAR_SALT).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Örtkryddor":
                size =dataHandler.getProducts(ProductCategory.HERB).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.HERB).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;

            case"Nötter & Frön":
                size = dataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.NUTS_AND_SEEDS).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;
            case"Potatis, ris & pasta":
                size =dataHandler.getProducts(ProductCategory.POTATO_RICE).size();
                for (int m = 0; m < size; m++) {
                    Product product = dataHandler.getProducts(ProductCategory.POTATO_RICE).get(m);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                size = dataHandler.getProducts(ProductCategory.PASTA).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.PASTA).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;
            case"Sötsaker":
                size = dataHandler.getProducts(ProductCategory.SWEET).size();
                for (int n = 0; n < size; n++) {
                    Product product = dataHandler.getProducts(ProductCategory.SWEET).get(n);
                    HomeGridCell h = new HomeGridCell(product);
                    itemGrid.getChildren().add(h);
                }
                break;
        }
    }
}


