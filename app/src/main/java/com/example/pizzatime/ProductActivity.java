package com.example.pizzatime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener{

    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent data = getIntent();
        TextView textField = findViewById(R.id.ProductName);
        action = data.getStringExtra("EXTRA_TEXT");
        textField.setText(action);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor databaseOutput = db.rawQuery("select * from " + action, null);

//        while(databaseOutput.moveToNext())
//        {
//            Log.e("wot", databaseOutput.getString(databaseOutput.getColumnIndex("name")));
//        }

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this, databaseOutput, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        Intent intent = new Intent(ProductActivity.this, SpecificActivity.class);
        intent.putExtra("EXTRA_TEXT", action);
        intent.putExtra("EXTRA_ID", clickedItemIndex+1);
        startActivity(intent);
    }
}