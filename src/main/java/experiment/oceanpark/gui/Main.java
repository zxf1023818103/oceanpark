package experiment.oceanpark.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getResource("/Login.fxml");
        Parent parent = FXMLLoader.load(url);
        parent.setUserData(primaryStage);
        primaryStage.setTitle("海洋公园");
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
