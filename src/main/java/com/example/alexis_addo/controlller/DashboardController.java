package com.example.alexis_addo.controlller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML

    public void initialize() {

        // Other initialization code
    }



    @FXML
    private void goToGoodsManagement() {
            // Code to navigate to the Goods Management page goes here
            loadFXML("/views/GoodsView.fxml");
    }

    @FXML
        private void goToVendorManagement() {
            // Code to navigate to the Vendor Management page goes here
            loadFXML("/views/VendorView.fxml");
    }

    @FXML
        private void goToBillManagement() {
            // Code to navigate to the Bill Management page goes here
            loadFXML("/views/BillsManagement.fxml");
    }

    @FXML
    private void goToReportManagement() {
        // Code to navigate to the Report Management page goes here
        loadFXML("/views/ReportManagement.fxml");
    }


    private void loadFXML(String fxmlPath) {
        try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


