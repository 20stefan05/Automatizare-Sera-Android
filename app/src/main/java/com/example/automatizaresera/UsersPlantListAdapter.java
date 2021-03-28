package com.example.automatizaresera;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.ColorInt;

import java.util.ArrayList;

public class UsersPlantListAdapter implements ListAdapter {
    ArrayList<UsersPlantList> al;
    TextView status;
    Context context;
    public UsersPlantListAdapter(Context context, ArrayList<UsersPlantList> arrayList) {
        this.al=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return al.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
boolean mconnected = false;
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        UsersPlantList upl = al.get(pos);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            status = convertView.findViewById(R.id.connectionStatus);
            TextView tittle = convertView.findViewById(R.id.list_title);
            ImageView imag = convertView.findViewById(R.id.list_image);
            tittle.setText(upl.PlantName);
            if(mconnected){
            status.setText("Conectat"); status.setTextColor(Color.GREEN);}
            else{ status.setText("Deconectat"); status.setTextColor(Color.RED);}
            //status.setTextColor(Integer.parseInt("#ff0000"));

            if(tittle.getText().length()>= 30) tittle.setTextSize(10);

            String base64Image = upl.Image.split(",")[1];
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imag.setImageBitmap(decodedByte);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    public void connected(){
        mconnected = true;
    }

}
