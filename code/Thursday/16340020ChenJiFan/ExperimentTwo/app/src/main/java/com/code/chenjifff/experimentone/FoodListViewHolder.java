package com.code.chenjifff.experimenttwo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.code.chenjifff.experimentone.R;

public class FoodListViewHolder extends RecyclerView.ViewHolder {
    Button foodIcon;
    TextView foodName;
    public FoodListViewHolder(View _view) {
        super(_view);
        foodIcon = (Button) _view.findViewById(R.id.food_icon);
        foodName = (TextView) _view.findViewById(R.id.food_name);
    }
}
