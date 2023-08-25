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
import java.util.ArrayList;
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

    private ObservableList<Vendor> vendorList = FXCollections.observableArrayList();
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


        vendorTable.setItems(vendorList);

        fetchDataFromDatabase();

    }

    private void fetchDataFromDatabase() {
        //List<Vendor> vendorData = new ArrayList<>();

        String selectQuery = "SELECT id, name, phone, location FROM vendor";

        try (Connection connection = new DatabaseConnector().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String location = resultSet.getString("location");

                Vendor vendor = new Vendor(id, name, phone, location);
                vendorList.add(vendor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //return vendorData;
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
    @FXML
    private void handleViewButton() {


        // Fetch data from the database and populate the vendorList
        fetchDataFromDatabase();

        // Set the data in the TableView
        vendorTable.setItems(vendorList);
    }

    private void updateTableView() throws SQLException {
        List<Vendor> allVendors = vendorController.getAllVendors();
        ObservableList<Vendor> vendorList = FXCollections.observableArrayList(allVendors);
        vendorTable.setItems(vendorList);
    }
}
