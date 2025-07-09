package com.lucas.locationfavorite.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.lucas.locationfavorite.DB.DBLocation;

public class DBController_loc {
    private SQLiteDatabase db;
    private DBLocation bank;

    public DBController_loc(Context context) {
        bank = new DBLocation(context);
    }

    public String insertData(String name, double latitude, double longitude) {
        ContentValues data = new ContentValues();
        db = bank.getWritableDatabase();

        data.put(DBLocation.Name, name);
        data.put(DBLocation.Latitude, latitude);
        data.put(DBLocation.Longitude, longitude);

        try {
            long result = db.insert(DBLocation.Table, null, data);
            return result == -1 ? "Error inserting record" : "Record inserted";
        } catch (Exception e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }
}