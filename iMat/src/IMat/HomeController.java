package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;




/**
 * Created by Erik on 2017-02-22.
 */
public class HomeController implements Initializable{


    @FXML
    private Button searchButt;
    @FXML
    private TextField searchField;
    @FXML
    private TextArea Valkommen;
    @FXML
    private ListView aList;



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
        ObservableList<String> items = FXCollections.observableArrayList("Kött", "Fisk", "hej");
        aList.setItems(items);
        System.out.println(aList.getItems());
        System.out.println(aList.getItems().size());
    }

        @FXML void returnSearch () {
        System.out.println(searchField.getText());
        Valkommen.setVisible(false);
    }
        @FXML void listClicked () {
        String o = aList.getFocusModel().getFocusedItem().toString();
        System.out.println(o);
    }
}


