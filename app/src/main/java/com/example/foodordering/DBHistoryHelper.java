package com.example.foodordering;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;

public class DBHistoryHelper extends SQLiteOpenHelper {

    public DBHistoryHelper(Context context) {
        super(context, "History.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table history (_id integer primary key autoincrement, username TEXT, total_amount DOUBLE, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists history");
    }

    //Function to store data into the database
    public Boolean insertData(String insert_username, double insert_totalAmount){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //Get current order date
        Date currentTime = Calendar.getInstance().getTime();

        contentValues.put("username", insert_username);
        contentValues.put("total_amount", insert_totalAmount);
        contentValues.put("date", String.valueOf(currentTime));

        long result = MyDB.insert("history", null, contentValues);

        //Check insertion of the data
        if (result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public Cursor fetchHistory(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from history WHERE username = ?", new String[] {username});
        return cursor;
    }
}
