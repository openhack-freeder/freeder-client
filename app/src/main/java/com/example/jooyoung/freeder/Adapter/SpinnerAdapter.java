package com.example.jooyoung.freeder.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jooyoung.freeder.*;

import java.util.ArrayList;

public class SpinnerAdapter extends BaseAdapter {
    Context _context;
    ArrayList<spinner_item> _list;
    public SpinnerAdapter(Context context, ArrayList<spinner_item> list){
        _context = context;
        _list = list;

    }
    @Override
    public int getCount() {
        return (_list == null) ?0: _list.size();
    }

    @Override
    public Object getItem(int position) {
        return _list.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item,parent,false);
        }
        spinner_item item = _list.get(position);

        TextView name = (TextView)convertView.findViewById(R.id.categoryname);
        ImageView image = (ImageView)convertView.findViewById(R.id.spinner_image);
        name.setText(item.getName());
        image.setImageResource(item.get_flag_id());

        return convertView;
    }
}
