package com.lucas.locationfavorite.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBLocation extends SQLiteOpenHelper implements BaseColumns {
    public static final String Name_Bank = "location.db";
    public static final String Table = "location";
    public static final String ID = "id";
    public static final String Name = "name";
    public static final String Latitude = "lat";
    public static final String Longitude = "long";
    public static final int Version = 1;

    public DBLocation(Context context) {
        super(context, Name_Bank, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criarDB = "CREATE TABLE " + DBLocation.Table +
                "( " + DBLocation.ID + " INTEGER PRIMARY KEY, "
                + DBLocation.Name + " text, "
                + DBLocation.Latitude + " real, "
                + DBLocation.Longitude + " real)";
        sqLiteDatabase.execSQL(criarDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Cursor getAllLocations() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + Table, null);
    }

    public int deleteLocation(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(Table, "id = ?", new String[]{String.valueOf(id)});
    }
}
