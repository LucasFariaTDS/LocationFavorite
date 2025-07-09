package com.lucas.locationfavorite.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lucas.locationfavorite.DB.DB;

public class DBController {
    private SQLiteDatabase db;
    private DB bank;

    public DBController(Context context) {
        bank = new DB(context);
    }

    public String insertData(String fullname, String password, String email) {
        ContentValues data;
        long result;
        db = bank.getWritableDatabase();
        data = new ContentValues();
        data.put(DB.Fullname, fullname);
        data.put(DB.Password, password);
        data.put(DB.Email, email);
        try {
            result = db.insert(DB.Table, null, data);
            if (result == -1) {
                return "Error inserting record";
            } else {
                return "Record inserted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }
}
