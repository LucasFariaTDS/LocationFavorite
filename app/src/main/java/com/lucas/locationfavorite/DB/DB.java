package com.lucas.locationfavorite.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DB extends SQLiteOpenHelper implements BaseColumns {
    public static final String Name_Bank = "bank.db";
    public static final String Table = "dataUsers";
    public static final String ID = "id";
    public static final String Fullname = "fullname";
    public static final String Password = "password";
    public static final String Email = "email";
    public static final int Version = 1;

    public DB(Context context) {
        super(context, Name_Bank, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String criarDB = "CREATE TABLE " + DB.Table +
                "( " + DB.ID + " INTEGER PRIMARY KEY, "
                + DB.Fullname + " text, "
                + DB.Password + " text, "
                + DB.Email + " text)";
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

    public boolean verifyUser(String password, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + Table + " WHERE password = ? AND email = ?",
                new String[]{password, email}
        );
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
