package com.example.alexis_addo.controlller;


import com.example.alexis_addo.model.dataStructures.Bill;
import com.example.alexis_addo.model.dataStructures.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillController {
    private Connection connection;
    private List<Bill> billList;  // List to store bill data

    private Bill currentBill; // This will hold the bill being currently edited or added to

    private List<Goods> addedGoods;

    //private GoodsConnection goodsConnection;



    public BillController() {
      //  this.connection = connection;
        billList = new ArrayList<>();
    }


    // Method to set the current bill
    //public void setCurrentBill(Bill bill) {
       // this.currentBill = bill;
    //}

    // Method to add goods to the current bill


    // Method to create a new bill
   // public Bill createBill(int billId) {
       // Bill bill = new Bill(billId);
       // billList.add(bill);
       // return bill;
    //}

    public void createBill(int billId) throws SQLException {
        String sql = "INSERT INTO bill (id) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }
//    // Method to retrieve a bill by ID
//    public Bill getBillById(int billId) {
//        for (Bill bill : billList) {
//            if (bill.getBillId() == billId) {
//                return bill;
//            }
//        }
//        return null; // Bill not found
//    }
   public Bill getBillById(int billId) throws SQLException {
        String sql = "SELECT * FROM bill WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Create a new Bill object using data from the result set
                    int retrievedBillId = resultSet.getInt("id");
                    // Retrieve other necessary data from the result set columns
                    Date retrievedDate = resultSet.getDate("date");
                    double retrievedTotalAmount = resultSet.getDouble("totalAmount");


                    Bill bill = new Bill(retrievedBillId, ((java.sql.Date) retrievedDate).toLocalDate(), retrievedTotalAmount);
                    // Set other properties of the Bill object

                    return bill;
                }
            }
        }
        return null; // Bill not found
    }

    // Method to remove a bill
//    public void removeBill(int billId) {
//        Bill billToRemove = getBillById(billId);
//        if (billToRemove != null) {
//            billList.remove(billToRemove);
//        }
//    }
    public void removeBill(int billId) throws SQLException {
        String sql = "DELETE FROM bill WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, billId);
            statement.executeUpdate();
        }
    }




    // Other methods and operations related to managing bills




}


