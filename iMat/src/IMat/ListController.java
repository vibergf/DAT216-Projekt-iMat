package IMat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
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
        shoppingList.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
            @Override
            public ListCell<String> call(ListView<String> listView)
            {
                return new ListViewCell();
            }
        });
    }


    public static class ListViewCell extends ListCell<String>
    {
        @Override
        public void updateItem(String string, boolean empty)
        {
            super.updateItem(string,empty);
            if(string != null)
            {
                Data data = new Data();
                data.setInfo(string);
                setGraphic(data.getBox());
            }
        }
    }

    public static class Data
    {
        @FXML
        private AnchorPane hBox;
        @FXML
        private Label labelTitle;
        @FXML
        private Label labelSummary;

        @FXML
        private Label labelPrice;

        public Data() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/shoppingListCell.fxml"));
            fxmlLoader.setController(this);
            try
            {
                fxmlLoader.load();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }

        public void setInfo(String string)
        {
            labelTitle.setText(string);
            labelSummary.setText(string + " " + string);
            labelPrice.setText(new Random().nextInt(1000) + " :-");
        }

        public AnchorPane getBox()
        {
            return hBox;
        }
    }


    public static class ShoppingList {
        private String name;
        private String description;
        private List<Product> items;

        public ShoppingList(String name, String description, List<Product> items) {
            this.name = name;
            this.description = description;
            this.items = items;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Product> getItems() {
            return items;
        }

        public void setItems(List<Product> items) {
            this.items = items;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ShoppingList that = (ShoppingList) o;

            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (description != null ? !description.equals(that.description) : that.description != null) return false;
            return items != null ? items.equals(that.items) : that.items == null;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (description != null ? description.hashCode() : 0);
            result = 31 * result + (items != null ? items.hashCode() : 0);
            return result;
        }
    }
}
