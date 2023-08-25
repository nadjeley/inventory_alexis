package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Bill;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateBillPopUpController {

    @FXML
    private TextField idField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField totalAmountField;

    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<Bill> billTable;
    private boolean confirmed = false;
    private DatabaseConnector databaseConnector = new DatabaseConnector();



    public void initialize(URL location, ResourceBundle resources) {
        //databaseConnector = new DatabaseConnector(); // Initialize the databaseConnector instance
    }


    @FXML
    private void onConfirmButtonClicked(ActionEvent event) {

        confirmed = true;
        closePopup();
    }

    @FXML
    private void onCancelButtonClicked(ActionEvent event) {
        // Perform actions when the Cancel button is clicked
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    // Methods to get the entered data
    public String getEnteredId() {
        return idField.getText();
    }

    public String getEnteredDate() {
        return datePicker.getValue().toString();
    }

    public String getEnteredTotalAmount() {
        return totalAmountField.getText();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}