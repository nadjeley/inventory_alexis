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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BillsConnection {

    @FXML
    private TableColumn<Bill, Integer> columnBillID;

    @FXML
    private TableColumn<Bill, Date> columnDate;

    @FXML
    private TableColumn<Bill, Double> columnTotalAmount;

    @FXML
    private TableView<Bill> billTable;

    //@FXML
    //private BillController billsController;

    @FXML
    private Button createBillButton;

    @FXML
    private Button removeBillButton;

    private DatabaseConnector databaseConnector = new DatabaseConnector();


    private Statement statement;

    private ObservableList<Bill> billsList = FXCollections.observableArrayList();


    public void initialize() {
        // call the method to display the table to display data
        setupTableView();

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

//    @FXML
//    private void handleViewBills() {
//        try (Connection connection = databaseConnector.getConnection();
//             Statement statement = connection.createStatement()) {
//            String selectQuery = "SELECT * FROM bill";
//            ResultSet resultSet = statement.executeQuery(selectQuery);
//
//            // Clear existing data in the TableView
//            billsList.clear();
//
//            // Fetch all bills from the result set and add them to the ObservableList
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                Date date = resultSet.getDate("date");
//                double total_amount = resultSet.getDouble("total_amount");
//                billsList.add(new Bill(id, date.toLocalDate(), total_amount));
//            }
//
//            // Refresh the TableView to reflect the new data
//            billTable.refresh();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }


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
            int id = Integer.valueOf(popupController.getEnteredId());
            LocalDate date = LocalDate.parse(popupController.getEnteredDate());
            double total_amount = Double.parseDouble(popupController.getEnteredTotalAmount());

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
        }
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
//    @FXML
//    private void handleViewBills() {
//        Connection connection = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = databaseConnector.getConnection();
//            String selectQuery = "SELECT * FROM bill";
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(selectQuery);
//
//            // Clear existing data in the TableView
//            billsList.clear();
//
//            // Fetch all bills from the result set and add them to the ObservableList
//            while (resultSet.next()) {
//                int id = resultSet.getInt("id");
//                Date date = resultSet.getDate("date");
//                double totalAmount = resultSet.getDouble("total_amount");
//                billsList.add(new Bill(id, date.toLocalDate(), totalAmount));
//            }
//
//            // Refresh the TableView to reflect the new data
//            billTable.refresh();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }


}





