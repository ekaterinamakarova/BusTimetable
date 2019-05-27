package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Main extends Application {

    private static final Logger logger = Logger.getLogger(Main.class);


    private static Stage stage;
    public static Stage getStage() { return stage; }

    @Override
    public void start(Stage primaryStage) throws Exception{
        BasicConfigurator.configure();
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root, 640, 320);
        scene.getStylesheets().add("sample/stylesheet.css");
        primaryStage.setTitle("BusTimetable");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
}
