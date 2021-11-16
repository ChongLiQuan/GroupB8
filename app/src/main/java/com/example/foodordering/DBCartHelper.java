package com.example.foodordering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DBCartHelper extends SQLiteOpenHelper {

    public DBCartHelper(Context context) {
        super(context, "Cart.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table cart (food_image int, food_name TEXT, food_price double, food_amount int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists cart");
    }

    public Boolean insertCart(int mfood_image, String mfood_name, Double mfood_price, int mfood_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("food_image", mfood_image);
        contentValues.put("food_name", mfood_name);
        contentValues.put("food_price", mfood_price);
        contentValues.put("food_amount", mfood_amount);

        long result = MyDB.insert("cart", null, contentValues);

        //Check insertion of the data
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }
    public Boolean updateDuplicate(int mfood_image, String mfood_name, Double mfood_price, int mfood_amount){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("food_image", mfood_image);
        values.put("food_name", mfood_name);
        values.put("food_price", mfood_price);
        values.put("food_amount", mfood_amount);

        MyDB.update("cart", values, "food_name = ?", new String[] {mfood_name});
        return true;
    }

    public Boolean checkDuplicate(String cfoodName){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select food_name FROM cart WHERE food_name = ? ", new String[] {cfoodName});

        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean deleteAllCart(){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        MyDB.execSQL("delete from cart");
        return true;
    }

    public Boolean deleteItem(String dFoodName){
        SQLiteDatabase MyDB = this.getWritableDatabase();

        MyDB.delete("cart", "food_name = ?", new String[] {dFoodName});
            return true;
    }

    public Cursor fetchCart(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from cart", null);
        return cursor;
    }


}
