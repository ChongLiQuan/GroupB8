package com.example.foodordering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table user (username TEXT primary key, password TEXT, address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists user");
    }

    //Function to store data into the database
    public Boolean insertData(String username, String password, String address){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("address", address);

        long result = MyDB.insert("user", null, contentValues);

        //Check insertion of the data
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }

    //To Check if the username is already existed in the database
    public Boolean checkUsername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * FROM user WHERE username = ?", new String[] {username});

        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    //To Check the username and password
    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * FROM user WHERE username = ? AND password = ?", new String[] {username, password});

        if (cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    //To select a user
    public Cursor fetchUser(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * FROM user WHERE username = ?", new String[] {username});
        return cursor;
    }

    public Boolean updateData(String username, String address){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("address", address);
        Cursor cursor = MyDB.rawQuery("Select * FROM user WHERE username = ?", new String[] {username});
        if (cursor.getCount() > 0) {

            long result = MyDB.update("user", contentValues, "username = ?", new String[]{username});

            //Check insertion of the data
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else return false;
    }
}
