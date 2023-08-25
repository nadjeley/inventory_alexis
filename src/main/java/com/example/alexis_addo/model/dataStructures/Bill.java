package com.example.alexis_addo.model.dataStructures;

import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.List;

import java.sql.Date;

public class Bill {
    private final IntegerProperty billID;
    private final ObjectProperty<LocalDate> date;
    private final DoubleProperty totalAmount;

    private List<Goods> purchasedGoods;

    public Bill(Integer billID, LocalDate date, double totalAmount) {
        this.billID = new SimpleIntegerProperty(billID);
        this.date = new SimpleObjectProperty<>(date);
        this.totalAmount = new SimpleDoubleProperty(totalAmount);
    }



    public int getBillID() {
        return billID.get();
    }

    public void setBillID(int billID) {
        this.billID.set(billID);
    }

    public IntegerProperty billIDProperty() {
        return billID;
    }

    public Date getDate() {
        return Date.valueOf(date.get());
    }

    public void setDate(Date date) {
        this.date.set(date.toLocalDate());
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public double getTotalAmount() {
        return totalAmount.get();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount.set(totalAmount);
    }

    public DoubleProperty totalAmountProperty() {
        return totalAmount;
    }

    public List<Goods> getPurchasedGoods() {
        return purchasedGoods;
    }

    public void addPurchasedGoods(Goods goods) {
        purchasedGoods.add(goods);
    }

}
