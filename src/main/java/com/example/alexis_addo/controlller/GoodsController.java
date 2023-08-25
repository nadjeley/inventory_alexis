package com.example.alexis_addo.controlller;

import com.example.alexis_addo.model.DatabaseConnector;

import com.example.alexis_addo.model.dataStructures.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class GoodsController {
    private List<Goods> goodsList;  // Collection to store goods data
    private Stack<Goods> goodsStack;  // Stack for adding and removing goods

    private List<String> categoriesList;
    private Stack<Goods> beveragesStack;  // Stack for beverages
    private Stack<Goods> breadStack;  // Stack for bread/bakery
    private Stack<Goods> cannedGoodsStack;  // Stack for canned/jarred goods
    private Stack<Goods> dairyStack;  // Stack for dairy products

    private Queue<Goods> dryGoodsQueue;  // Queue for dry goods
    private Queue<Goods> frozenProductsQueue;  // Queue for frozen products
    private Queue<Goods> meatQueue;  // Queue for meat

    private List<Goods> farmProduceList;  // List for farm produce
    private List<Goods> homeCleanersList;  // List for home cleaners
    private List<Goods> paperGoodsList;  // List for paper goods
    private List<Goods> homeCareList;  // List for home care
    private Connection connection;
    DatabaseConnector databaseConnector = new DatabaseConnector();
    private Map<String, String> categoryMap;



    public GoodsController(DatabaseConnector databaseConnector) {
        this.connection = DatabaseConnector.getConnection();
        goodsList = new ArrayList<>();
        goodsStack = new Stack<>();

        beveragesStack = new Stack<>();
        breadStack = new Stack<>();
        cannedGoodsStack = new Stack<>();
        dairyStack = new Stack<>();

        dryGoodsQueue = new LinkedList<>();
        frozenProductsQueue = new LinkedList<>();
        meatQueue = new LinkedList<>();

        farmProduceList = new ArrayList<>();
        homeCleanersList = new ArrayList<>();
        paperGoodsList = new ArrayList<>();
        homeCareList = new ArrayList<>();

        categoriesList = new ArrayList<>();
        categoryMap = new HashMap<>();
        initializeCategoryMap();
    }
    private void initializeCategoryMap() {
        //Beverages
        categoryMap.put("coffee", "Beverages");
        categoryMap.put("tea", "Beverages");
        categoryMap.put("juice", "Beverages");
        categoryMap.put("soda", "Beverages");

        //Bread/Bakery
        categoryMap.put("sandwich loaves", "Bread/Bakery");
        categoryMap.put("dinner rolls", "Bread/Bakery");
        categoryMap.put("tortillas", "Bread/Bakery");
        categoryMap.put("cake", "Bread/Bakery");

        // Canned/Jarred Goods
        categoryMap.put("strawberry jam", "Canned/Jarred Goods");
        categoryMap.put("blended tomatos", "Canned/Jarred Goods");
        categoryMap.put("ketchup", "Canned/Jarred Goods");


        // Dry/Baking Goods
        categoryMap.put("cereals", "Dry/Baking Goods");
        categoryMap.put("flour", "Dry/Baking Goods");
        categoryMap.put("sugar", "Dry/Baking Goods");
        categoryMap.put("pasta", "Dry/Baking Goods");
        categoryMap.put("mixes", "Dry/Baking Goods");

        // Frozen Products
        categoryMap.put("waffles", "Frozen Products");
        categoryMap.put("ice cream", "Frozen Products");

        // Meat
        categoryMap.put("lunch meat", "Meat");
        categoryMap.put("poultry", "Meat");
        categoryMap.put("beef", "Meat");
        categoryMap.put("pork", "Meat");

        // Farm Produce
        categoryMap.put("fruits", "Farm Produce");
        categoryMap.put("vegetables", "Farm Produce");

        // Home Cleaners
        categoryMap.put("all-purpose", "Home Cleaners");
        categoryMap.put("laundry detergent", "Home Cleaners");
        categoryMap.put("dishwashing liquid", "Home Cleaners");

        // Paper Goods
        categoryMap.put("paper towels", "Paper Goods");
        categoryMap.put("toilet paper", "Paper Goods");
        categoryMap.put("aluminium foil", "Paper Goods");
        categoryMap.put("sandwich bags", "Paper Goods");

        // Home Care
        categoryMap.put("shampoo", "Home Care");
        categoryMap.put("soap", "Home Care");
        categoryMap.put("hand soap", "Home Care");
        categoryMap.put("shaving cream", "Home Care");


        // ...
    }
    // Method to add a new goods item
    public void addGoods(String name, String category, int quantity, double price) throws SQLException {
        Goods goods = new Goods(name, category, quantity, price);
        goodsList.add(goods);
        goodsStack.push(goods);


        String Category = categoryMap.getOrDefault(name, "Unknown");

        if (Category.equalsIgnoreCase("Beverages")) {
            beveragesStack.push(goods);
        } else if (Category.equalsIgnoreCase("Bread/Bakery")) {
            breadStack.push(goods);
        } else if (Category.equalsIgnoreCase("Canned/Jarred Goods")) {
            cannedGoodsStack.push(goods);
        } else if (Category.equalsIgnoreCase("Dairy Products")) {
            dairyStack.push(goods);
        } else if (Category.equalsIgnoreCase("Dry/Baking Goods")) {
            dryGoodsQueue.offer(goods);
        } else if (Category.equalsIgnoreCase("Frozen Products")) {
            frozenProductsQueue.offer(goods);
        } else if (Category.equalsIgnoreCase("Meat")) {
            meatQueue.offer(goods);
        } else if (Category.equalsIgnoreCase("Farm Produce")) {
            farmProduceList.add(goods);
        } else if (Category.equalsIgnoreCase("Home Cleaners")) {
            homeCleanersList.add(goods);
        } else if (Category.equalsIgnoreCase("Paper Goods")) {
            paperGoodsList.add(goods);
        } else if (Category.equalsIgnoreCase("Home Care")) {
            homeCareList.add(goods);
        }

        String sql = "INSERT INTO goods (name, category, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, String.valueOf(category)
            );
            statement.setInt(3, quantity);
            statement.setDouble(4, price);
            statement.executeUpdate();
        }




    }

    // Method to remove a goods item
    public void removeGoods(Goods goods) {
        goodsList.remove(goods);
        goodsStack.remove(goods);

        String Category = categoryMap.getOrDefault(goods.getName(), "Unknown");

        // Check category and remove from appropriate data structure
        if (Category.equalsIgnoreCase("Beverages")) {
            beveragesStack.remove(goods);
        } else if (Category.equalsIgnoreCase("Bread/Bakery")) {
            breadStack.remove(goods);
        } else if (Category.equalsIgnoreCase("Canned/Jarred Goods")) {
            cannedGoodsStack.remove(goods);
        } else if (Category.equalsIgnoreCase("Dairy Products")) {
            dairyStack.remove(goods);
        } else if (Category.equalsIgnoreCase("Dry/Baking Goods")) {
            dryGoodsQueue.remove(goods);
        } else if (Category.equalsIgnoreCase("Frozen Products")) {
            frozenProductsQueue.remove(goods);
        } else if (Category.equalsIgnoreCase("Meat")) {
            meatQueue.remove(goods);
        } else if (Category.equalsIgnoreCase("Farm Produce")) {
            farmProduceList.remove(goods);
        } else if (Category.equalsIgnoreCase("Home Cleaners")) {
            homeCleanersList.remove(goods);
        } else if (Category.equalsIgnoreCase("Paper Goods")) {
            paperGoodsList.remove(goods);
        } else if (Category.equalsIgnoreCase("Home Care")) {
            homeCareList.remove(goods);
        }


        String sql = "DELETE FROM goods WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, goods.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to retrieve goods by category
    public List<Goods> getGoodsByCategory(String category) {
        List<Goods> categoryGoods = new ArrayList<>();
        for (Goods goods : goodsList) {
            if (goods.getCategory().equalsIgnoreCase(category)) {
                categoryGoods.add(goods);
            }
        }
        return categoryGoods;
    }

    // Method to update goods information
    public void updateGoods(Goods goods, String name, String category, int quantity, double price) {
        goods.setName(name);
        goods.setCategory(category);
        goods.setQuantity(quantity);
        goods.setPrice(price);

        String sql = "UPDATE goods SET category = ?, quantity = ?, price = ? WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, String.valueOf(category));
            statement.setInt(2, quantity);
            statement.setDouble(3, price);
            statement.setString(4, goods.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    // Getter for goods list
    public List<Goods> getGoodsList() {
        return goodsList;
    }

    // Getter for goods stack
    public Stack<Goods> getGoodsStack() {
        return goodsStack;
    }
}