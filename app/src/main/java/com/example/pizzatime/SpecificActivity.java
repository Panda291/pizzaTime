package com.example.pizzatime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SpecificActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);

        Intent data = getIntent();
        String action = data.getStringExtra("EXTRA_TEXT");
        int id = data.getIntExtra("EXTRA_ID", 1);
        TextView desc = (TextView) findViewById(R.id.specificDesc);
        TextView price = (TextView) findViewById(R.id.specificPrice);
        TextView pizza = (TextView) findViewById(R.id.specificPizzaView);
        TextView extra = (TextView) findViewById(R.id.specificExtraView);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        if (action.equals("pizza") || action.equals("extra")) {
            Cursor output = db.rawQuery("select c.name, c.price from " + action + " c where c.id = '" + id + "'", null);

            output.moveToPosition(0);

            desc.setText(output.getString(output.getColumnIndex("name")));
            price.setText("€" + String.valueOf(output.getString(output.getColumnIndex("price"))));
            output.close();
        }
        else if (action.equals("menu")) {
            Cursor output = db.rawQuery("select c.name, c.price, c.pid, c.eid from menu c where c.id = '" + id + "'", null);

            output.moveToPosition(0);

            desc.setText(output.getString(output.getColumnIndex("name")));
            int pid = output.getInt(output.getColumnIndex("pid"));
            int eid = output.getInt(output.getColumnIndex("eid"));
            price.setText("€" + String.valueOf(output.getString(output.getColumnIndex("price"))));

            output.close();

            output = db.rawQuery("select p.name from pizza p where p.id = '" + pid + "'", null);
            output.moveToPosition(0);
            pizza.setText(output.getString(output.getColumnIndex("name")));
            output.close();

            output = db.rawQuery("select e.name from extra e where e.id = '" + eid + "'", null);
            output.moveToPosition(0);
            extra.setText(output.getString(output.getColumnIndex("name")));
            output.close();
        }
    }
}
