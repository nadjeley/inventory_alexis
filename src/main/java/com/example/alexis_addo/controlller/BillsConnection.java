package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Bill;
import com.example.alexis_addo.model.dataStructures.Goods;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillsConnection {

    @FXML
    private TableColumn<Bill, Integer> columnBillID;

    @FXML
    private TableColumn<Bill, Date> columnDate;

    @FXML
    private TableColumn<Bill, Double> columnTotalAmount;

    @FXML
    private TableView<Bill> billTable;


    private Connection connection;

    private DatabaseConnector databaseConnector = new DatabaseConnector();


    private Statement statement;

    private ObservableList<Bill> billsList = FXCollections.observableArrayList();


    public void initialize() {
        // call the method to display the table to display data
        setupTableView();
        connectToDatabase();

    }
    private void connectToDatabase() {
        connection = DatabaseConnector.getConnection();
    }


    private void setupTableView() {
        // Set up cell value factories for each column
        columnBillID.setCellValueFactory(cellData -> cellData.getValue().billIDProperty().asObject());
        columnDate.setCellValueFactory(cellData -> {
            ObjectProperty<LocalDate> dateProperty = cellData.getValue().dateProperty();
            return new SimpleObjectProperty<>(Date.valueOf(dateProperty.get()));
        });
        columnTotalAmount.setCellValueFactory(cellData -> cellData.getValue().totalAmountProperty().asObject());

        // Set the item list for the TableView
        ObservableList<Bill> billsList = FXCollections.observableArrayList();
        billTable.setItems(billsList);
    }

    //Event handler for Viewing Bills
    @FXML
    private void handleViewBills() {
        try {
            List<Bill> bills = fetchDataFromDatabase();
            ObservableList<Bill> billList = FXCollections.observableArrayList(bills);
            billTable.setItems(billList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Bill> fetchDataFromDatabase() throws SQLException {
        List<Bill> billData = new ArrayList<>();

        String selectQuery = "SELECT id, date, total_amount FROM bill";

        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int billID = resultSet.getInt("id");
                LocalDate date = resultSet.getDate("date").toLocalDate();
                double totalAmount = resultSet.getDouble("total_amount");

                Bill bill = new Bill(billID, date, totalAmount);
                billData.add(bill);
            }
        } catch (SQLException e) {
            throw e;
        }

        return billData;
    }




// Event handler for Creating Bills
    @FXML
    private void handleCreateBill() {


        if (databaseConnector == null) {
            databaseConnector = new DatabaseConnector();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/CreateBIllPopUp.fxml"));
        Parent root;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Create a new stage for the popup
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        // Show the popup and wait for user input
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        // Retrieve the entered data from the popup controller
        CreateBillPopUpController popupController = loader.getController();
        if (popupController.isConfirmed()) {
            String enteredId = popupController.getEnteredId();
            String enteredDate = popupController.getEnteredDate();
            String enteredTotalAmount = popupController.getEnteredTotalAmount();

            // Check if any of the entered data is null
            if (enteredId != null && enteredDate != null && !enteredTotalAmount.isEmpty()) {
                int id = Integer.parseInt(enteredId);
                LocalDate date = LocalDate.parse(enteredDate);
                double total_amount = Double.parseDouble(enteredTotalAmount);


            //Insert the new bill into the database
            String insertQuery = "INSERT INTO bill (id, date, total_amount) VALUES (?, ?, ?)";

            try (Connection connection = databaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, Integer.parseInt(String.valueOf((id))));
                statement.setDate(2, Date.valueOf(date));
                statement.setDouble(3, total_amount);

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New bill added to the database");

                    // Create a new Bill object
                    Bill newBill = new Bill(id, date, total_amount);

                    // Add the new Bill to the TableView
                    billTable.getItems().add(newBill);

                    // Close the popup

                    stage.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            }else {
                // Handle missing data with a pop-up message or other feedback
                showAlert("Please fill in all the fields.");
            }
        }
        }



    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Missing Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }






    // Event handler for the "Remove Bill" button
    @FXML
    private void handleRemoveBill() {

        if (databaseConnector == null) {
            databaseConnector = new DatabaseConnector();
        }
        // Get the selected bill from the table
        Bill selectedBill = billTable.getSelectionModel().getSelectedItem();

        if (selectedBill != null) {
            // Remove the selected bill from the table
            billTable.getItems().remove(selectedBill);

            // Delete the selected bill from the database
            String deleteQuery = "DELETE FROM bill WHERE billID = ?";
            try (Connection connection = databaseConnector.getConnection();
                 PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

                statement.setInt(1, selectedBill.getBillID());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Bill removed from the database");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }



}





