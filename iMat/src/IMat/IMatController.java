package IMat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class IMatController implements Initializable {

    @FXML private Button aButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        aButton.setText("En annan knapp");
    }

    @FXML void buttonPress(){
        System.exit(0);
    }
}
