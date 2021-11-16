package com.example.foodordering;

import java.util.Date;

public class HistoryItem {
    private String mUsername;
    private double mOrderBill;
    private String mOrderDate;


    public HistoryItem(String username, Double orderBill, String orderDate){
        mUsername = username;
        mOrderBill = orderBill;
        mOrderDate = orderDate;
    }

    public String getUsername() { return mUsername; }

    public void setUsername(String username) { mUsername = username; }

    public double getOrderBill() {
        return mOrderBill;
    }

    public void setOrderBill(double orderBill) {
        mOrderBill = orderBill;
    }

    public String getOrderDate() {
        return mOrderDate;
    }

    public void setOrderDate(String orderDate) {
        mOrderDate = orderDate;
    }
}
