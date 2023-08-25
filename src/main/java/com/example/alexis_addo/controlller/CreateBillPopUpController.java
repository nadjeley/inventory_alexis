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
       // databaseConnector = new DatabaseConnector();
        // Perform actions when the Confirm button is clicked
//        Integer id = Integer.valueOf(idField.getText());
//        LocalDate date = datePicker.getValue();
//        double totalAmount = Double.parseDouble(totalAmountField.getText());
//
//        // Insert the new bill into the database
//        String insertQuery = "INSERT INTO bill (id, date, totalAmount) VALUES (?, ?, ?)";
//
//        try (Connection connection = databaseConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(insertQuery)) {
//            statement.setInt(1, Integer.parseInt(String.valueOf((id))));
//            statement.setDate(2, Date.valueOf(date));
//            statement.setDouble(3, totalAmount);
//
//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("New bill added to the database");
//
//                // Create a new Bill object
//                Bill newBill = new Bill(id, date, totalAmount);
//
//                // Add the new Bill to the TableView
//                billTable.getItems().add(newBill);
//
//                // Close the popup
//                Stage stage = (Stage) idField.getScene().getWindow();
//                stage.close();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

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