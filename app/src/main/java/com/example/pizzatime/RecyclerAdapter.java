package com.example.pizzatime;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListItem> listItems;
    private Cursor databaseOutput;
    private Context context;

    public RecyclerAdapter(ProductActivity productActivity, Cursor database) {
        listItems = new ArrayList<ListItem>();
        listItems.add(new ListItem("test"));
        listItems.add(new ListItem("test2"));
        databaseOutput = database;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //ListItem listItem = listItems.get(i);

        //viewHolder.descView.setText(listItem.getWhatever());
        databaseOutput.moveToPosition(i);
        viewHolder.descView.setText(databaseOutput.getString(databaseOutput.getColumnIndex("name")));
        viewHolder.priceView.setText(String.valueOf(databaseOutput.getFloat(databaseOutput.getColumnIndex("price"))));
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView descView;
        public TextView priceView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descView = (TextView) itemView.findViewById(R.id.descView);
            priceView = (TextView) itemView.findViewById(R.id.priceView);

        }
    }
}
