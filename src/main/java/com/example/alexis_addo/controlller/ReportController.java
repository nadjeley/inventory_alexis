package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Bill;
import com.example.alexis_addo.model.dataStructures.Goods;
import com.example.alexis_addo.model.dataStructures.Vendor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportController {
    private DatabaseConnector databaseConnector;

    public ReportController() {
        databaseConnector = new DatabaseConnector();
    }

    @FXML
    private void generateGoodsReport() {
        List<Goods> goodsList = fetchGoodsDataFromDatabase();
        // Fetch the goods list
        Alert goodsReportAlert = new Alert(Alert.AlertType.INFORMATION);
        goodsReportAlert.setTitle("Goods Report");
        goodsReportAlert.setHeaderText(null);

        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setWrapText(true);

        StringBuilder reportText = new StringBuilder();
        reportText.append("Goods Report:\n");
        reportText.append("--------------\n");
        for (Goods goods : goodsList) {
            reportText.append("Name: ").append(goods.getName()).append("\n");
            reportText.append("Category: ").append(goods.getCategory()).append("\n");
            reportText.append("Quantity: ").append(goods.getQuantity()).append("\n");
            reportText.append("Price: ").append(goods.getPrice()).append("\n");
            reportText.append("\n");
        }

        reportTextArea.setText(reportText.toString());
        goodsReportAlert.getDialogPane().setContent(reportTextArea);

        goodsReportAlert.showAndWait();
    }
    @FXML
    private void generateVendorsReport() {
        List<Vendor> vendorList = fetchVendorDataFromDatabase();

        Alert vendorsReportAlert = new Alert(Alert.AlertType.INFORMATION);
        vendorsReportAlert.setTitle("Vendors Report");
        vendorsReportAlert.setHeaderText(null);

        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setWrapText(true);

        StringBuilder reportText = new StringBuilder();
        reportText.append("Vendors Report:\n");
        reportText.append("---------------\n");
        for (Vendor vendor : vendorList) {
            reportText.append("Name: ").append(vendor.getName()).append("\n");
            reportText.append("Phone: ").append(vendor.getPhone()).append("\n");
            reportText.append("Location: ").append(vendor.getLocation()).append("\n");
            reportText.append("\n");
        }

        reportTextArea.setText(reportText.toString());
        vendorsReportAlert.getDialogPane().setContent(reportTextArea);

        vendorsReportAlert.showAndWait();
    }

    @FXML
    private void generateBillsReport() {
        List<Bill> billList = fetchBillDataFromDatabase();

        Alert billsReportAlert = new Alert(Alert.AlertType.INFORMATION);
        billsReportAlert.setTitle("Bills Report");
        billsReportAlert.setHeaderText(null);

        TextArea reportTextArea = new TextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setWrapText(true);

        StringBuilder reportText = new StringBuilder();
        reportText.append("Bills Report:\n");
        reportText.append("-------------\n");
        for (Bill bill : billList) {
            reportText.append("Bill ID: ").append(bill.getBillID()).append("\n");
            reportText.append("Date: ").append(bill.getDate()).append("\n");
            reportText.append("Total Amount: ").append(bill.getTotalAmount()).append("\n");

            reportText.append("\n");
        }

        reportTextArea.setText(reportText.toString());
        billsReportAlert.getDialogPane().setContent(reportTextArea);

        billsReportAlert.showAndWait();
    }


    // Method to generate a report of all goods
//    public void generateGoodsReport(List<Goods> goodsList) {
//        System.out.println("Goods Report:");
//        System.out.println("--------------");
//        for (Goods goods : goodsList) {
//            System.out.println("Name: " + goods.getName());
//            System.out.println("Category: " + goods.getCategory());
//            System.out.println("Quantity: " + goods.getQuantity());
//            System.out.println("Price: " + goods.getPrice());
//            System.out.println();
//        }
//    }
//    @FXML
//    // Method to generate a report of all vendors
//    public void generateVendorsReport(List<Vendor> vendorList) {
//        System.out.println("Vendors Report:");
//        System.out.println("---------------");
//        for (Vendor vendor : vendorList) {
//            System.out.println("Name: " + vendor.getName());
//            System.out.println("Phone: " + vendor.getPhone());
//            System.out.println("Location: " + vendor.getLocation());
//            System.out.println();
//        }
//    }

    // Method to generate a report of all bills
//    public void generateBillsReport(List<Bill> billList) {
//        System.out.println("Bills Report:");
//        System.out.println("-------------");
//        for (Bill bill : billList) {
//            System.out.println("Bill ID: " + bill.getBillID());
//            System.out.println("Date: " + bill.getDate());
//            System.out.println("Total Amount: " + bill.getTotalAmount());
//            System.out.println("Purchased Goods:");
//            for (Goods goods : bill.getPurchasedGoods()) {
//                System.out.println(goods.getName() + " - Category: " + goods.getCategory() + " - Quantity: " + goods.getQuantity());
//            }
//            System.out.println();
//        }
//    }



    private List<Goods> fetchGoodsDataFromDatabase() {
        List<Goods> goodsList = new ArrayList<>();

        String selectQuery = "SELECT name, category, quantity, price FROM goods";

        try (Connection connection = new DatabaseConnector().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

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

        return goodsList;
    }
    private List<Bill> fetchBillDataFromDatabase() {
        List<Bill> billList = new ArrayList<>();

        String selectQuery = "SELECT id, date, total_amount FROM bill";

        try (Connection connection = new DatabaseConnector().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                java.sql.Date date = resultSet.getDate("date");
                double total_amount = resultSet.getDouble("total_amount");

                // Fetch purchased goods for this bill


                Bill bill = new Bill(id, date.toLocalDate(), total_amount);
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return billList;
    }

//    private List<Goods> fetchPurchasedGoodsForBill(Connection connection, int billID) {
//        List<Goods> purchasedGoods = new ArrayList<>();
//
//        String selectQuery = "SELECT goods.name, goods.category, goods.quantity, goods.price FROM goods " +
//                "JOIN bill_goods ON goods.name = bill_goods.goodsName AND bill_goods.billID = ?";
//
//        try (PreparedStatement statement = connection.prepareStatement(selectQuery)) {
//            statement.setInt(1, billID);
//            ResultSet resultSet = statement.executeQuery();
//
//            while (resultSet.next()) {
//                String name = resultSet.getString("name");
//                String category = resultSet.getString("category");
//                int quantity = resultSet.getInt("quantity");
//                double price = resultSet.getDouble("price");
//
//                Goods goods = new Goods(name, category, quantity, price);
//                purchasedGoods.add(goods);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return purchasedGoods;
//    }
    private List<Vendor> fetchVendorDataFromDatabase() {
        List<Vendor> vendorList = new ArrayList<>();

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

        return vendorList;
    }
}