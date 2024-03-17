package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ParisRouteApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ParisRouteApplication.class.getResource("ParisRoute.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1480, 830);
        stage.setTitle("Paris Route Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();


    }
    }
