package com.example.foodordering;

public class FoodItem {
    private int mImageResource;
    private String mItemName;
    private double mPrice;
    private String mCategory;

    public FoodItem(int imageResource, String itemName, String category, double price){
        mImageResource = imageResource;
        mItemName = itemName;
        mPrice = price;
        mCategory = category;
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

    public String getCategory() { return mCategory; }
}
