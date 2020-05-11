package Graphics;


import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World");
        Parent root = new Parent() {
        };
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
