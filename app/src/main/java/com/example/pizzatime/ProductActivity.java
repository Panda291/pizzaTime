package com.example.pizzatime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Intent data = getIntent();
        TextView textField = findViewById(R.id.ProductName);
        textField.setText(data.getStringExtra("EXTRA_TEXT"));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}