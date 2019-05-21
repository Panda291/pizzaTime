package com.example.pizzatime;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity implements RecyclerAdapter.ListItemClickListener{

    private String action;
    private String action2;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent data = getIntent();
        TextView textField = findViewById(R.id.ProductName);
        Button checkoutButton = findViewById(R.id.checkoutButton);
        action = data.getStringExtra("EXTRA_TEXT");
        action2 = data.getStringExtra("EXTRA_ACTION");
        textField.setText(action);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        final SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor databaseOutput;
        if (action.equals("cart"))
        {
            databaseOutput = db.rawQuery("select c.fid as 'price', c.type as 'name', c.id from cart c", null);
        }
        else {
            databaseOutput = db.rawQuery("select * from " + action, null);
        }

//        while(databaseOutput.moveToNext())
//        {
//            Log.e("wot", databaseOutput.getString(databaseOutput.getColumnIndex("name")));
//        }

        recyclerAdapter = new RecyclerAdapter(this, databaseOutput, this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);


        if (action2.equals("cart"))
        {
            checkoutButton.setVisibility(View.VISIBLE);
            checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.delete("cart", "", null);
                    Context context = getApplicationContext();
                    CharSequence text = "order placed";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    finish();
                }
            });
        }
        else
        {
            checkoutButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        if(!action.equals("cart")) {
            Intent intent = new Intent(ProductActivity.this, SpecificActivity.class);
            intent.putExtra("EXTRA_TEXT", action);
            intent.putExtra("EXTRA_ACTION", "add");
            TextView IdView = recyclerView.findViewHolderForAdapterPosition(clickedItemIndex).itemView.findViewById(R.id.IdView);
            intent.putExtra("EXTRA_ID", Integer.parseInt(IdView.getText().toString()));
            startActivity(intent);
        }
        else
        {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            TextView IdView = recyclerView.findViewHolderForAdapterPosition(clickedItemIndex).itemView.findViewById(R.id.IdView);

            Cursor output = db.rawQuery("select * from cart where id = " + Integer.parseInt(IdView.getText().toString()), null);

            output.moveToPosition(0);

            Intent intent = new Intent(ProductActivity.this, SpecificActivity.class);
            intent.putExtra("EXTRA_TEXT", output.getString(output.getColumnIndex("type")));
            intent.putExtra("EXTRA_ACTION", "remove");
            intent.putExtra("EXTRA_ID", output.getString(output.getColumnIndex("fid")));
            intent.putExtra("EXTRA_ID2", Integer.parseInt(IdView.getText().toString()));
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }
}