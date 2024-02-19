package com.example.bloodbond;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;

public class MyCustomAdapter extends ArrayAdapter<DashB> {

    private ArrayList<DashB> dashboardArrayList;
    Context context;

    public MyCustomAdapter(ArrayList<DashB> dashboardArrayList, Context context) {
        super(context, R.layout.item_list_layout,dashboardArrayList);
        this.dashboardArrayList = dashboardArrayList;
        this.context = context;
    }

    private static class MyViewHolder{

        TextView name;
        ImageView image;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DashB dashB= getItem(position);

        MyViewHolder myViewHolder;
        final View result;

        if (convertView==null){
            myViewHolder=new MyViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());

            convertView=inflater.inflate(R.layout.item_list_layout,parent,false);

            myViewHolder.name=(TextView) convertView.findViewById(R.id.list_item_name);
            myViewHolder.image=(ImageView) convertView.findViewById(R.id.img1);

            result=convertView;

            convertView.setTag(myViewHolder);
        }else {
            myViewHolder=(MyViewHolder) convertView.getTag();
            result=convertView;
        }

        myViewHolder.name.setText(dashB.getName());
        myViewHolder.image.setImageResource(dashB.getImageview());

        return result;
        }
}
