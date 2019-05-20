package com.example.pizzatime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener{

    private String action;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

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

        recyclerAdapter = new RecyclerAdapter(this, databaseOutput, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        Intent intent = new Intent(ProductActivity.this, SpecificActivity.class);
        intent.putExtra("EXTRA_TEXT", action);
        TextView IdView = recyclerView.findViewHolderForAdapterPosition(clickedItemIndex).itemView.findViewById(R.id.IdView);
        intent.putExtra("EXTRA_ID", Integer.parseInt(IdView.getText().toString()));
        startActivity(intent);
    }
}