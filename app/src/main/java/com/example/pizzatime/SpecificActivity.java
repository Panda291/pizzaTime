package com.example.pizzatime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SpecificActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        Intent data = getIntent();
        String action = data.getStringExtra("EXTRA_TEXT");
        int id = data.getIntExtra("EXTRA_ID", 0);
        TextView desc = (TextView) findViewById(R.id.specificDesc);
        TextView price = (TextView) findViewById(R.id.specificPrice);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor output = db.rawQuery("select c.name, c.price from " + action + " c where c.id = '" + id + "'", null);

        output.moveToPosition(0);


        desc.setText(output.getString(output.getColumnIndex("name")));
        price.setText("€" + String.valueOf(output.getString(output.getColumnIndex("price"))));
    }
}
