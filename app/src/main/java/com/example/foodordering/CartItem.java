package com.example.foodordering;

public class CartItem {
    private int mImageResource;
    private String mItemName;
    private double mPrice;
    private int mQuantity;

    public CartItem(int imageResource, String itemName, double price, int quantity){
        mImageResource = imageResource;
        mItemName = itemName;
        mPrice = price;
        mQuantity = quantity;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getItemName(){
        return mItemName;
    }

    public double getPrice(){
        return mPrice;
    }

    public int getQuantity() { return mQuantity; }
}
