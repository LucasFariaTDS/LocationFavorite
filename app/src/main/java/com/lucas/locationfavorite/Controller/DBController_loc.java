package com.lucas.locationfavorite.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lucas.locationfavorite.DB.DBLocation;

public class DBController_loc {

    private DBLocation dbLocation;

    public DBController_loc(Context context) {
        dbLocation = new DBLocation(context);
    }

    public String insertData(String name, double lat, double lng, String photoUri) {
        SQLiteDatabase db = dbLocation.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("lat", lat);
        values.put("long", lng);
        values.put("photoUri", photoUri);

        long result = db.insert("location", null, values);
        db.close();

        return result == -1 ? "Error inserting" : "Record inserted";
    }
}
