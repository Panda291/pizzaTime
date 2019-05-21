package com.example.pizzatime;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SpecificActivity extends AppCompatActivity {

    String action;
    String action2;
    int id;
    int id2;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        Intent data = getIntent();
        action = data.getStringExtra("EXTRA_TEXT");
        action2 = data.getStringExtra("EXTRA_ACTION");

        Button addButton = findViewById(R.id.addButton);

        id = data.getIntExtra("EXTRA_ID", 1);
        id2 = data.getIntExtra("EXTRA_ID2", 0);
        TextView desc = (TextView) findViewById(R.id.specificDesc);
        TextView price = (TextView) findViewById(R.id.specificPrice);
        TextView pizza = (TextView) findViewById(R.id.specificPizzaView);
        TextView extra = (TextView) findViewById(R.id.specificExtraView);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor output;
        if (action.equals("pizza") || action.equals("extra")) {
            output = db.rawQuery("select c.name, c.price from " + action + " c where c.id = '" + id + "'", null);

            output.moveToPosition(0);

            desc.setText(output.getString(output.getColumnIndex("name")));
            price.setText("€" + String.valueOf(output.getString(output.getColumnIndex("price"))));
            pizza.setText("");
            extra.setText("");
            output.close();
        }
        else if (action.equals("menu")) {
            output = db.rawQuery("select c.name, c.price, c.pid, c.eid from menu c where c.id = '" + id + "'", null);

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
        else if (action.equals("discount"))
        {
            output = db.rawQuery("select d.name, d.price, d.pid from discount d where d.id = '" + id + "'", null);

            output.moveToPosition(0);

            desc.setText(output.getString(output.getColumnIndex("name")));
            int pid = output.getInt(output.getColumnIndex("pid"));
            price.setText("€" + String.valueOf(output.getString(output.getColumnIndex("price"))));

            output.close();

            output = db.rawQuery("select p.name from pizza p where p.id = '" + pid + "'", null);
            output.moveToPosition(0);
            pizza.setText(output.getString(output.getColumnIndex("name")));
            output.close();

            extra.setText("");
        }
        if (action2.equals("remove"))
        {
            addButton.setText("Remove from cart");
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromCart();
                }
            });
        }
        else {
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart();
                }
            });
        }
    }

    void addToCart()
    {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("type", action);
        values.put("fid", id);
        db.insert("cart", null, values);

        Context context = getApplicationContext();
        CharSequence text = "added to cart";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        finish();
    }
    void removeFromCart(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        db.delete("cart", "id = '" + id2 + "'", null);

        Context context = getApplicationContext();
        CharSequence text = "item removed";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
