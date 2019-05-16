package com.example.pizzatime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<ListItem> listItems;
    private Context context;

    public RecyclerAdapter(ProductActivity productActivity) {
        listItems = new ArrayList<ListItem>();
        listItems.add(new ListItem("test"));
        listItems.add(new ListItem("test2"));
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ListItem listItem = listItems.get(i);

        viewHolder.textView2.setText(listItem.getWhatever());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView2 = (TextView) itemView.findViewById(R.id.textView2);

        }
    }
}
