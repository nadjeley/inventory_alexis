package com.example.alexis_addo;

// Name: Alexis Adjeley Addo
// ID: 10896904
// Level 300

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/views/Dashboard.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 688, 555);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
 // To Remove any data from the table view in each of the interfaces, a row has to be selected and then
 // remove bill, good or vendor is clicked to remove the data.
    public static void main(String[] args) {
        launch(args);
    }
}





