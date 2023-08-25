package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.dataStructures.Vendor;
import com.example.alexis_addo.model.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.util.List;

public class VendorConnection {

    @FXML
    private TableColumn<Vendor, Integer> columnId;

    @FXML
    private TableColumn<Vendor, String> columnName;

    @FXML
    private TableColumn<Vendor, String> columnPhone;

    @FXML
    private TableColumn<Vendor, String> columnLocation;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TableView<Vendor> vendorTable;

    @FXML
    private Button addVendorButton;

    @FXML
    private Button removeVendorButton;

    @FXML
    private Button updateButton;

    private Connection connection;
    private VendorController vendorController;
    private DatabaseConnector databaseConnector;
    public void initialize() {

        setupTableView();
        connectToDatabase();
        setupVendorController();
    }
    private void connectToDatabase() {
        DatabaseConnector databaseConnector = new DatabaseConnector(); // Create an instance of DatabaseConnector
        connection = databaseConnector.getConnection(); // Initialize the connection using DatabaseConnector
    }

    private void setupVendorController() {
        DatabaseConnector databaseConnector = new DatabaseConnector(); // Create a DatabaseConnector instance
        vendorController = new VendorController(databaseConnector); // Instantiate VendorController using DatabaseConnector
    }
    private void setupTableView() {
        // Set up cell value factories for each column
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnLocation.setCellValueFactory(new PropertyValueFactory<>("location"));

        // Set the item list for the TableView
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList();
        vendorTable.setItems(vendorList);
    }



    @FXML
    private void handleAddVendor() throws SQLException {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String phone = phoneTextField.getText();
        String location = locationTextField.getText();

        vendorController.addVendor(id, name, phone, location);

        // Clear the input fields
        idTextField.clear();
        nameTextField.clear();
        phoneTextField.clear();
        locationTextField.clear();

        // Update the table view
        refreshTableView();
    }

    private void refreshTableView() throws SQLException {
        List<Vendor> vendors = vendorController.getAllVendors();
        vendorTable.getItems().setAll(vendors);
    }


//    @FXML
//    private void handleRemoveVendor() {
//        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();
//
//        if (selectedVendor != null) {
//            String deleteQuery = "DELETE FROM vendor WHERE id = ?";
//            try (Connection connection = databaseConnector.getConnection();
//                 PreparedStatement statement = connection.prepareStatement(deleteQuery)){
//                 statement.setInt(1, selectedVendor.getId());
//
//                int rowsAffected = statement.executeUpdate();
//                if (rowsAffected > 0) {
//                    System.out.println("Vendor removed from the database");
//
//                    vendorController.removeVendor(selectedVendor.getId());
//
//                    updateTableView();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
  @FXML
  private void handleRemoveVendor() throws SQLException {
    Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();

    if (selectedVendor != null) {
        vendorController.removeVendor(selectedVendor.getId());

        // Update the table view
        refreshTableView();
    }
}   @FXML
    private void handleUpdateVendor() throws SQLException {
        Vendor selectedVendor = vendorTable.getSelectionModel().getSelectedItem();

        if (selectedVendor != null) {


            // retrieve the updated information
            int updatedId = selectedVendor.getId(); // Assuming id is not updated
            String updatedName = nameTextField.getText(); // Get the updated name from the text field
            String updatedPhone = phoneTextField.getText(); // Get the updated phone from the text field
            String updatedLocation = locationTextField.getText(); // Get the updated location from the text field

            vendorController.updateVendor(updatedId, updatedName, updatedPhone, updatedLocation);

            // Update the vendor's properties in the table view directly
            selectedVendor.setName(updatedName);
            selectedVendor.setPhone(updatedPhone);
            selectedVendor.setLocation(updatedLocation);



            // Refresh the table view after updating
            refreshTableView();
        }
    }
    private void updateTableView() throws SQLException {
        List<Vendor> allVendors = vendorController.getAllVendors();
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList(allVendors);
        vendorTable.setItems(vendorList);
    }
}
