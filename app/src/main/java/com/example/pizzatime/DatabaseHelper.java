package com.example.pizzatime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE pizza(id INTEGER PRIMARY KEY, name TEXT, price REAL)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        ContentValues values = new ContentValues();
        values.put("name", "pizza margharita");
        values.put("price", 9.00);

        long newRowId = db.insert("pizza", null, values);

        values.put("name", "pizza bolognaise");
        values.put("price", 10.00);

        newRowId = db.insert("pizza", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
