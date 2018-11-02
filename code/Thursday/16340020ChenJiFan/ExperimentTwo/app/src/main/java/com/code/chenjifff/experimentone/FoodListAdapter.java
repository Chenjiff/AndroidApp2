package com.code.chenjifff.experimenttwo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code.chenjifff.experimentone.R;

import java.util.ArrayList;


public  class FoodListAdapter extends RecyclerView.Adapter<FoodListViewHolder>{
    private ArrayList<Food> foodData;
    private Context context;
    private OnItemClickListener itemClickListener;

    public FoodListAdapter(Context _context, ArrayList<Food> _food_data) {
        context = _context;
        foodData = _food_data;
    }

    public interface OnItemClickListener {
        void OnItemLongClick(int i);
        void OnItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return foodData.size();
    }

    @NonNull
    @Override
    public FoodListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FoodListViewHolder holder =
                new FoodListViewHolder(LayoutInflater.from(context).inflate(R.layout.food_list_ltem, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final FoodListViewHolder viewHolder, int i) {
        if(itemClickListener != null) {
            viewHolder.foodName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.OnItemClick(viewHolder.getAdapterPosition());
                }
            });
            viewHolder.foodName.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickListener.OnItemLongClick(viewHolder.getAdapterPosition());
                    return true;
                }
            });
        }
        viewHolder.foodIcon.setText(foodData.get(i).getTypeSimple());
        viewHolder.foodName.setText(foodData.get(i).getName());
    }
}

