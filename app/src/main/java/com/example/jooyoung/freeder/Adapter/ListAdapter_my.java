package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jooyoung.freeder.EventInformation;
import com.example.jooyoung.freeder.ListItem;
import com.example.jooyoung.freeder.R;

import java.util.ArrayList;

public class ListAdapter_my extends BaseAdapter {
    ArrayList<EventInformation> info = new ArrayList<EventInformation>();
    public ListAdapter_my(){

    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.mypageitem, parent, false);
        }


        TextView name = convertView.findViewById(R.id.event_name);
        TextView d_day = convertView.findViewById(R.id.event_d_day);
        ImageView dead_line = convertView.findViewById(R.id.dead_line);

        int temp = 0;

        if(info.get(position).getDday() > 0){
            dead_line.setImageResource(R.drawable.star_yes);
            d_day.setText("D-" + String.valueOf(info.get(position).getDday()));
        }
        else{
            dead_line.setImageResource(R.drawable.deadline);
            d_day.setText("마감");
        }
        name.setText(info.get(position).getEvent_name());


        return convertView;
    }

    public void addItem(EventInformation user_event ){
        info.add(user_event);
    }
}
