package com.lucas.locationfavorite.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLocation extends SQLiteOpenHelper {

    private static final String DB_NAME = "locationDB";
    private static final int DB_VERSION = 2; // incrementado por causa do novo campo

    public DBLocation(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE location (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "lat REAL," +
                "long REAL," +
                "photoUri TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS location");
        onCreate(db);
    }

    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM location", null);
    }

    public void deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("location", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
