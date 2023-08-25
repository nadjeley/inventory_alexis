package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Goods;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;
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

    private ObservableList<Goods> goodsList = FXCollections.observableArrayList();
    private Connection connection;

    public void initialize() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        goodController = new GoodsController(databaseConnector);
        setupTableView();
        connectToDatabase();
        setupGoodsController();
        //createGoodsTable();

    }
    //public void setBillsController(BillController billsController) {
    // this.billsController = billsController;
    //  }
    private void connectToDatabase( ) {
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
        //ObservableList<Goods> goodsList = FXCollections.observableArrayList();
        fetchAllGoods();
        goodsTable.setItems(goodsList);
    }
    public void fetchAllGoods() {


        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement()) {

            String selectQuery = "SELECT * FROM goods";
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");

                Goods goods = new Goods(name, category, quantity, price);
                goodsList.add(goods);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

//    @FXML
//    private void handleView() {
//        fetchAllGoods();
//
//        goodsTable.setItems(goodsList);
//    }
//    @FXML
//    private void handleViews() {
//        // Clear the existing table data
//        goodsTable.getItems().clear();
//
//        // Fetch all goods from the database using the GoodsController
//        List<Goods> allGoods = goodController.fetchAllGoods();
//
//        // Set the fetched data in the TableView
//        goodsTable.getItems().addAll(allGoods);
//    }



//    private void fetchGDataFromDatabase() {
//        String selectQuery = "SELECT name, category, quantity, price FROM goods";
//
//        try (Connection connection = DatabaseConnector.getConnection();
//             PreparedStatement statement = connection.prepareStatement(selectQuery);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String category = resultSet.getString("category");
//                int quantity = resultSet.getInt("quantity");
//                double price = resultSet.getDouble("price");
//
//                Goods goods = new Goods(name, category, quantity, price);
//                goodsList.add(goods);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
   // }


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

        Goods selectedGoods = goodsTable.getSelectionModel().getSelectedItem();

        if (selectedGoods != null) {
            goodController.removeGoods(selectedGoods);;

            // Update the table view
            refreshTableView();
        }





    }


}