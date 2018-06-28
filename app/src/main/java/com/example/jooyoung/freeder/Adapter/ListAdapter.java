package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jooyoung.freeder.ListItem;
import com.example.jooyoung.freeder.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter{
    ArrayList<ListItem> listitem = new ArrayList<ListItem>();
    public ListAdapter(){

    }
    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return listitem.get(position);
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
            convertView = inflater.inflate(R.layout.d_dayitem,parent,false);
        }

        ImageView eventimg = (ImageView) convertView.findViewById(R.id.eventimg);
        TextView eventname = (TextView)convertView.findViewById(R.id.eventname);
        TextView event_day = (TextView)convertView.findViewById(R.id.d_day);
        CheckBox select_day = (CheckBox)convertView.findViewById(R.id.select_day);

        ListItem listItem = listitem.get(position);

        eventimg.setImageDrawable(listItem.getImg());
        eventname.setText(listItem.getTitle());
        event_day.setText(listItem.getDday());



        return convertView;
    }

    public void addItem(Drawable img, String name,String dday){
        ListItem item = new ListItem();
        item.setImg(img);
        item.setTitle(name);
        item.setDday(dday);

        listitem.add(item);

    }
}
