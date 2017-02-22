/**
 * Created by Lasse on 17/02/2017.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/imat");

        Parent root = FXMLLoader.load(getClass().getResource("imat.fxml"), bundle);

        //För att köra hemvyn tills vidare
        //Parent root = FXMLLoader.load(getClass().getResource("homeView.fxml"), bundle);

        Scene scene = new Scene(root);

        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
}
