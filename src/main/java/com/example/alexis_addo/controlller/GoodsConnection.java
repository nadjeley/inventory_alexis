package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Goods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.List;

public class  GoodsConnection {

    @FXML
    private TableColumn<Goods, String> columnCategory;

    @FXML
    private TableColumn<Goods, String> columnName;

    @FXML
    private TableColumn<Goods, Double> columnPrice;

    @FXML
    private TableColumn<Goods, Integer> columnQuantity;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField categoryTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TableView<Goods> goodsTable;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    public Button addGoodsButton;

    @FXML
    public Button removeGoodsButton;

    private GoodsController goodController;


    private Statement statement;

    private DatabaseConnector databaseConnector; // Use DatabaseConnector for connection handling

    private Connection connection;

    public void initialize() {

        setupTableView();
        connectToDatabase();
        setupGoodsController();
        //createGoodsTable();

    }
    //public void setBillsController(BillController billsController) {
    // this.billsController = billsController;
    //  }
    private void connectToDatabase() {
        DatabaseConnector databaseConnector = new DatabaseConnector(); // Create an instance of DatabaseConnector
        connection = DatabaseConnector.getConnection(); // Initialize the connection using DatabaseConnector
    }

    private void setupGoodsController() {
        DatabaseConnector databaseConnector = new DatabaseConnector(); // Create a DatabaseConnector instance
        goodController = new GoodsController(databaseConnector); // Instantiate VendorController using DatabaseConnector
    }

    private void setupTableView() {
        // Set up cell value factories for each column
        columnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set the item list for the TableView
        ObservableList<Goods> goodsList = FXCollections.observableArrayList();
        goodsTable.setItems(goodsList);
    }

    // Connect to the database


    // Create the goods table in the database
    // private void createGoodsTable() {
    // try {
    //String createTableQuery = "CREATE TABLE IF NOT EXISTS goods (" +
    // "name VARCHAR(255)," +
    // "category VARCHAR(255)," +
    //"quantity INT," +
    // "price DOUBLE)";
    //statement.executeUpdate(createTableQuery);
    // System.out.println("Goods table created");
    // } catch (SQLException e) {
    // e.printStackTrace();
    //}
    // }

    // Add event handler for the "Add Goods" button

    //    @FXML
//    private void handleAddGoods() {
//        if (databaseConnector == null) {
//            databaseConnector = new DatabaseConnector();
//        }
//
//        // Get the input values from the UI fields
//        String name = nameTextField.getText();
//        String category = categoryChoiceBox.getValue(); // Use the selected value from the ChoiceBox
//        int quantity = Integer.parseInt(quantityTextField.getText());
//        double price = Double.parseDouble(priceTextField.getText());
//
//
//        // Insert the new goods into the database
//        String insertQuery = "INSERT INTO goods (name, category, quantity, price) " +
//                "VALUES (?, ?, ?, ?)";
//
//        try (Connection connection = DatabaseConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(insertQuery)) {
//            statement.setString(1, name);
//            statement.setString(2, String.valueOf(category));
//            statement.setInt(3, quantity);
//            statement.setDouble(4, price);
//
//            int rowsAffected = statement.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("New goods added to the database");
//
//                // Create a new goods item
//                Goods newGoods = new Goods(name, category, quantity, price);
//
//                // Add the new goods to the table
//                goodsTable.getItems().add(newGoods);
//
//                // Clear the text fields
//                nameTextField.clear();
//                categoryChoiceBox.getSelectionModel().clearSelection(); // Clear the selected item in ChoiceBox
//                quantityTextField.clear();
//                priceTextField.clear();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//    }
    @FXML
    private void handleAddGoods() throws SQLException {
        String name = nameTextField.getText();
        String category = categoryChoiceBox.getValue(); // Use the selected value from the ChoiceBox
        int quantity = Integer.parseInt(quantityTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());

        goodController.addGoods(name, category, quantity, price);

        // Clear the input fields
        nameTextField.clear();
        categoryChoiceBox.getSelectionModel().clearSelection(); // Clear the selected item in ChoiceBox
        quantityTextField.clear();
        priceTextField.clear();
        // Update the table view
        refreshTableView();
    }

    private void refreshTableView() throws SQLException {
        List<Goods> goods= goodController.getGoodsList();
        goodsTable.getItems().setAll(goods);
    }





    // Add event handler for the "Remove Goods" button
    @FXML
    private void handleRemoveGoods() throws SQLException {
        // Get the selected goods from the table
//        Goods selectedGoods = goodsTable.getSelectionModel().getSelectedItem();
//
//        if (selectedGoods != null) {
//            // Remove the selected goods from the table
//            goodsTable.getItems().remove(selectedGoods);
//
//            // Delete the selected goods from the database
//            String deleteQuery = "DELETE FROM goods WHERE name = ?";
//            try (Connection connection = databaseConnector.getConnection();
//                 PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
//                int rowsAffected = statement.executeUpdate();
//                if (rowsAffected > 0) {
//                    System.out.println("Goods removed from the database");}
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
        Goods selectedGoods = goodsTable.getSelectionModel().getSelectedItem();

        if (selectedGoods != null) {
            goodController.removeGoods(selectedGoods);;

            // Update the table view
            refreshTableView();
        }



        // Other methods and logic related to goods management

    }
}