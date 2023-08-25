package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;
import com.example.alexis_addo.model.dataStructures.Vendor;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorController {

    private Connection connection;

    DatabaseConnector databaseConnector = new DatabaseConnector(); // Create a DatabaseConnector instance

    private Map<Integer, Vendor> vendorMap;  // HashMap to store vendor data



    public VendorController(DatabaseConnector databaseConnector) {
        this.connection = databaseConnector.getConnection();
        vendorMap = new HashMap<>();
    }




    // Method to add a new vendor
    public void addVendor(int id, String name, String phone, String location) {
        Vendor vendor = new Vendor(id, name, phone, location);
        vendorMap.put(id, vendor);

        //addVendorMethod
        String sql = "INSERT INTO vendor (id, name, phone, location) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.setString(2, name);
            statement.setString(3, phone);
            statement.setString(4, location);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to remove a vendor
    public void removeVendor(int id) {

        vendorMap.remove(id);
        //remove VEndorMethod
        String delsql = "DELETE FROM vendor WHERE id = ?";
       // try (Connection connection = databaseConnector.getConnection();
         try(PreparedStatement statement = connection.prepareStatement(delsql)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve a vendor by ID
    public Vendor getVendorById(int id) {
        if (vendorMap.containsKey(id)) {
            return vendorMap.get(id);
        }

        //getVendor Method
        // SQL query to retrieve vendor from the database
        String sql = "SELECT name, phone, location FROM vendor WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String location = resultSet.getString("location");
                Vendor vendor = new Vendor(id, name, phone, location);
                vendorMap.put(id, vendor);
                return vendor;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Vendor not found
    }
    // Method to retrieve all vendors
    public List<Vendor> getAllVendors() throws SQLException {

        if (!vendorMap.isEmpty()) {
            return new ArrayList<>(vendorMap.values());
        }
        //getAllvendorMethod
        // SQL query to retrieve all vendors from the database
        String sql = "SELECT id, name, phone, location FROM vendor";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String location = resultSet.getString("location");
                Vendor vendor = new Vendor(id, name, phone, location);
                vendorMap.put(id, vendor);
            }
        }
        return new ArrayList<>(vendorMap.values());

    }




    // Method to update vendor information
    public void updateVendor(int id, String name, String phone, String location) {
        Vendor vendor = vendorMap.get(id);
        if (vendor != null) {
            vendor.setName(name);
            vendor.setPhone(phone);
            vendor.setLocation(location);

            // updateVendor Method
            String sql = "UPDATE vendor SET name = ?, phone = ?, location = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, phone);
                statement.setString(3, location);
                statement.setInt(4, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


}