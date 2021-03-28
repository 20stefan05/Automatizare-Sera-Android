package com.example.automatizaresera;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Plants> PlantList = null;
    private ArrayList<Plants> arraylist;

    public ListViewAdapter(Context context, List<Plants> PlantList) {
        mContext = context;
        this.PlantList = PlantList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Plants>();
        this.arraylist.addAll(PlantList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return PlantList.size();
    }

    @Override
    public Plants getItem(int position) {
        return PlantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(PlantList.get(position).getPlant());
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        PlantList.clear();
        if (charText.length() == 0) {
            PlantList.addAll(arraylist);
        } else {
            for (Plants wp : arraylist) {
                if (wp.getPlant().toLowerCase(Locale.getDefault()).contains(charText)) {
                    PlantList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
