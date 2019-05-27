package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;
    public static Stage getStage() { return stage; }

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        primaryStage.setTitle("BusTimetable");
        primaryStage.setScene(new Scene(root, 640, 320));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
