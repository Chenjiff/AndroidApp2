package com.code.chenjifff.experimenttwo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.code.chenjifff.experimentone.R;

import java.util.ArrayList;

public class FoodCollectListViewAdapter extends BaseAdapter {
    private ArrayList<Food> data;
    private Context context;

    FoodCollectListViewAdapter(Context _context, ArrayList<Food> _data) {
        context = _context;
        data = _data;
    }

    private class ViewHolder {
        Button foodIcon;
        TextView foodName;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        if(data == null) {
            return null;
        }
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder viewHolder;
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.food_list_ltem,null);
            viewHolder = new ViewHolder();
            viewHolder.foodIcon = view.findViewById(R.id.food_icon);
            viewHolder.foodName = view.findViewById(R.id.food_name);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.foodIcon.setText(data.get(i).getTypeSimple());
        viewHolder.foodName.setText(data.get(i).getName());
        return view;
    }
}
