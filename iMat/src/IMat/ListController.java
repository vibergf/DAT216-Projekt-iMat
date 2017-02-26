package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import se.chalmers.ait.dat215.project.IMatDataHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Erik on 2017-02-22.
 */
public class ListController implements Initializable {


    private IMatDataHandler dataHandler = IMatDataHandler.getInstance();

    private @FXML
    ListView<String> shoppingList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = FXCollections.observableArrayList (
                "Börjes Kakor", "Vecko Listan", "Kanelbullar", "Kroppkakor", "Norrlands Pölsa");
        shoppingList.setItems(items);
    }
}
