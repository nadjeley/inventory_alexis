package com.example.alexis_addo;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Dashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 688, 555);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





