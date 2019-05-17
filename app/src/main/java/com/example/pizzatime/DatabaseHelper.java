package com.example.pizzatime;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Database.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE pizza(id INTEGER PRIMARY KEY, name TEXT, price REAL)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL("CREATE TABLE extra(id INTEGER PRIMARY KEY, name TEXT, price REAL)");

        ContentValues values = new ContentValues();
        values.put("name", "pizza margharita");
        values.put("price", 9.00);

        long newRowId = db.insert("pizza", null, values);

        values.put("name", "pizza bolognaise");
        values.put("price", 10.00);

        newRowId = db.insert("pizza", null, values);

        values.put("name", "pizza mozzarella");
        values.put("price", 12.00);

        db.insert("pizza", null, values);

        values.put("name", "fries");
        values.put("price", 4.50);

        db.insert("extra", null, values);

        values.put("name", "garlic bread");
        values.put("price", 5.00);

        db.insert("extra", null, values);

        values.put("name", "garlic bread with cheese");
        values.put("price", 6.00);

        db.insert("extra", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2)
        {
            db.execSQL("CREATE TABLE extra(id INTEGER PRIMARY KEY, name TEXT, price REAL)");
            ContentValues values = new ContentValues();

            values.put("name", "garlic bread");
            values.put("price", 5.00);

            db.insert("extra", null, values);

            values.put("name", "garlic bread with cheese");
            values.put("price", 6.00);

            db.insert("extra", null, values);
        }
        if (oldVersion < 3)
        {
            ContentValues values = new ContentValues();

            values.put("name", "pizza mozzarella");
            values.put("price", 12.00);

            db.insert("pizza", null, values);

            values.put("name", "fries");
            values.put("price", 4.50);

            db.insert("extra", null, values);
        }
    }
}
